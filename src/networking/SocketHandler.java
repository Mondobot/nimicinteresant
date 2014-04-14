package networking;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.PriorityQueue;

import model.Transfer;

public class SocketHandler extends Thread{
	int BUF_SIZE = 55;
	SocketChannel socket;
	Selector selector;
	ByteBuffer sockBuf, msgBuf;
	PriorityQueue<Transfer> sendQ, recvQ;
	
	public SocketHandler(ServerSocketChannel serverSocketChannel) throws IOException {
		this.socket = serverSocketChannel.accept();				

		this.socket.configureBlocking(false);
		this.sockBuf = ByteBuffer.allocateDirect(BUF_SIZE);
		this.msgBuf = ByteBuffer.allocateDirect(BUF_SIZE - 5);
		
		this.socket.register(this.selector, SelectionKey.OP_READ, this.sockBuf);
		
		this.sendQ = new PriorityQueue<Transfer>();
		this.recvQ = new PriorityQueue<Transfer>();
		
		System.out.println("Connection from: " + this.socket.socket().getRemoteSocketAddress());
	}
	
	private void parseMessage(){
		System.out.println("Message size: " + this.sockBuf.getInt());
		System.out.println("Message type: " + this.sockBuf.getInt());
		
		System.out.println("Actual size: " + this.sockBuf.capacity() + "\n");
	}
	
	public void recv(SelectionKey key) throws IOException {
		int bytes;
		//WritableByteChannel outChannel = Channels.newChannel(System.out);
		
		this.msgBuf.clear();
		
		// TODO 2.8: read from socket into buffer, use a loop
		while ((bytes = this.socket.read(this.msgBuf)) > 0){
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
	
	public void send(SelectionKey key) throws IOException {
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
		
	}	
}
