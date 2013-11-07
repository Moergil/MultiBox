package sk.hackcraft.multibox.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.util.MessageQueue;

public class ServerPlaylistShadow implements Playlist
{
	private final ServerInterface serverInterface;
	private final MessageQueue messageQueue;
	
	private List<Multimedia> actualPlaylist;
	
	private List<Playlist.PlaylistEventListener> playlistListeners;
	
	private ServerListener serverListener;
	
	public ServerPlaylistShadow(ServerInterface serverInterface, MessageQueue messageQueue)
	{
		this.serverInterface = serverInterface;
		this.messageQueue = messageQueue;
		
		playlistListeners = new LinkedList<Playlist.PlaylistEventListener>();
		
		serverListener = new ServerListener();
		
		actualPlaylist = new LinkedList<Multimedia>();
	}
	
	@Override
	public void init()
	{
		serverInterface.registerEventListener(serverListener);
		
		serverInterface.requestPlaylistUpdate();
	}

	@Override
	public void close()
	{
		serverInterface.unregisterEventListener(serverListener);
	}
	
	@Override
	public void registerListener(PlaylistEventListener listener)
	{
		playlistListeners.add(listener);
	}
	
	@Override
	public void unregisterListener(PlaylistEventListener listener)
	{
		playlistListeners.remove(listener);
	}

	@Override
	public List<Multimedia> getItems()
	{
		return Collections.unmodifiableList(actualPlaylist);
	}
	
	private void updatePlaylist(List<Multimedia> playlist)
	{
		actualPlaylist.clear();
		actualPlaylist.addAll(playlist);
		
		final List<Multimedia> playlistCopy = new LinkedList<Multimedia>(actualPlaylist);
		
		for (final Playlist.PlaylistEventListener listener : playlistListeners)
		{
			messageQueue.post(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onPlaylistChanged(playlistCopy);
				}
			});
		}
	}
	
	private class ServerListener implements ServerInterface.ServerInterfaceEventListener
	{
		@Override
		public void onDisconnect()
		{
		}

		@Override
		public void onAuthentificationResponse(boolean succesfull)
		{
		}

		@Override
		public void onPlayerUpdateReceived(Multimedia multimedia, int playbackPosition, boolean playing)
		{
		}

		@Override
		public void onPlaylistReceived(List<Multimedia> playlist)
		{
			updatePlaylist(playlist);
		}
	}
}
