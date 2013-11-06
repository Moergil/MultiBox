package sk.hackcraft.netinterface;

import sk.hackcraft.netinterface.ClientAccepter.EventListener;

public class ClientAccepterEventAdapter<I> implements EventListener<I>
{
	@Override
	public void onClientAccepted(I clientInterface)
	{
	}
}
