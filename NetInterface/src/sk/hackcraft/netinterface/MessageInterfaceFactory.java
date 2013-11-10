package sk.hackcraft.netinterface;

import java.io.IOException;

public interface MessageInterfaceFactory
{
	public MessageInterface create() throws IOException;
}
