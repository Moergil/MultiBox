package sk.hackcraft.multibox.net.parsers;

import java.io.IOException;

public class MessageParseException extends IOException
{
	private static final long serialVersionUID = 1L;

	public MessageParseException(Throwable cause)
	{
		super(cause);
	}
}
