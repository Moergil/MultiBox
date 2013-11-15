package sk.hackcraft.multibox.net.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import sk.hackcraft.multibox.net.encoders.MessageEncoder;
import sk.hackcraft.netinterface.Message;
import sk.hackcraft.netinterface.MessageType;

public abstract class DataStringMessage<D> implements Message
{
	private final MessageType messageType;
	private final D data;
	
	public DataStringMessage(MessageType messageType, D data)
	{
		this.messageType = messageType;
		this.data = data;
	}
	
	protected abstract MessageEncoder<D, String> createEncoder();
	
	@Override
	public MessageType getType()
	{
		return messageType;
	}

	@Override
	public byte[] getContent() throws IOException
	{
		MessageEncoder<D, String> encoder = createEncoder();
		
		String dataString = encoder.encode(data);
		
		byte dataStringBytes[] = dataString.getBytes();
		
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(byteOutput);
		output.writeInt(dataStringBytes.length);
		output.write(dataStringBytes);
		
		return byteOutput.toByteArray();
	}
}
