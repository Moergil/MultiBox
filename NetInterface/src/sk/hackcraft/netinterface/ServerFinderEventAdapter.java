package sk.hackcraft.netinterface;

import java.net.InetAddress;

import sk.hackcraft.netinterface.ServerFinder.EventListener;

public class ServerFinderEventAdapter implements EventListener
{
	@Override
	public void onServerBeaconReceived(String name, InetAddress address)
	{
	}
}
