package sk.hackcraft.netinterface;

import sk.hackcraft.netinterface.ServerInterface.EventListener;

public class ServerInterfaceEventAdapter implements EventListener
{
	@Override
	public void onDisconnect()
	{
	}

	@Override
	public void onChamberIdReceived(int chamberId)
	{
	}

	@Override
	public void onNeighborChamberConnected(int chamberId)
	{
	}

	@Override
	public void onNeighborChamberDisconnected(int chamberId)
	{
	}

	@Override
	public void onGameStart()
	{
	}

	@Override
	public void onGameEnd()
	{
	}

	@Override
	public void onParticleSpawned(int particleId, double speed)
	{
	}

	@Override
	public void onParticleReceived(int particleId, double speed, int sourceChamberId)
	{
	}
}