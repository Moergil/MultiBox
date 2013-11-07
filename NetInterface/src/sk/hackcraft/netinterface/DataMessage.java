package sk.hackcraft.netinterface;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class DataMessage implements Message
{
	private final MessageType messageType;
	private final byte content[];
	
	public DataMessage(MessageType messageType)
	{
		this.messageType = messageType;
		
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		DataOutput output = new DataOutputStream(byteOutputStream);
		
		try
		{
			writeContent(output);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Can't create message content; this should never happen.");
		}
		
		this.content = byteOutputStream.toByteArray();
	}
	
	@Override
	public MessageType getType()
	{
		return messageType;
	}
	
	@Override
	public byte[] getContent()
	{
		return content;
	}
	
	protected abstract void writeContent(DataOutput output) throws IOException;
	
	@Override
	public String toString()
	{
		return messageType + " message";
	}
}
