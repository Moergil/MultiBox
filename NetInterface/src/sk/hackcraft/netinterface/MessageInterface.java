package sk.hackcraft.netinterface;

import java.io.IOException;

public interface MessageInterface
{
	public void sendMessage(Message message) throws IOException;
	public Message waitForMessage() throws IOException;
}
