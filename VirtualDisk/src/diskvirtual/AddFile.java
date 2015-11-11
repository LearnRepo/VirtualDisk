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
		fileWriteTest();
	}
	public void ReadDisk()
	{
		RandomAccessFile aFile;
		try{
			aFile     = new RandomAccessFile("D:\\VD\\vd1.shd", "rw");
			FileChannel      inChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(5);
			//aFile.seek(1024*1024*1024-1);
			aFile.seek(0);
			System.out.print(aFile.read());
			//Comment code return number of byte read through bytebuffer allocated
			/*int bytesRead;
			do{
				bytesRead= inChannel.read(buf);
			}while (bytesRead != -1 && buf.hasRemaining());
			System.out.print(bytesRead);*/
			aFile.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
	}
	private void fileWriteTest()
	{
		RandomAccessFile aFile;
		try{
			aFile     = new RandomAccessFile("D:\\VD\\vd1.shd", "rw");
			String data= "Hello I am only person exist in world";
			long fileLength = aFile.length();
			/*byte b1 = (byte)255;
			System.out.print(orginalByte(b1)+" ");*/
			aFile.seek(fileLength-1);
			int IndexBit = aFile.read();	//Index bit
			aFile.seek(fileLength-2);
			int Sbit = aFile.read();
			aFile.seek(fileLength-3);
			int Mbit = aFile.read();
			aFile.seek(fileLength-4);
			int Kbit = aFile.read();
			aFile.seek(fileLength-5);
			int Bbit = aFile.read();
			System.out.print(IndexBit+" "+Sbit+" "+Mbit+" "+Kbit+" "+Bbit);
			
			aFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	private int orginalByte(byte b1)
	{
		if(b1<0)
		return (256- (~(b1-1)));
		else
			return b1;
	}
	private int IntegerSplitFour(byte b1)
	{
		int NextoffsetSignificantM = b1 & 0x30>>4;
		int NextoffsetSignificantK = b1 & 0xC0>>6;
		int SpaceLeftSignificantM = b1 & 0x03;
		int SpaceLeftSignificantK = b1 & 0x0C>>2;
		return NextoffsetSignificantM;
	}
}
