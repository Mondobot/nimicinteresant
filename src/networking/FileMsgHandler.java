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
	private long remaining;

	
	public FileMsgHandler(int type, String ops, String relativePath, int size) throws IOException {	
		super(type, ops);
		
		File tmpFile = new File(relativePath);
		this.remaining = tmpFile.length();
		
		if (this.getOp().equals("w")  && !tmpFile.exists()) {
			tmpFile.createNewFile();
			this.remaining = size;
		}
		
		try {
			this.aFile = new RandomAccessFile(tmpFile, this.op);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file \"" + relativePath + "\"");
			e.printStackTrace();
		}

	    this.channel = this.aFile.getChannel();
	    this.memBuff = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, this.remaining);

	    this.buf = ByteBuffer.allocate(48);
	}

	@Override
	public int read(ByteBuffer into) {
		if (!this.op.equals("r"))
			return -1;
	    
		into.clear();
		int bytesRead = 0;		
		if (into.remaining() < this.memBuff.remaining()) {
			bytesRead = this.memBuff.remaining();
			
			while (into.hasRemaining() && this.memBuff.hasRemaining())
				into.put(memBuff.get());
			
		} else {
			bytesRead = into.remaining();
			
			into.put(this.memBuff);
		}
	
		into.flip();	
		return bytesRead;
	}

	@Override
	public void write(ByteBuffer from) {
		if (!this.op.equals("w"))
			return;
		
		if (from.remaining() > this.memBuff.remaining()) {
			this.memBuff.put(from);
		
		} else {
			while (this.memBuff.hasRemaining() && from.hasRemaining())
				this.memBuff.put(from.get());
		}
	}

	@Override
	public void setOp(String ops) {
		if (ops.equals("w")) {
			this.op = "w";
			
		
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
