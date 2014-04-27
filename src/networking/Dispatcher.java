package networking;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher extends Thread{
	
	public static final int BUF_SIZE	= 4;			// buffer size
	public static final String IP		= "127.0.0.1";	// server IP
	public static final int PORT		= 30009;		// server port
	private ArrayList<SocketHandler> socketHandlers = new ArrayList<SocketHandler>();
	
	public static ExecutorService pool = Executors.newFixedThreadPool(5);	// thread pool - 5 threads
	
	public void accept(SelectionKey key) throws IOException {
		System.out.print("ACCEPT: ");
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel(); 
		
		System.out.println("Cevaa");
		SocketHandler socketHandler = new SocketHandler(serverSocketChannel);
		
		System.out.println("Altceva");
		socketHandler.run();
		socketHandlers.add(socketHandler);
		
	}
	
	public void run() {
		Selector selector						= null;
		ServerSocketChannel serverSocketChannel	= null;
		
		try {
			selector = Selector.open();
			
			// TODO 2.3: init server socket and register it with the selector
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(IP, PORT));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			 
			SocketChannel socketChannel = serverSocketChannel.accept();              
						
			while (true) {
				selector.select();
				
				// iterate over the events
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
					// get current event and REMOVE it from the list!!!
					SelectionKey key = it.next();
					it.remove();
					
					if (key.isAcceptable())
						accept(key);
					else 
						System.out.println("Error! Bad request on dispatcher!");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			// cleanup
			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {}
			
			if (serverSocketChannel != null)
				try {
					serverSocketChannel.close();
				} catch (IOException e) {}
		}
	}
	
	public static void main(String args[]) {
		System.out.println("Hehe");
		Dispatcher x = new Dispatcher();
		x.run();
	}
}
