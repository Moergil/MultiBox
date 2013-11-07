package sk.hackcraft.multibox;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;

public class TestNetwork
{
	public static void main(String[] args)
	{
		int i = 1;
		
		while (true)
		{
			System.out.println("Test no. " + i);
			try
			{
				Socket socket = new Socket("192.168.1.12", 13110);
				socket.setSoTimeout(10000);
				
				DataOutput output = new DataOutputStream(socket.getOutputStream());
				
				System.out.println("Sended 42");
				output.writeInt(41);
				
				/*DataInput input = new DataInputStream(socket.getInputStream());
				
				System.out.print("Received: ");
				System.out.println(input.readInt());*/
				
				//socket.close();
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
