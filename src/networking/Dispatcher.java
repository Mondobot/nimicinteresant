package networking;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Transfer;
import model.User;

public class Dispatcher extends Thread{
	
	public static final int BUF_SIZE	= 4;			// buffer size
	public static final String IP		= "127.0.0.1";	// server IP
	public static final int PORT		= 30009;		// server port
	
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	
	private ArrayList<SocketHandler> socketHandlers = new ArrayList<SocketHandler>();
	
	
	
	public static ExecutorService pool = Executors.newFixedThreadPool(5);	// thread pool - 5 threads
	
	public void accept(SelectionKey key) throws IOException {
		System.out.print("ACCEPT: ");
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel(); 
		
		System.out.println("Cevaa");
		SocketHandler socketHandler = new SocketHandler();
		SocketChannel newSocket = serverSocketChannel.accept();
		newSocket.configureBlocking(false);
		socketHandler.setSocket(newSocket);
		
		
		System.out.println("Altceva");
		socketHandler.start();
		socketHandlers.add(socketHandler);
		
	}
	
	private void connect(SelectionKey key) throws Exception {
		SocketChannel socket = (SocketChannel) key.channel();
		
		if (socket.finishConnect()) {
			SocketHandler socketHandler = new SocketHandler();
			
			socketHandler.setSocket(socket);
			socketHandlers.add(socketHandler);
			socketHandler.start();
			
		} else {
			System.out.println("Filed to connect");
		}
	}
	
	public void connectTo(final String IP, final int PORT) throws Exception {

		SocketChannel socket = SocketChannel.open();
		socket.configureBlocking(false);
		socket.connect(new InetSocketAddress(IP, PORT));	
		socket.register(this.selector, SelectionKey.OP_CONNECT);
	}
	
	public void run() {
		
		try {
			this.selector = Selector.open();
			
			// TODO 2.3: init server socket and register it with the selector
			this.serverSocketChannel = ServerSocketChannel.open();
			this.serverSocketChannel.configureBlocking(false);
			this.serverSocketChannel.socket().bind(new InetSocketAddress(this.IP, this.PORT));
			this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
			 
			SocketChannel socketChannel = this.serverSocketChannel.accept();              
						
			while (true) {
				this.selector.select();
				
				// iterate over the events
				for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext(); ) {
					// get current event and REMOVE it from the list!!!
					SelectionKey key = it.next();
					it.remove();
					
					if (key.isAcceptable())
						accept(key);
					else if (key.isConnectable()) 
						connect(key);

					else
						System.out.println("Error! Bad request on dispatcher!");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	public static void main(String args[]) throws InterruptedException {
		System.out.println("Hehe");
		Dispatcher x = new Dispatcher();
		x.start();
		
		System.out.println("mamaliga");
		try {
			sleep(1000);
			System.out.println("Outer");
			x.connectTo("127.0.0.1", 30009);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Bag pla!");
			e.printStackTrace();
		}
		
		CommandMsgHandler cmd = new CommandMsgHandler(1, "r", "gigi");
		User ieu = new User(2, "Matei");
		Transfer tr = new Transfer(1, ieu, ieu, cmd, 0, Transfer.UPLD);
		sleep(4000);
		x.socketHandlers.get(1).sendQ.add(tr);
		/*try {
			x.socketHandlers.get(0).send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//System.out.println("Connected to: " + x.socketHandlers.get(0).socket.socket().getRemoteSocketAddress());
		//System.out.println("Connected to: " + x.socketHandlers.get(1).socket.socket().getRemoteSocketAddress());
	}
}
