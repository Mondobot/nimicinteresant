package networking;
import java.io.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mediator.Mediator;
import model.User;

public class Dispatcher extends Thread{
	
	//public static final int BUF_SIZE	= 4;			// buffer size
	public String IP;// = "127.0.0.1";	// server IP
	public int PORT	= 30008;		// server port
	
	private Mediator mediator;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;	
	private ArrayList<SocketHandler> socketHandlers = new ArrayList<SocketHandler>();
	
	
	public Dispatcher (Mediator mediator) {
		this.mediator = mediator;
		this.IP = mediator.getMyUser().getIP();
		//this.PORT = mediator.getMyUser().getPORT();
		//this.PORT = 30008;
	}
	
	public static ExecutorService pool = Executors.newFixedThreadPool(5);	// thread pool - 5 threads
	
	public void accept(SelectionKey key) throws IOException {
		System.out.print("ACCEPT: ");
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel(); 
		
		SocketHandler socketHandler = new SocketHandler(this.mediator);
		SocketChannel newSocket = serverSocketChannel.accept();
		
		String remAddr = ((InetSocketAddress)newSocket.getRemoteAddress()).getAddress().getHostAddress();
		int remPort = ((InetSocketAddress)newSocket.getRemoteAddress()).getPort();
		
		if (!this.mediator.hasUser(remAddr, remPort))
			return;
		newSocket.configureBlocking(false);
		socketHandler.setSocket(newSocket);
		socketHandlers.add(socketHandler);
		socketHandler.start();
	}
	
	private void connect(SelectionKey key) throws Exception {
		SocketChannel socket = (SocketChannel) key.channel();
		
		if (socket.finishConnect()) {
			SocketHandler socketHandler = new SocketHandler(this.mediator);
			
			socketHandler.setSocket(socket);
			socketHandlers.add(socketHandler);
			
			socketHandler.makeCmd(MsgHandler.GETID, Integer.toString(this.mediator.getMyUser().getId()));
			socketHandler.start();
			
			
		} else {
			System.out.println("Failed to connect");
		}
	}
	
	public void connectTo(final String IP, final int PORT) throws Exception {
		for (SocketHandler i:this.socketHandlers) {
			if (((InetSocketAddress)i.socket.getRemoteAddress()).getAddress().getHostAddress().equals(IP) &&
				((InetSocketAddress)i.socket.getRemoteAddress()).getPort() == PORT)
				return;
		}
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
	
	public void maine(String args[]) {
		System.out.println("Hehe");
		Dispatcher x = new Dispatcher(this.mediator);
		x.start();
		
		System.out.println("mamaliga");
		try {
			sleep(1000);
			System.out.println("Outer");
			x.connectTo("127.0.0.1", 30008);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("dim: " + x.socketHandlers.size());
		x.socketHandlers.get(1).makeCmd(MsgHandler.GETFILE, "test");
		x.socketHandlers.get(0).makeCmd(MsgHandler.GETFILE, "enya");
		//x.socketHandlers.get(0).makeCmd(MsgHandler.GETFILE, "test");
		
		try {
			sleep(12000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (x.socketHandlers.get(1).recvQ.peek() != null)
			System.out.println("Not quitting :(");
		
		else
			System.out.println("works!");
		//x.socketHandlers.get(0).makeCmd(MsgHandler.GETFILE, "test");
		//FileMsgHandler rd = new FileMsgHandler(1, "r", "edi.txt", 0);
		//FileMsgHandler  wrt= new FileMsgHandler(1, "rw", "edi.txt_2", 24);
		//ByteBuffer y = ByteBuffer.allocate(50);
		
		//rd.read(y);
		//wrt.write(y);
		//wrt.close();
		
		
		//System.out.println(y.position() + " " + y.remaining());
		//System.out.println(y.getChar() + "_" + y.getChar());
		
		//System.out.println(y.array().toString());
		
		//User ieu = new User(2, "Matei");
		//Transfer tr = new Transfer(MsgHandler.GETNAME, ieu, ieu, cmd, 0, Transfer.UPLD);
		//sleep(4000);
		//x.socketHandlers.get(1).sendQ.add(tr);
		//x.socketHandlers.get(1).makeCmd(MsgHandler.GETNAME, )
		
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

	public void downloadFile(String name) {
		User sel = this.mediator.getSelectedUser();
		for (SocketHandler i:this.socketHandlers)
			try {
				if (((InetSocketAddress)i.socket.getRemoteAddress()).getAddress().getHostAddress().equals(sel.getIP()) &&
						((InetSocketAddress)i.socket.getRemoteAddress()).getPort() == sel.getPORT()) {
					i.makeRequest(name);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
