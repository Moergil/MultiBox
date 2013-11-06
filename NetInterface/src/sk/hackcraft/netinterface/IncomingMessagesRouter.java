package sk.hackcraft.netinterface;

import java.util.HashMap;
import java.util.Map;

public class IncomingMessagesRouter
{
	private final Map<MessageType, MessageReceiver> receivers;
	
	public IncomingMessagesRouter()
	{
		receivers = new HashMap<MessageType, MessageReceiver>();
	}
	
	public synchronized void addReceiver(MessageType type, MessageReceiver receiver)
	{
		receivers.put(type, receiver);
	}
	
	public void receiveMessage(int type, byte content[])
	{
		MessageReceiver receiver;
		
		synchronized (this)
		{
			receiver = receivers.get(type);
		}
		
		receiver.receive(content);
	}
}
