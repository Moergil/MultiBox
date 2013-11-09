package sk.hackcraft.multibox;

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
				
				DataOutput output = new DataOutputStream(socket.getOutputStream());
				
				String hello = "hello!";
				
				byte helloBytes[] = hello.getBytes("UTF-8");
				
				output.writeInt(1);
				output.writeInt(helloBytes.length);
				
				output.writeInt(helloBytes.length);
				output.write(helloBytes);
				
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
