package sk.hackcraft.multibox.net.messages;

import sk.hackcraft.netinterface.Message;
import sk.hackcraft.netinterface.MessageType;

public class PlainRequestMessage implements Message
{
	private final MessageType messageType;
	
	public PlainRequestMessage(MessageType messageType)
	{
		this.messageType = messageType;
	}

	@Override
	public MessageType getType()
	{
		return messageType;
	}

	@Override
	public byte[] getContent()
	{
		return new byte[0];
	}
}
