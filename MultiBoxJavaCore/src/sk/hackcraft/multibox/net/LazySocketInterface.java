package sk.hackcraft.multibox.net;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.netinterface.AsynchronousSocketInterface;
import sk.hackcraft.netinterface.IncomingMessagesRouter;
import sk.hackcraft.netinterface.Message;
import sk.hackcraft.netinterface.MessageReceiver;
import sk.hackcraft.netinterface.MessageType;
import sk.hackcraft.netinterface.OldAsynchronousSocketInterface;
import sk.hackcraft.util.MessageQueue;

public class LazySocketInterface implements AsynchronousSocketInterface
{
	private final MessageQueue messageQueue;
	
	private final String address;
	private final IncomingMessagesRouter incomingMessagesRouter;
	
	private OldAsynchronousSocketInterface socketInterface = null;
	
	private BlockingQueue<Message> pendingMessages;
	
	private volatile boolean run;
	private Thread senderThread;
	
	public LazySocketInterface(MessageQueue messageQueue, String address)
	{
		this.messageQueue = messageQueue;
		
		this.address = address;
		this.incomingMessagesRouter = new IncomingMessagesRouter();
		
		this.pendingMessages = new LinkedBlockingQueue<Message>();
		
		this.run = true;
		senderThread = new Thread(new MessageProcessor());
		senderThread.start();
	}
	
	@Override
	public void setSeriousErrorListener(SeriousErrorListener listener)
	{
		// notify application that something serious has happend 
		// for example when its not possible to open socket
	}
	
	@Override
	public void sendMessage(Message message)
	{
		// add message to pending messages
		// notify underlaying logic that new message is ready to process
	}

	@Override
	public void sendMessage(Message message, MessageSendListener listener)
	{
		// use this, if you need notice about message sending result
	}

	@Override
	public void setMessageReceiver(MessageType messageType, MessageReceiver receiver)
	{
		// registering message receivers
	};
	
	private void processPendingMessages()
	{
		if (socketInterface == null)
		{
			try
			{
				createSocketInterface();
			}
			catch (IOException e)
			{
				
			}
		}
	}
	
	private void createSocketInterface() throws IOException
	{
		Socket socket = new Socket(address, 13110);
		
		socketInterface = new OldAsynchronousSocketInterface(socket, incomingMessagesRouter)
		{
			@Override
			protected void onDisconnect()
			{
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private class MessageProcessor implements Runnable
	{
		@Override
		public void run()
		{
			while (run)
			{
				if (socketInterface == null)
				{
					try
					{
						createSocketInterface();
					}
					catch (IOException e)
					{
						continue;
					}
				}
				
				if (pendingMessages.isEmpty())
				{
					synchronized (this)
					{
						try
						{
							wait();
						}
						catch (InterruptedException e)
						{
							continue;
						}
					}
				}
				
				while (!pendingMessages.isEmpty())
				{
					Message message = pendingMessages.peek();
					
					socketInterface.sendMessage(message);
				}
			}
		}
	}
}
