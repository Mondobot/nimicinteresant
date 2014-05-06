package networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileMsgHandler extends MsgHandler{
	private RandomAccessFile aFile;
	private FileChannel channel;
	private MappedByteBuffer memBuff;
	private ByteBuffer buf;
	long remaining;
	long size;

	
	public FileMsgHandler(int type, String ops, String relativePath, int size) throws IOException {	
		super(type, ops);
		System.out.println("Path " + relativePath);
		File tmpFile = new File(relativePath);
		this.remaining = tmpFile.length();
		this.size = this.remaining;
		
		if (this.getOp().equals("rw")  && !tmpFile.exists()) {
			tmpFile.createNewFile();
			this.remaining = size;
			this.size = size;
		}
		
		try {
			this.aFile = new RandomAccessFile(tmpFile, this.op);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file \"" + relativePath + "\"");
			e.printStackTrace();
		}

	    this.channel = this.aFile.getChannel();
	    if (this.getOp().equals("rw"))
	    	this.memBuff = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, this.remaining);
	    else
	    	this.memBuff = this.channel.map(FileChannel.MapMode.READ_ONLY, 0, this.remaining);
	    

	    this.buf = ByteBuffer.allocate(55);
	    
	    System.out.println("New file with size " + this.size + " " + this.remaining);
	}

	@Override
	public int read(ByteBuffer into) {
		if (!this.op.equals("r"))
			return -1;
	    
		into.clear();
		int bytesRead = 0;		
		if (into.remaining() > this.memBuff.remaining()) {
			bytesRead = this.memBuff.remaining();
			
			while (into.hasRemaining() && this.memBuff.hasRemaining())
				into.put(this.memBuff.get());
			
		} else {
			bytesRead = into.remaining();
			
			into.put(this.memBuff);
		}
	
		into.flip();
		this.remaining -= bytesRead;
		//System.out.println("Read remaining " + this.remaining);
		return bytesRead;
	}

	@Override
	public int write(ByteBuffer from) {
		if (!this.op.equals("rw"))
			return 0;
		
		int bytesWritten = from.remaining();
		System.out.println("Remaining " + bytesWritten);
		
		if (from.remaining() < this.memBuff.remaining()) {
			this.memBuff.put(from);
		
		} else {
			while (this.memBuff.hasRemaining() && from.hasRemaining())
				this.memBuff.put(from.get());
		}
		
		this.remaining -= bytesWritten;
		
		System.out.println("Write remaining " + this.remaining);
		
		if (this.size != 0)
			return bytesWritten * 100 / (int)this.size ;
		
		return 0;
	}

	@Override
	public void setOp(String ops) {
		if (ops.equals("rw")) {
			this.op = "rw";
			
		
		} else if (ops.equals("r")) {
			this.op = "r";
		}
	}

	@Override
	public String getOp() {
		return this.op;
	}
	
	public void close() {
		 try {
			this.aFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
