package sk.hackcraft.multibox.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.multibox.net.ServerInterface.ServerInterfaceEventAdapter;
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
	
	@Override
	public void addItem(long itemId)
	{
		serverInterface.addLibraryItemToPlaylist(itemId);
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
	
	private class ServerListener extends ServerInterfaceEventAdapter
	{
		@Override
		public void onPlaylistReceived(List<Multimedia> playlist)
		{
			updatePlaylist(playlist);
		}
		
		@Override
		public void onAddingLibraryItemToPlaylistResult(final boolean result, final Multimedia multimedia)
		{
			for (final Playlist.PlaylistEventListener listener : playlistListeners)
			{
				messageQueue.post(new Runnable()
				{
					@Override
					public void run()
					{
						listener.onItemAdded(result, multimedia);
					}
				});
			}
		}
	}
}
