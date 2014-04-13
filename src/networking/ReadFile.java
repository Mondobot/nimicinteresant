package networking;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFile {
	public void main(String fileName) throws IOException {
				
		File file = new File(fileName);
		long fileLength = file.length();
		RandomAccessFile aFile = new RandomAccessFile(fileName, "rw");

	    FileChannel inChannel = aFile.getChannel();
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
