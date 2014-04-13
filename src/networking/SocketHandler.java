package networking;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class SocketHandler extends Thread{
	
	public SocketHandler(ServerSocketChannel serverSocketChannel) {
/*		SocketChannel socketChannel = serverSocketChannel.accept();				

		socketChannel.configureBlocking(false);
		
		ByteBuffer buf = ByteBuffer.allocateDirect(4);
		socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);
		
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());*/
	}
	
public static void read(SelectionKey key) throws IOException {
		
		System.out.print("READ: ");
		
		int bytes;
		ByteBuffer buf				= (ByteBuffer)key.attachment();		
		SocketChannel socketChannel	= (SocketChannel)key.channel();
		socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);
		
		WritableByteChannel outChannel = Channels.newChannel(System.out);
		
		buf.clear();
		
		// TODO 2.8: read from socket into buffer, use a loop
		while ((bytes = socketChannel.read(buf)) > 0){
			if (!buf.hasRemaining()) {
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
			System.out.println(buf);
		}
		
	}
	
	public static void write(SelectionKey key) throws IOException {
		
		System.out.println("WRITE: ");
		
		ByteBuffer buf				= (ByteBuffer)key.attachment();		
		SocketChannel socketChannel	= (SocketChannel)key.channel();
		socketChannel.register(key.selector(), SelectionKey.OP_WRITE, buf);
		
		socketChannel.write(buf);
		
		// TODO 2.11: write from buffer to socket, use a loop
	}
	
	public void run() {
		
	}	
}
