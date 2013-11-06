package sk.hackcraft.multibox.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.util.MessageQueue;

public class MockServerInterface implements ServerInterface
{
	private MessageQueue messageQueue;
	
	private boolean playing;
	
	private List<Multimedia> playlist;
	private int playbackPosition;
	
	private List<ServerInterface.ServerInterfaceEventListener> serverListeners;
	
	public MockServerInterface(MessageQueue messageQueue)
	{
		this.messageQueue = messageQueue;
		
		this.playing = false;
		this.playlist = new LinkedList<Multimedia>();
		this.playbackPosition = 0;
		
		this.serverListeners = new LinkedList<ServerInterface.ServerInterfaceEventListener>();
	}
	
	@Override
	public void connect()
	{
	}

	@Override
	public void close()
	{
	}

	@Override
	public boolean isConnected()
	{
		return true;
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

	@Override
	public void authentificate(String id, String password)
	{
	}
	
	public Controller getController()
	{
		return new Controller();
	}
	
	private void broadcastPlayerUpdate()
	{
		for (final ServerInterface.ServerInterfaceEventListener listener : serverListeners)
		{
			final Multimedia multimedia;
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

			messageQueue.post(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onPlayerUpdateReceived(multimedia, playbackPosition, playing);
				}
			});
		}
	}

	@Override
	public void requestPlayerUpdate()
	{
		broadcastPlayerUpdate();
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
		
		public void addRandomSong()
		{
			long id = generateId();
			String name = "Random" + random.nextInt(100);
			int length = random.nextInt(60);
			
			Multimedia multimedia = new Multimedia(id, name, length);
			
			playlist.add(multimedia);
		}
		
		public void addSong(final String name, final int length)
		{
			long id = generateId();
			Multimedia multimedia = new Multimedia(id, name, length);
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
}
