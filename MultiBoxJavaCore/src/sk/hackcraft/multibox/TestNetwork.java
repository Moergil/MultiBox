package sk.hackcraft.multibox;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.BitSet;

public class TestNetwork
{
	public static void main(String[] args)
	{
		System.out.println("Sending simple message, with header (1), content size, and content.");
		System.out.println("Content: int (utf string bytes count) and byte array");
		
		int i = 1;
		
		while (true)
		{
			System.out.println("Test no. " + i);
			try
			{
				Socket socket = new Socket("localhost", 13110);
				//Socket socket = new Socket("localhost", 13110);
				socket.setSoTimeout(10000);
				socket.setTcpNoDelay(true);
				
				DataOutput outputData = new DataOutputStream(socket.getOutputStream());
				
				String hello = "hello!";

				byte helloBytes[] = hello.getBytes("UTF-8");
				
				outputData.writeInt(1);
				outputData.writeInt(helloBytes.length);
				
				ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
				DataOutput contentDataOutput = new DataOutputStream(contentStream);
				
				contentDataOutput.writeInt(helloBytes.length);
				contentDataOutput.write(helloBytes);
				
				outputData.write(contentStream.toByteArray());
				
				socket.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
			}
			
			i++;
		}
	}
}
