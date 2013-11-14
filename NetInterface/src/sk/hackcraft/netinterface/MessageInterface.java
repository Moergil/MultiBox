package sk.hackcraft.netinterface;

import java.io.Closeable;
import java.io.IOException;

public interface MessageInterface extends Closeable
{
	public void sendMessage(Message message) throws IOException;
	public Message waitForMessage() throws IOException;
}
