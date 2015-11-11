package diskvirtual;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Get the size of disk by user - done
 * Zero the amount of space - done
 * Allocate Index bit. Index bit tell the index length
 * Index Bit must be at end of the file.
 * Give the File name to Disk Name known as Disk Name
 * Apply the NTFS security 
 */

public class MakeDisk {
	public MakeDisk(String name, int size, String Byte) throws IOException
	{
		System.out.println("Making Disk...");
		switch(Byte)
		{
		case "MB":
			if( size<=1024)
			size=1024*1024*size;
			else
			{
			System.out.print("Error: size exceed, max size can be 1GB");
			return;
			}
			break;
		case "KB":
			if(size>=100 && size<=1024*1024)
			size=1024*size;
			else
			{
			System.out.print("Error: size exceed, max size can be 1GB or cannot less than 100 KB");
			return;
			}
			break;
		case "GB":
			if(size<=1)
			size=1024*1024*1024*size;
			else
			{
			System.out.print("Error: size exceed, max size can be 1GB");
			return;
			}
			break;
		}
		FileOutputStream out = null;
		byte c[];
		int div;
		if(Byte =="GB" || Byte=="MB")
		{
		div =1024;
		c =  new byte[size/div];
		for(int i=0;i<c.length;i++)
		c[i] = 0x00;
		}
		else
		{
			c =  new byte[size];
			div = 1;
			for(int i=0;i<c.length;i++)
				c[i] = 0x00;
		}
		try {
            out = new FileOutputStream(name);
            for(int i=0;i<div;i++)
            out.write(c);
            System.out.println("Successfully Disk Created!");
        } 
		finally {
        	if (out != null) {
                out.close();
            }
        }
		System.out.println("Preparing disk for use...");
		prepareDisk(size-9, name);
		System.out.println("Disk is ready!");
		
	}
	private void prepareDisk(long size, String name)
	{
		long temp, tempMS, tempKS,tempBS;
		byte SBit;
		byte Mbit,Kbit,Bbit;
		//System.out.println(size);
		temp = size/(1024*1024);
		//System.out.println("Size in MB:"+temp/256+" "+temp%256);
		tempMS = temp/256;
		Mbit = (byte)(temp%256);
		temp = (size%(1024*1024))/(1024);
		//System.out.println("Size in KB:"+temp/256+" "+temp%256);
		tempKS = temp/256;
		Kbit = (byte)(temp%256);
		temp = (size%(1024*1024))%(1024);
		//System.out.println("Size in Byte:"+temp/256+" "+temp%256);
		tempBS = temp/256;
		Bbit = (byte)(temp%256);
		SBit = encodeSignificantBit(tempMS,tempKS,tempBS);
		System.out.print("S:"+orginalByte(SBit)+"M:"+orginalByte(Mbit)+"K:"+orginalByte(Kbit)+"B:"+orginalByte(Bbit));
		RandomAccessFile aFile;
		try{
			aFile     = new RandomAccessFile(name, "rw");
			aFile.seek(aFile.length()-2);
			aFile.write(SBit);
			aFile.seek(aFile.length()-3);
			aFile.write(Mbit);
			aFile.seek(aFile.length()-4);
			aFile.write(Kbit);
			aFile.seek(aFile.length()-5);
			aFile.write(Bbit);
			aFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private byte encodeSignificantBit(long w, long x, long y)
	{
		w = w << 4;
		x = x << 2;
		return (byte) (w|x|y);
	}
	private int orginalByte(byte b1)
	{
		if(b1<0)
		return (256- (~(b1-1)));
		else
			return b1;
	}
}
