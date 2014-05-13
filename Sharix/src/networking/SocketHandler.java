package networking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import mediator.Mediator;
import model.Transfer;
import model.User;

public class SocketHandler extends Thread{
	int ID;
	int BUF_SIZE = 1155;
	SocketChannel socket = null;
	Selector selector = null;
	ByteBuffer sockBuf, msgBuf;
	Queue<Transfer> sendQ, recvQ, cmdQ;
	User remote, self;
	boolean active;
	int counter = 0;
	Mediator mediator;

	
	public SocketHandler(Mediator mediator) throws IOException {
		this.active = false;
		this.ID = (int)(Math.random() * 100);
		
		this.remote = null;
		this.mediator = mediator;
		this.self = mediator.getOwnUser();
		
		this.sockBuf = ByteBuffer.allocate(BUF_SIZE);
		this.msgBuf = ByteBuffer.allocate(BUF_SIZE - 8);
		this.selector = Selector.open();
		
		this.sendQ = new LinkedList<Transfer>();
		this.recvQ = new LinkedList<Transfer>();
		this.cmdQ = new LinkedList<Transfer>();
		
	}
	
	public void setMediator(Mediator med) {
		this.mediator = med;
	}
	
	public void setSocket(SocketChannel newSocket) throws ClosedChannelException {
		this.socket = newSocket;
		
		this.socket.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, this.sockBuf);
		System.out.println("Connected to: " + this.socket.socket().getRemoteSocketAddress());
			
		if (!this.active)
			this.active = true;
	}
	
	void makeCmd(int type, String cmd) {
		//System.out.println("Sending message type " + type + " and cmd " + "\"" + cmd + "\"");
		CommandMsgHandler cmdMH = new CommandMsgHandler(type, "r", cmd);
		Transfer tr = new Transfer(0, null, null, cmdMH, 0, Transfer.UPLD);
		this.cmdQ.add(tr);
	}	
	
	void makeRequest(String relativePath) {
		makeCmd(MsgHandler.GETFILE, relativePath);
	}
	
	private void parseMessage(){
		int size = this.sockBuf.getInt();
		int type = this.sockBuf.getInt();
		String id = "", name = "";
		Transfer tr;
		
		
		//System.out.println("Message size: " + size);
		//System.out.println("Message type: " + type);
		
		//System.out.println("Sockbuf pos " + this.sockBuf.position());
		//System.out.println("Sockbuf limit " + this.sockBuf.limit());
		
		this.msgBuf.clear();
		this.msgBuf.put(this.sockBuf);
		this.msgBuf.flip();
		
		//System.out.println("Msgbuf pos " + this.msgBuf.position());
		//System.out.println("Msgbuf limit " + this.msgBuf.limit());
		
		
		switch (type) {
		case MsgHandler.GETID:
			System.out.println("GetID");
			id = "";
			while (this.msgBuf.position() < this.msgBuf.limit()) {
				//System.out.println("Read one char");
				id += this.msgBuf.getChar();
			}
			System.out.println("Found ID " + id);
			
			this.remote = this.mediator.getUser(Integer.parseInt(id));
			
			if (this.remote == null)
				this.active = false;
			makeCmd(MsgHandler.RESID, Integer.toString(this.self.getId()));
			break;
		
		case MsgHandler.RESID:
			//System.out.println("Resname");
			
			id = "";
			while (this.msgBuf.position() < this.msgBuf.limit())
				id += this.msgBuf.getChar();
			
			this.remote = this.mediator.getUser(Integer.parseInt(id));
			if (this.remote == null)
				this.active = false;
			
			break;

		case MsgHandler.GETFILE:
			//System.out.println("GETFILE");
			//System.out.println(System.getProperty("user.dir"));
			name = "";
			//print
			while (this.msgBuf.position() < this.msgBuf.limit())
				name += this.msgBuf.getChar();
			
			int ansType = MsgHandler.ACKFILE;
			FileMsgHandler upld = null;
			if (this.mediator.hasFile(name)) {
				
				try {
					upld = new FileMsgHandler(MsgHandler.DATAPACK, "r", name, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
				}
				tr = new Transfer(0, this.self, this.remote, upld, 0, Transfer.UPLD);
				this.mediator.newTransfer(tr);
				this.sendQ.add(tr);
				
			} else {
				ansType = MsgHandler.NACKFILE;
			}
			
			makeCmd(ansType, name + "_" + upld.remaining);
			break;

		case MsgHandler.ACKFILE:
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("ACK");
			name = "";
			System.out.println(this.msgBuf.position() + " " + this.msgBuf.limit());
			while (this.msgBuf.hasRemaining() && this.msgBuf.position() < 500) {
				
				//System.out.println("Name: " + name);
				if (this.recvQ.size() > 1) {
					//System.out.println(this.recvQ.size());
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
				}
				//System.out.println(this.recvQ.size());
				//System.out.println(this.msgBuf.position() + " " + this.msgBuf.limit());
				
				char x = this.msgBuf.getChar();
				name += x;
			}
			try {
				System.out.println("Receiving ------> " + new String(this.msgBuf.array(), "UTF-8"));
				System.out.println("Dafuq");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long sz = 0;
			String vec;
			System.out.println("NAME= " + "\"" + name + "\"ahah");
			String[] parts = name.split("_");
			name = parts[0];
			if (name.equals(""))
				return;
			System.out.println("NAMEEEEE" + parts[1]);
			parts[1] = parts[1].replaceAll("[^\\d.]", "");
			System.out.println("SIZE: " + parts[1].length());
			if (parts[1].length() > 25)
				System.out.println("Char: " + parts[1].charAt(25));
			String x = parts[1];
			sz = Long.parseLong(x);
			System.out.println("On " + name);
			
			FileMsgHandler dwnld = null;
			try {
				dwnld = new FileMsgHandler(MsgHandler.DATAPACK, "rw", name + "_1", sz);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tr = new Transfer(0, this.self, this.remote, dwnld, 0, Transfer.DWNLD);
			this.mediator.newTransfer(tr);
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
			int bytes = msg.write(this.msgBuf);
			//System.out.println("Writing " + percent);
			tr.setProgress(tr.getProgress() + (double)bytes * 100 / msg.size);
			//msg.close();
			this.counter += 1;
			if (this.counter == 40) {
				System.out.println(this.recvQ.peek().getProgress());
				this.counter = 0;
			}
			
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
			try {
				sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			break;
			//this.stop();
			
			default:
				break;
		}
		
		this.msgBuf.clear();
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
			//System.out.println("SENDQ");
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
		//System.out.println("Sending " + bytes);
		
		if (bytes == 0) {
			//System.out.println("Cleaning");
			current.setProgress(100);
			current.setStatus(Transfer.CMPLT);
			currentHandler.close();
			inUse.remove();
			
			if (currentHandler.getType() == MsgHandler.DATAPACK) {
				System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERE");
				makeCmd(MsgHandler.ENDFILE, "");
				
				return;
			} else if (currentHandler.getType() != MsgHandler.ENDFILE) {
				return;
			}
		}
		
		
		if (currentHandler.getType() == MsgHandler.ENDFILE)
			System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEND");
		this.sockBuf.clear();
		this.sockBuf.putInt(bytes);
		this.sockBuf.putInt(currentHandler.getType());
		
	//	System.out.println("Sockbuf Pos " + this.sockBuf.position() + " of " + this.sockBuf.limit());
		this.sockBuf.put(this.msgBuf);
		
		this.sockBuf.flip();
		//System.out.println("Pos SOCKBUF " + this.sockBuf.position() + " of " + this.sockBuf.remaining());
		
		this.socket.write(this.sockBuf);
		
		if (bytes != 0 && currentHandler.getType() == MsgHandler.DATAPACK) {
			current.setProgress(current.getProgress() + (double)bytes * 100 / ((FileMsgHandler)currentHandler).size);
		}
		
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