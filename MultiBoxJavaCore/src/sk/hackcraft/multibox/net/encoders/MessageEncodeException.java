package sk.hackcraft.multibox.net.encoders;

import java.io.IOException;

public class MessageEncodeException extends IOException
{
	private static final long serialVersionUID = 1L;

	public MessageEncodeException(Throwable cause)
	{
		super(cause);
	}
}
