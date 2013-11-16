package sk.hackcraft.multibox.net.receivers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import sk.hackcraft.multibox.util.DataTransformer;
import sk.hackcraft.netinterface.MessageReceiver;
import sk.hackcraft.util.MessageQueue;

public abstract class DataStringMessageReceiver<R> implements MessageReceiver
{
	private static final Charset ENCODING = StandardCharsets.UTF_8;

	private final MessageQueue messageQueue;
	
	public DataStringMessageReceiver(MessageQueue messageQueue)
	{
		this.messageQueue = messageQueue;
	}
	
	@Override
	public void receive(byte[] content) throws IOException
	{
		final DataTransformer<String, R> parser = createParser();
		
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(content));
		
		int length = input.readInt();
		byte dataStringBytes[] = new byte[length];
		
		input.read(dataStringBytes);
		
		String dataString = new String(dataStringBytes, ENCODING);

		final R result = parser.transform(dataString);
		
		messageQueue.post(new Runnable()
		{
			@Override
			public void run()
			{
				onResult(result);
			}
		});
	}
	
	protected abstract DataTransformer<String, R> createParser();
	protected abstract void onResult(R result);
}
