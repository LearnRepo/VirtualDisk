package diskvirtual;

import java.io.FileOutputStream;
import java.io.IOException;

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
            out = new FileOutputStream("D:\\VD\\"+name);
            for(int i=0;i<div;i++)
            out.write(c);
            System.out.println("Successfully Disk Created!");
        } 
		finally {
        	if (out != null) {
                out.close();
            }
        }
		System.out.println("Preapre disk for use...");
		prepareDisk(size-6);
	}
	private void prepareDisk(long size)
	{
		long temp;
		System.out.println(size);
		temp = size/(1024*1024);
		System.out.println("Size in MB:"+temp/256+" "+temp%256);
		temp = (size%(1024*1024))/(1024);
		System.out.println("Size in KB:"+temp/256+" "+temp%256);
		temp = (size%(1024*1024))%(1024);
		System.out.println("Size in Byte:"+temp/256+" "+temp%256);
		
	}
}
