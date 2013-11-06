package sk.hackcraft.netinterface;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

public abstract class DataMessageReceiver implements MessageReceiver
{
	@Override
	public void receive(byte[] content)
	{
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(content);
		DataInput input = new DataInputStream(byteInputStream);
		
		try
		{
			parseContent(input);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Can't parse message; this should never happen.");
		}
	}
	
	protected abstract void parseContent(DataInput input) throws IOException;
}
