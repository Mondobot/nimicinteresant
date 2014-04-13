package networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileHandler {
	RandomAccessFile afile;
	FileChannel channel;
	long length;
	String op;
	
	
	public FileHandler(String relativePath, String ops) {	
		File tmpFile = new File(relativePath);
		this.length = tmpFile.length();
		
		if (ops.equals("w")) {
			this.op = "w";
			if (!tmpFile.exists()) {
				tmpFile.createNewFile();
			}
		
		} else if (ops.equals("r")) {
			this.op = "r";
		}
		
		try {
			this.aFile = new RandomAccessFile(tmpFile, this.op);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid file \"" + relativePath + "\"");
			e.printStackTrace();
		}

	    this.channel = this.aFile.getChannel();
	    MappedByteBuffer memBuf	= inChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

	    ByteBuffer buf = ByteBuffer.allocate(48);

	    int bytesRead = inChannel.read(buf);
	    while (bytesRead != -1) {

	      System.out.println("Read " + bytesRead);
	      buf.flip();

	      while(buf.hasRemaining()){
	          System.out.print((char) buf.get());
	      }

	      buf.clear();
	      bytesRead = inChannel.read(buf);
	    }
	    aFile.close();
	}
}
