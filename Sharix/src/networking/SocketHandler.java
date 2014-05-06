package networking;

import java.io.IOException;
import java.nio.*;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import model.Transfer;
import model.User;

public class SocketHandler extends Thread{
	int ID;
	int BUF_SIZE = 55;
	SocketChannel socket = null;
	Selector selector = null;
	ByteBuffer sockBuf, msgBuf;
	Queue<Transfer> sendQ, recvQ, cmdQ;
	User remote, self;
	boolean active;
	

	
	public SocketHandler(User self) throws IOException {
		this.active = false;
		this.ID = (int)(Math.random() * 100);
	/*	
		this.socket = serverSocketChannel.accept();				
		this.socket.configureBlocking(false);
	*/	
//		ByteToC
	//	this.sockBuf = new CharBuffer() ;//= StringBuffer.allocateDirect(BUF_SIZE);
		//this.msgBuf = new StringBuffer();//= StringBuffer.allocateDirect(BUF_SIZE - 5);
				
		this.self = self;
		
		this.sockBuf = ByteBuffer.allocate(BUF_SIZE);
		this.msgBuf = ByteBuffer.allocate(BUF_SIZE);
		this.selector = Selector.open();
		
		this.sendQ = new LinkedList<Transfer>();
		this.recvQ = new LinkedList<Transfer>();
		this.cmdQ = new LinkedList<Transfer>();
		
	}
	
	public void setSocket(SocketChannel newSocket) throws ClosedChannelException {
		this.socket = newSocket;
		
		this.socket.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, this.sockBuf);
		System.out.println("Connected to: " + this.socket.socket().getRemoteSocketAddress());
		
		
		if (!this.active)
			this.active = true;
	}
	
	void makeCmd(int type, String cmd) {
		System.out.println("Sending message type " + type + " and cmd " + "\"" + cmd + "\"");
		CommandMsgHandler cmdMH = new CommandMsgHandler(type, "r", cmd);
		Transfer tr = new Transfer(0, null, null, cmdMH, 0, Transfer.UPLD);
		this.cmdQ.add(tr);
	}	
	
	void makeRequest(String relativePath) {
		makeCmd(MsgHandler.GETFILE, relativePath);
	}
	
	@SuppressWarnings("deprecation")
	private void parseMessage(){
		int size = this.sockBuf.getInt();
		int type = this.sockBuf.getInt();
		int id;
		String name = "";
		Transfer tr;
		
		//System.out.println("Message size: " + size);
		//System.out.println("Message type: " + type);
		
		System.out.println("Sockbuf pos " + this.sockBuf.position());
		System.out.println("Sockbuf limit " + this.sockBuf.limit());
		
		this.msgBuf.clear();
		this.msgBuf.put(this.sockBuf);
		this.msgBuf.flip();
		
		System.out.println("Msgbuf pos " + this.msgBuf.position());
		System.out.println("Msgbuf limit " + this.msgBuf.limit());
		
		
		switch (type) {
		case MsgHandler.GETNAME:
			System.out.println("Getname");
			
			name = "";
			while (this.msgBuf.position() < this.msgBuf.limit()) {
				System.out.println("Read one char");
				System.out.println(this.msgBuf.getChar());
			}
			
			System.out.println("Found name " + name);
			
			this.remote = new User(0, name);
			makeCmd(MsgHandler.RESNAME, this.self.getName());
			break;
		
		case MsgHandler.RESNAME:
			System.out.println("Resname");
			
			name = "";
			while (this.msgBuf.position() < this.msgBuf.limit())
				name += this.msgBuf.getChar();
			
			this.remote = new User(0, name);
			break;

		case MsgHandler.GETFILE:
			System.out.println("GETFILE");
			System.out.println(System.getProperty("user.dir"));
			name = "";
			//print
			while (this.msgBuf.position() < this.msgBuf.limit())
				name += this.msgBuf.getChar();
			
			System.out.println("On " + name);
			// check to see if we have the file?
			
			if (name.equals(""))
				return;
			
			FileMsgHandler upld = null;
			try {
				upld = new FileMsgHandler(MsgHandler.DATAPACK, "r", name, 0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			tr = new Transfer(0, this.self, this.remote, upld, 0, Transfer.UPLD);
			this.sendQ.add(tr);
			
			int ansType = MsgHandler.ACKFILE;
			if (!true) // If we don't have the file
				ansType = MsgHandler.NACKFILE;
			
			makeCmd(ansType, name + "_" + upld.remaining);
			break;

		case MsgHandler.ACKFILE:
			System.out.println("ACK");
			name = "";
			while (this.msgBuf.position() < this.msgBuf.limit())
				name += this.msgBuf.getChar();
			int sz = 0;
			String[] parts = name.split("_");
			name = parts[0];
			sz = Integer.parseInt(parts[1]);
			System.out.println("On " + name);
			
			FileMsgHandler dwnld = null;
			try {
				dwnld = new FileMsgHandler(MsgHandler.DATAPACK, "rw", name + "_1", sz);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tr = new Transfer(0, this.self, this.remote, dwnld, 0, Transfer.DWNLD);
			this.recvQ.add(tr);
			break;

		case MsgHandler.NACKFILE:
			name = "";
			while (this.msgBuf.position() < this.msgBuf.limit())
				name += this.msgBuf.getChar();
			System.out.println("Can't download " + name);
			break;

		case MsgHandler.DATAPACK:
			tr = this.recvQ.peek();
			
			if (tr == null) {
				System.out.println("No active download");
				break;
			}
			
			FileMsgHandler msg = (FileMsgHandler) tr.getCargo();
			int percent = msg.write(this.msgBuf);
			System.out.println("Writing " + percent);
			tr.setProgress(tr.getProgress() + percent);
			break;
			
		case MsgHandler.ENDFILE:
			System.out.println("ENDFILE");
			tr = this.recvQ.peek();
			
			if (tr == null) {
				System.out.println("No active download ENDFILE");
			}
			
			((FileMsgHandler)tr.getCargo()).close();
			tr.setProgress(100);
			tr.setStatus(Transfer.CMPLT);
			this.recvQ.remove();
			System.out.println("Finished sending file!");
			this.stop();
		}
	}
	
	public void recv() throws IOException {
		int bytes = 0;
		//WritableByteChannel outChannel = Channels.newChannel(System.out);
		
		this.sockBuf.clear();
		this.msgBuf.clear();
		
		// TODO 2.8: read from socket into buffer, use a loop
		while ((bytes = this.socket.read(this.sockBuf)) > 0){
			/*if (!buf.hasRemaining()) {
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
			System.out.println(buf);
			*/
			//System.out.println("We read " + bytes + " from  the socket!");
			this.sockBuf.flip();
			parseMessage();
		}
	}
	
	public void send() throws Exception {
		Transfer current = this.cmdQ.peek();
		Queue<Transfer> inUse = this.cmdQ;
		if (current == null) {
			current = this.sendQ.peek();
			inUse = this.sendQ;
		}
		
		//System.out.println(this.ID);
		if (current == null) {
			//System.out.println("Ie null" + this.ID);
			
			return;
		}
		MsgHandler currentHandler = current.getCargo();
		
		int bytes = currentHandler.read(this.msgBuf);
		
		//System.out.println("Type " + currentHandler.getType());
		System.out.println("Sending " + bytes);
		
		if (bytes == 0) {
			//System.out.println("Cleaning");
			current.setProgress(100);
			current.setStatus(Transfer.CMPLT);
			inUse.remove();
			
			if (currentHandler.getType() == MsgHandler.DATAPACK) {
				makeCmd(MsgHandler.ENDFILE, "");
				return;
			} else if (currentHandler.getType() != MsgHandler.ENDFILE) {
				return;
			}
		}
		
		this.sockBuf.clear();
		this.sockBuf.putInt(bytes);
		this.sockBuf.putInt(currentHandler.getType());
		this.sockBuf.put(this.msgBuf);
		
		this.sockBuf.flip();
		//System.out.println("Limit sockbuff " + this.sockBuf.limit());
		
		this.socket.write(this.sockBuf);
	}
	
	public void run() {
		if (!this.active) {
			System.out.println("Inactive socket");
		}
		
		while (this.active) {
			try {
				this.selector.select();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				break;
			}
			
			// iterate over the events
			for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext(); ) {
				// get current event and REMOVE it from the list!!!
				SelectionKey key = it.next();
				it.remove();
				
				if (key.isReadable()) {
					try {
						recv();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				
				} else if (key.isWritable()) {
					try {
						send();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
					
				} else {
					System.out.println("Error! Bad request on dispatcher!");
				}
			}
		}
		
		if (this.selector != null)
			try {
				this.selector.close();
			} catch (IOException e) {}
		
		if (this.socket != null)
			try {
				this.socket.close();
			} catch (IOException e) {}
	}	
}