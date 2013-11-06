package sk.hackcraft.netinterface;

import java.io.IOException;

public interface ServerInterfaceFactory
{
	public ServerInterface create(String address, int port) throws IOException;
}
