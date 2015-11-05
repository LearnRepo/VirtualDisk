package diskvirtual;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * :: File_Manager
 * Load IndexBit (1 byte), NextOffset+SpaceLeft(8 x 4 byte)
 * Read IndexBit
 * Read NextOffset
 * check SpaceLeft
 * Traverse FileIndex to value(IndexBit)*(24)
 * Size <- Calculate(data)
 * Update FileIndex (Name, Offset, Size) 
 * 			 NextOffSet-> Offset
 * 			 Offset -> NextOffset + Size
 * 			 SpaceLeft -> SpaceLeft - Size
 * Update NextOffset+SpaceLeft
 * Increment IndexBit
 * 
 * Size :: Calculate(data)
 * length = data.length;
 * if(length
 */

public class AddFile {

	public AddFile(String filename, String data, String diskpath)
	{
		
	}
	public AddFile(){
		
	}
	public void ReadDisk()
	{
		RandomAccessFile aFile;
		try{
			aFile     = new RandomAccessFile("D:\\VD\\vd1.shd", "rw");
			FileChannel      inChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024*1024);

			int bytesRead;
			do{
				bytesRead= inChannel.read(buf);
			}while (bytesRead != -1 && buf.hasRemaining());
			System.out.print(bytesRead);
			aFile.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
	}
}
