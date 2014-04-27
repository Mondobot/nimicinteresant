package networking;

import java.io.IOException;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.PriorityQueue;

import model.Transfer;

public class SocketHandler extends Thread{
	int BUF_SIZE = 55;
	SocketChannel socket = null;
	Selector selector = null;
	ByteBuffer sockBuf, msgBuf;
	PriorityQueue<Transfer> sendQ, recvQ;
	boolean active;
	
	public SocketHandler(ServerSocketChannel serverSocketChannel) throws IOException {
		this.active = true;
		
		this.socket = serverSocketChannel.accept();				
		this.socket.configureBlocking(false);
		
//		ByteToC
	//	this.sockBuf = new CharBuffer() ;//= StringBuffer.allocateDirect(BUF_SIZE);
		//this.msgBuf = new StringBuffer();//= StringBuffer.allocateDirect(BUF_SIZE - 5);
				
		this.sockBuf = ByteBuffer.allocate(BUF_SIZE);
		this.msgBuf = ByteBuffer.allocate(BUF_SIZE - 5);
		this.selector = Selector.open();
		
		this.socket.register(this.selector, SelectionKey.OP_READ, this.sockBuf);
		
		this.sendQ = new PriorityQueue<Transfer>();
		this.recvQ = new PriorityQueue<Transfer>();
		
		System.out.println("Connection from: " + this.socket.socket().getRemoteSocketAddress());
	}
	
	private void parseMessage(){
		System.out.println(this.sockBuf.position());
		System.out.println("Message size: " + this.sockBuf.get());
		System.out.println("Message type: " + this.sockBuf.getChar());
		
		System.out.println("Actual size: " + this.sockBuf.capacity() + "\n");
	}
	
	public void recv() throws IOException {
		int bytes;
		//WritableByteChannel outChannel = Channels.newChannel(System.out);
		
		this.sockBuf.clear();
		
		// TODO 2.8: read from socket into buffer, use a loop
		while ((bytes = this.socket.read(this.sockBuf)) > 0){
			/*if (!buf.hasRemaining()) {
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
			System.out.println(buf);
			*/
			System.out.println("We read " + bytes + " from  the socket!");
			this.sockBuf.flip();
			parseMessage();
		}
	}
	
	public void send() throws IOException {
		Transfer current = this.sendQ.peek();
		MsgHandler currentHandler = current.getCargo();
		
		int bytes = currentHandler.read(this.msgBuf);
		this.sockBuf.clear();
		this.sockBuf.putInt(bytes);
		this.sockBuf.putInt(currentHandler.getType());
		this.sockBuf.put(this.msgBuf);
		this.sockBuf.flip();
		
		this.socket.write(this.sockBuf);
	}
	
	public void run() {
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
					} catch (IOException e) {
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