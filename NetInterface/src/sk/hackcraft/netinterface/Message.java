package sk.hackcraft.netinterface;

import java.io.IOException;

public interface Message
{
	public MessageType getType();
	public byte[] getContent() throws IOException;
}