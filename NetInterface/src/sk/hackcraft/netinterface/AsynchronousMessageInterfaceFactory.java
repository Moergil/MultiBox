package sk.hackcraft.netinterface;

import java.io.IOException;

public interface AsynchronousMessageInterfaceFactory
{
	public AsynchronousMessageInterface create() throws IOException;
}
