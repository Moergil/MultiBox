package sk.hackcraft.multibox.model;

import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.util.MessageQueue;

public class Server
{
	private final ServerInterface serverInterface;
	private final MessageQueue messageQueue;
	
	public Server(ServerInterface serverInterface, MessageQueue messageQueue)
	{
		this.serverInterface = serverInterface;
		this.messageQueue = messageQueue;
	}
	
	public void requestInfo(final ServerInfoListener listener)
	{
		serverInterface.registerEventListener(new ServerInterface.ServerInterfaceEventAdapter()
		{
			@Override
			public void onServerInfoReceived(String serverName)
			{
				listener.onServerNameReceived(serverName);
				
				serverInterface.unregisterEventListener(this);
			}
		});
		
		serverInterface.requestServerInfo();
	}

	public Player getPlayer()
	{
		return new ServerPlayerShadow(serverInterface, messageQueue);
	}

	public Playlist getPlaylist()
	{
		return new ServerPlaylistShadow(serverInterface, messageQueue);
	}

	public Library getLibrary()
	{
		return new ServerLibraryShadow(serverInterface, messageQueue);
	}
	
	public interface ServerInfoListener
	{
		public void onServerNameReceived(String name);
	}
}
