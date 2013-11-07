package sk.hackcraft.netinterface;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class SocketInterface
{	
	private final Socket socket;
	
	protected final SendWorker sendWorker;
	protected final ReceiveWorker receiveWorker;
	
	public SocketInterface(Socket socket, IncomingMessagesRouter incomingMessagesRouter) throws IOException
	{
		this.socket = socket;
		
		socket.setTcpNoDelay(true);
		
		DataInput input = new DataInputStream(socket.getInputStream());
		DataOutput output = new DataOutputStream(socket.getOutputStream());
		
		receiveWorker = new ReceiveWorker(input, incomingMessagesRouter);
		sendWorker = new SendWorker(output);
	}
	
	public void start()
	{
		new Thread(receiveWorker).start();
		new Thread(sendWorker).start();
	}
	
	public void stop()
	{
		receiveWorker.stop();
		sendWorker.stop();
		
		if (socket != null)
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
			}
		}
	}
	
	public void sendMessage(Message message)
	{
		sendWorker.sendMessage(message);
	}
	
	protected abstract void onDisconnect();
	
	private class SendWorker extends StoppableRunnable
	{
		private final DataOutput output;	
		private final BlockingQueue<Message> waitingMessages;

		public SendWorker(DataOutput output)
		{
			this.output = output;
			waitingMessages = new LinkedBlockingQueue<Message>();
		}
		
		@Override
		public void stop()
		{
			super.stop();
			
			Thread.currentThread().interrupt();
		}

		public void sendMessage(Message message)
		{
			waitingMessages.add(message);
		}

		@Override
		public void run()
		{
			try
			{
				while (!isStopped())
				{
					Message message = waitingMessages.take();
					
					int typeId = message.getType().toTypeId();
					output.writeInt(typeId);
					
					byte content[] = message.getContent();
					
					output.writeInt(content.length);
					output.write(content);
				}
			}
			catch (IOException e)
			{
				if (!isStopped())
				{
					onDisconnect();
				}
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	private class ReceiveWorker extends StoppableRunnable
	{
		private final DataInput input;
		private final IncomingMessagesRouter incomingMessagesRouter;

		public ReceiveWorker(DataInput input, IncomingMessagesRouter incomingMessagesRouter)
		{
			this.input = input;
			this.incomingMessagesRouter = incomingMessagesRouter;
		}
		
		@Override
		public void run()
		{
			try
			{
				while (!isStopped())
				{
					int messageType = input.readInt();
					
					int contentSize = input.readInt();
					byte content[] = new byte[contentSize];
					input.readFully(content);
					
					incomingMessagesRouter.receiveMessage(messageType, content);
				}
			}
			catch (Exception e)
			{
				if (!isStopped())
				{
					onDisconnect();
				}
			}
		}
	}
}
