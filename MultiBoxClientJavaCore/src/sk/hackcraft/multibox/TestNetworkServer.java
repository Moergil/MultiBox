package sk.hackcraft.multibox;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestNetworkServer
{
	public static void main(String[] args)
	{
		while (true)
		{
			try
			{
				ServerSocket serverSocket = null;
				Socket socket = null;
				
				try
				{
					serverSocket = new ServerSocket(13110);
					serverSocket.setSoTimeout(3000);
					
					socket = serverSocket.accept();
					DataInput input = new DataInputStream(socket.getInputStream());
					
					byte value = input.readByte();
					
					System.out.println("Received " + value);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (serverSocket != null)
					{
						serverSocket.close();
					}
					
					if (socket != null)
					{
						socket.close();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				
			}
			
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				
			}
		}
	}
}
