package sk.hackcraft.multibox.net;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sk.hackcraft.multibox.model.LibraryItem;
import sk.hackcraft.multibox.model.libraryitems.DirectoryItem;
import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import sk.hackcraft.util.MessageQueue;

public class MockServerInterface implements ServerInterface
{
	private MessageQueue messageQueue;
	
	private boolean playing;
	
	private List<MultimediaItem> playlist;
	private int playbackPosition;
	
	private Map<Long, LibraryItem> library;
	
	private List<ServerInterface.ServerInterfaceEventListener> serverListeners;
	
	public MockServerInterface(MessageQueue messageQueue)
	{
		this.messageQueue = messageQueue;
		
		this.playing = false;
		this.playlist = new LinkedList<MultimediaItem>();
		this.playbackPosition = 0;
		
		initLibrary();
		
		this.serverListeners = new LinkedList<ServerInterface.ServerInterfaceEventListener>();
	}
	
	@Override
	public void close()
	{		
	}
	
	private void initLibrary()
	{
		library = new HashMap<Long, LibraryItem>();
		
		long id = 0;
		
		DirectoryItem rootDirectory = new DirectoryItem(id++, "Mock Server");
		DirectoryItem linkinParkDirectory = new DirectoryItem(id++, "Linkin Park");
		DirectoryItem yesDirectory = new DirectoryItem(id++, "Yes");
		
		addLibraryItem(rootDirectory);
		addLibraryItem(linkinParkDirectory);
		addLibraryItem(yesDirectory);
		
		LibraryItem item;
		
		item = new MultimediaItem(id++, "Bleed it out", 5);
		linkinParkDirectory.addItem(item);
		addLibraryItem(item);
		
		item = new MultimediaItem(id++, "Faint", 5);
		linkinParkDirectory.addItem(item);
		addLibraryItem(item);
		
		item = new MultimediaItem(id++, "Blackout", 5);
		linkinParkDirectory.addItem(item);
		addLibraryItem(item);
		
		item = new MultimediaItem(id++, "Homeworld", 5);
		yesDirectory.addItem(item);
		addLibraryItem(item);
		
		item = new MultimediaItem(id++, "New languages", 5);
		yesDirectory.addItem(item);
		addLibraryItem(item);
		
		item = new MultimediaItem(id++, "Terra's theme", 5);
		rootDirectory.addItem(item);
		addLibraryItem(item);
		
		rootDirectory.addItem(linkinParkDirectory);
		rootDirectory.addItem(yesDirectory);
	}
	
	private void addLibraryItem(LibraryItem item)
	{
		library.put(item.getId(), item);
	}
	
	private <I> I getLibraryItem(long id, Class<I> itemClass)
	{
		return itemClass.cast(library.get(id));
	}

	@Override
	public void registerEventListener(ServerInterfaceEventListener listener)
	{
		serverListeners.add(listener);
	}

	@Override
	public void unregisterEventListener(ServerInterfaceEventListener listener)
	{
		serverListeners.remove(listener);
	}
	
	public Controller getController()
	{
		return new Controller();
	}
	
	private void broadcastServerInfo()
	{
		for (final ServerInterface.ServerInterfaceEventListener listener : serverListeners)
		{
			messageQueue.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onServerInfoReceived("M0ck3d");
				}
			}, 1000);
		}
	}	
	
	private void broadcastPlayerUpdate()
	{
		final MultimediaItem multimedia;
		final int playbackPosition;
		final boolean playing;
		
		if (playlist.isEmpty())
		{
			multimedia = null;
			playbackPosition = 0;
			playing = false;
		}
		else
		{
			multimedia = playlist.get(0);
			playbackPosition = MockServerInterface.this.playbackPosition;
			playing = true;
		}
		
		for (final ServerInterface.ServerInterfaceEventListener listener : serverListeners)
		{
			messageQueue.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onPlayerUpdateReceived(multimedia, playbackPosition, playing);
				}
			}, 1000);
		}
	}	
	
	private void broadcastPlaylistUpdate()
	{
		final List<MultimediaItem> playlist = new LinkedList<MultimediaItem>(this.playlist);
		
		for (final ServerInterface.ServerInterfaceEventListener listener : serverListeners)
		{
			messageQueue.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onPlaylistReceived(playlist);
				}
			}, 1000);
		}
	}
	
	private void broadcastLibraryItemReceived(long id)
	{
		final DirectoryItem directory = getLibraryItem(id, DirectoryItem.class);
		
		for (final ServerInterface.ServerInterfaceEventListener listener : serverListeners)
		{
			messageQueue.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onLibraryItemReceived(directory);
				}
			}, 1000);
		}
	}
	
	@Override
	public void requestServerInfo()
	{
		broadcastServerInfo();
	}

	@Override
	public void requestPlayerUpdate()
	{
		broadcastPlayerUpdate();
	}
	
	@Override
	public void requestPlaylistUpdate()
	{
		broadcastPlaylistUpdate();
	}
	
	public class Controller
	{
		private long nextId;
		private Random random;
		
		public Controller()
		{
			this.nextId = 1;
			this.random = new Random();
		}
		
		private long generateId()
		{
			return nextId++;
		}
		
		public void addRandomSongToPlaylist()
		{
			long id = generateId();
			String name = "Random" + random.nextInt(100);
			int length = random.nextInt(60);
			
			MultimediaItem multimedia = new MultimediaItem(id, name, length);
			
			playlist.add(multimedia);
		}
		
		public void addSongToPlaylist(final String name, final int length)
		{
			long id = generateId();
			MultimediaItem multimedia = new MultimediaItem(id, name, length);
			playlist.add(multimedia);
		}
		
		public void finishSong()
		{
			if (!playlist.isEmpty())
			{
				playlist.remove(0);
				
				if (playlist.isEmpty())
				{
					playing = false;
				}
				else
				{
					playbackPosition = 0;
				}
			}
			
			broadcastPlayerUpdate();
		}
		
		public void setPlaying(final boolean playing)
		{
			MockServerInterface.this.playing = playing;
			
			broadcastPlayerUpdate();
		}
		
		public void setPlaybackPosition(final int playbackPosition)
		{
			MockServerInterface.this.playbackPosition = playbackPosition;
			
			broadcastPlayerUpdate();
		}
	}

	@Override
	public void requestLibraryItem(long id)
	{
		broadcastLibraryItemReceived(id);
	}
	
	@Override
	public void addLibraryItemToPlaylist(long itemId)
	{
		playlist.add(new MultimediaItem(itemId, "Item " + itemId, 10));
		
		broadcastPlaylistUpdate();
		broadcastPlayerUpdate();
	}
}
