import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class BufferTest {

	public static void main(String[] args) throws IOException {
		
		// wrap byte-oriented System.in into a character-oriented reader
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		
		// buffer to hold data
		ByteBuffer buf = ByteBuffer.allocateDirect(128);

		// channel for in
		ReadableByteChannel inChannel = Channels.newChannel(System.in);

		// channel for out
		WritableByteChannel outChannel = Channels.newChannel(System.out);

		// system-specific line separator - ex: \n
		final String NEW_LINE = System.getProperty("line.separator");
		
		RandomAccessFile raf	= new RandomAccessFile("gigi.txt", "rw");		// file
		FileChannel fc			= raf.getChannel();		// associated file channel
		MappedByteBuffer memBuf	= fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size());	// associated memory mapping
			
		try {
			// TODO 1.5: open file and file channel
			
			
			// TODO 1.6: map file into memory
			
			while (true) {
				//System.out.print("> ");

				// read a line from console
				//String line = inReader.readLine();
				
				// echo it
				//System.out.println(line);
				
				// 1-3
				/*buf.clear();
				System.out.println(buf);
				buf.put(line.getBytes());
				System.out.println(buf);
				buf.put(NEW_LINE.getBytes());
				System.out.println(buf);
				buf.flip();
				System.out.println(buf);
				
				outChannel.write(buf);*/
				
				// 4
				/*buf.clear();
				inChannel.read(buf);
				buf.flip();*/
				
				
				
				/*System.out.println(buf);*/
				//5
				/*fc.write(buf);      */
				inChannel.read(memBuf);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			fc.close();
		}
	}

}
