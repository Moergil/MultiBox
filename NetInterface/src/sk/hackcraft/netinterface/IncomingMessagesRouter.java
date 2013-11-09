package sk.hackcraft.netinterface;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moergil
 * This class works as simple abstract map, which associates message type
 * with concrete message receiver. Main purpose is to synchronize access
 * to receivers, because they will be mostly used in multithread envirnoment.
 */
public class IncomingMessagesRouter
{
	private final Map<MessageType, MessageReceiver> receivers;
	
	public IncomingMessagesRouter()
	{
		receivers = new HashMap<MessageType, MessageReceiver>();
	}
	
	public synchronized void setReceiver(MessageType type, MessageReceiver receiver)
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
