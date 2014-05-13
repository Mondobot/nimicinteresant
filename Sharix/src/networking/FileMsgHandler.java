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
	long remaining;
	long size;

	
	public FileMsgHandler(int type, String ops, String relativePath, long size) throws IOException {	
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
	    if (this.getOp().equals("rw")) {
	    	this.memBuff = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, this.remaining);
	    } else {
	    	this.memBuff = this.channel.map(FileChannel.MapMode.READ_ONLY, 0, this.remaining);
	    }
	}

	@Override
	public int read(ByteBuffer into) {
		if (!this.op.equals("r"))
			return -1;
	    
		into.clear();
		int bytesRead = 0;		
		while (this.memBuff.hasRemaining() && into.hasRemaining())
			into.put(this.memBuff.get());
			
		bytesRead = into.position();
	
		into.flip();
		this.remaining -= bytesRead;
		return bytesRead;
	}

	@Override
	public int write(ByteBuffer from) {
		if (!this.op.equals("rw"))
			return 0;
		
		int bytesWritten = from.remaining();
		
		if (from.remaining() <= this.memBuff.remaining()) {
			this.memBuff.put(from);
		
		} else {
			while (from.hasRemaining() && this.memBuff.hasRemaining())
				this.memBuff.put(from.get());
		}
		
		this.remaining -= bytesWritten;		
		return bytesWritten;
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
		System.out.println("CLOSING");
		 try {
			this.aFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
