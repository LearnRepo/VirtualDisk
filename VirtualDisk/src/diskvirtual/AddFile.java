package diskvirtual;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * :: File_Manager
 * Load IndexBit (1 byte), NextOffset+SpaceLeft(2 x 4 byte)
 * Read IndexBit
 * check SpaceLeft
 * Read NextOffset
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
			String filename = "file1";
			long fileLength = aFile.length();
			int dataLength = data.length();
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
			
			//System.out.print(IndexBit+" "+Sbit+" "+Mbit+" "+Kbit+" "+Bbit);
			//System.out.print(this.decodeAddress(this.decodeSignificant((byte) Sbit), Mbit, Kbit, Bbit));
			if(this.decodeAddress(this.decodeSignificant((byte) Sbit), Mbit, Kbit, Bbit)> dataLength)
			{
				/*start write
				 *  increment index
				 *  decode NextOffset
				 *  Goto NextOffset address
				 *  write filename + data
				 *  NextOffset = NextOffset+dataSize+filenameSize
				 *  encode nextOffset
				 *  update
				 *  Size = Size - (dataSize+FileIndex)
				 *  encode size
				 *  update size->SpaceLeft
				 *  encode FileIndex
				 *  Initialize FileIndex Last bit by FilenameSize
				 *  write FileIndex
				 *  decode 
				 */
				aFile.seek(fileLength-1);
				aFile.write((byte)(aFile.read() + 1));
				
				aFile.seek(fileLength-6);
				int OSbit = aFile.read();
				aFile.seek(fileLength-7);
				int OMbit = aFile.read();
				aFile.seek(fileLength-8);
				int OKbit = aFile.read();
				aFile.seek(fileLength-9);
				int OBbit = aFile.read();
				
				aFile.seek(this.decodeAddress(this.decodeSignificant((byte) OSbit), OMbit, OKbit, OBbit));
				
			}
			else
			{
				System.out.print("Not Enough Space");
			}
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
	private int[] decodeSignificant(byte b1)
	{
		int s[] = new int[3];
		s[0] = (b1 & 0x30)>>4;
		s[1] = (b1 & 0x0C)>>2;
		s[2] = (b1 & 0x03);
		//System.out.print(MS+" "+KS+" "+BS);
		return s;
	}
	
	private long decodeAddress(int [] v, int w , int x, int y){
		
		return (v[0]*256+w)*1024*1024+(v[1]*256+x)*1024+(v[2]*256+y);
	}
}
