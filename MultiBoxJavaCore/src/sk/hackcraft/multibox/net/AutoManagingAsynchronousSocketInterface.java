package sk.hackcraft.multibox.net;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.netinterface.AsynchronousSocketInterface;
import sk.hackcraft.netinterface.IncomingMessagesRouter;
import sk.hackcraft.netinterface.Message;
import sk.hackcraft.netinterface.MessageInterface;
import sk.hackcraft.netinterface.MessageReceiver;
import sk.hackcraft.netinterface.MessageType;
import sk.hackcraft.netinterface.SocketMessageInterface;
import sk.hackcraft.netinterface.AsynchronousSocketInterface.MessageSendListener;
import sk.hackcraft.util.MessageQueue;

public class AutoManagingAsynchronousSocketInterface implements AsynchronousSocketInterface
{
	private AsynchronousSocketInterface.SeriousErrorListener seriousErrorListener;
	
	private final String address;
	private final MessageQueue messageQueue;
	
	private BlockingQueue<MessageEntry> pendingMessages;
	private IncomingMessagesRouter incomingMessagesRouter;

	private MessageInterface messageInterface;
	
	private Thread senderWorkerThread;
	private Thread receiveWorkerThread;
	private Thread usageGuarderThread;
	
	private boolean active;
	private boolean cleaning;
	
	private volatile long lastNetworkActivity;
	private static final int NETWORK_INACTIVITY_TIMEOUT = 10000;
	
	public AutoManagingAsynchronousSocketInterface(String address, MessageQueue messageQueue)
	{
		this.address = address;
		this.messageQueue = messageQueue;
		
		this.incomingMessagesRouter = new IncomingMessagesRouter();
		
		this.active = true;
		this.cleaning = false;
	}
	
	@Override
	public void setSeriousErrorListener(SeriousErrorListener listener)
	{
		this.seriousErrorListener = listener;
	}

	@Override
	public void sendMessage(Message message)
	{
		sendMessage(message, null);
	}
	
	@Override
	public void sendMessage(Message message, MessageSendListener listener)
	{
		MessageEntry entry = new MessageEntry(message, listener);
		pendingMessages.add(entry);
		
		onNewPendingMessage();
	}

	@Override
	public void setMessageReceiver(MessageType messageType, MessageReceiver receiver)
	{
		incomingMessagesRouter.setReceiver(messageType, receiver);
	}
	
	private boolean isNetworkingActive()
	{
		// TODO synchronize
		return messageInterface != null;
	}
	
	private boolean isNetworkingSpawning()
	{
		return true; // TODO added synchronized guard variables
	}

	private synchronized void onNewPendingMessage()
	{
		if (!active)
		{
			spawnNetworking();
			active = false;
		}
	}
	
	private void spawnNetworking()
	{
		Runnable initCode = new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					lastNetworkActivity = System.currentTimeMillis();
					
					Socket socket = new Socket(address, 13110);
					messageInterface = new SocketMessageInterface(socket);
					
					usageGuarderThread = new Thread(new UsageGuarder());
					usageGuarderThread.start();
					
					senderWorkerThread = new Thread(new SenderWorker());
					senderWorkerThread.start();
					
					receiveWorkerThread = new Thread(new ReceiveWorker());
					receiveWorkerThread.start();
				}
				catch (Exception e)
				{
					if (seriousErrorListener != null)
					{
						seriousErrorListener.onSeriousError();
					}
				}
			}
		};
		
		new Thread(initCode).start();
	}
	
	private void cleanNetworking()
	{
		synchronized (this)
		{
			cleaning = true;
		}
		
		senderWorkerThread.interrupt();
		receiveWorkerThread.interrupt();
		
		senderWorkerThread = null;
		receiveWorkerThread = null;
		usageGuarderThread = null;
		
		synchronized (AutoManagingAsynchronousSocketInterface.this)
		{
			notifyAll();
			cleaning = false;
			active = true;
		}
	}
	
	private class MessageEntry
	{
		private final Message message;
		private final MessageSendListener sendListener;
		
		public MessageEntry(Message message, MessageSendListener sendListener)
		{
			this.message = message;
			this.sendListener = sendListener;
		}
		
		public Message getMessage()
		{
			return message;
		}
		
		public MessageSendListener getSendListener()
		{
			return sendListener;
		}
	}
	
	private class SenderWorker implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				MessageEntry entry;
				
				try
				{
					entry = pendingMessages.take();
				}
				catch (InterruptedException e)
				{
					return;
				}
				
				Message message = entry.getMessage();
				
				boolean sendingResult;
				try
				{
					messageInterface.sendMessage(message);
					sendingResult = true;
				}
				catch (IOException e)
				{
					sendingResult = false;
				}

				final MessageSendListener listener = entry.getSendListener();
				if (listener != null)
				{
					final boolean finalSendingResult = sendingResult;
					
					messageQueue.post(new Runnable()
					{
						@Override
						public void run()
						{
							listener.onFinish(finalSendingResult);
						}
					});
				}
			}
		};
	}
	
	private class ReceiveWorker implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				Message message;
				
				try
				{
					message = messageInterface.waitForMessage();
				}
				catch (IOException e)
				{
					
				}
			}
		}
	}
	
	private class UsageGuarder implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				if (lastNetworkActivity + NETWORK_INACTIVITY_TIMEOUT < System.currentTimeMillis())
				{
					cleanNetworking();
					
					return;
				}
				else
				{
					try
					{
						wait(TimeUnit.SECONDS.toMillis(1));
					}
					catch (InterruptedException e)
					{
						return;
					}
				}
			}
		}
	}
}
