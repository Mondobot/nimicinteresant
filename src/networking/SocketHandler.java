package networking;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketHandler extends Thread{
	
	public SocketHandler(ServerSocketChannel serverSocketChannel) {
		SocketChannel socketChannel = serverSocketChannel.accept();				
		socketChannel.configureBlocking(false);
		
		ByteBuffer buf = ByteBuffer.allocateDirect(4);
		socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);
		
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
	}
	
	public void run() {
		
	}	
}
