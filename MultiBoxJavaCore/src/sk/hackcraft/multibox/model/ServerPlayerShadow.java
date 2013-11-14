package sk.hackcraft.multibox.model;

import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.multibox.net.ServerInterface.ServerInterfaceEventAdapter;
import sk.hackcraft.util.MessageQueue;

public class ServerPlayerShadow implements Player
{
	private final ServerInterface serverInterface;
	private final MessageQueue messageQueue;
	
	private ServerListener serverListener;
	
	private boolean playing;
	private Multimedia activeMultimedia;
	private int playbackPosition;
	
	private List<PlayerEventListener> playerListeners;
	
	public ServerPlayerShadow(ServerInterface serverInterface, MessageQueue messageQueue)
	{
		this.serverInterface = serverInterface;
		this.messageQueue = messageQueue;
		
		this.playing = false;
		this.activeMultimedia = null;
		this.playbackPosition = 0;
		
		this.playerListeners = new LinkedList<Player.PlayerEventListener>();
		
		this.serverListener = new ServerListener();
	}
	
	@Override
	public void init()
	{
		serverInterface.registerEventListener(serverListener);
		serverInterface.requestPlayerUpdate();
	}

	@Override
	public void close()
	{
		serverInterface.unregisterEventListener(serverListener);
	}

	@Override
	public boolean isPlaying()
	{
		return playing;
	}

	@Override
	public boolean hasActiveMultimedia()
	{
		return activeMultimedia != null;
	}

	@Override
	public int getPlaybackPosition()
	{
		return playbackPosition;
	}

	@Override
	public Multimedia getActiveMultimedia()
	{
		return activeMultimedia;
	}

	@Override
	public void requestActiveMultimediaChange(Multimedia multimedia)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public void requestPlayingStateChange(boolean playing)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public void requestActiveMultimediaSkip()
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public void registerPlayerEventListener(PlayerEventListener listener)
	{
		playerListeners.add(listener);
	}

	@Override
	public void unregisterPlayerEventListener(PlayerEventListener listener)
	{
		playerListeners.remove(listener);
	}
	
	private class ServerListener extends ServerInterfaceEventAdapter
	{
		@Override
		public void onPlayerUpdateReceived(final Multimedia multimedia, final int playbackPosition, final boolean playing)
		{
			if (multimedia == null)
			{
				if (activeMultimedia != null)
				{
					activeMultimedia = null;
					
					for (final Player.PlayerEventListener listener : playerListeners)
					{
						messageQueue.post(new Runnable()
						{
							@Override
							public void run()
							{
								listener.onMultimediaChanged(null);
							}
						});
					}
				}
			}
			else
			{
				final boolean newMultimedia;
				if (activeMultimedia == null || !activeMultimedia.equals(multimedia))
				{
					newMultimedia = true;
					ServerPlayerShadow.this.activeMultimedia = multimedia;
				}
				else
				{
					newMultimedia = false;
				}
	
				ServerPlayerShadow.this.playbackPosition = playbackPosition;
				
				for (final Player.PlayerEventListener listener : playerListeners)
				{
					messageQueue.post(new Runnable()
					{
						@Override
						public void run()
						{
							if (newMultimedia)
							{
								listener.onMultimediaChanged(multimedia);
							}
							
							listener.onPlaybackPositionChanged(playbackPosition);
						}
					});
				}
			}
			
			boolean stateChanged = ServerPlayerShadow.this.playing != playing;
			
			ServerPlayerShadow.this.playing = playing;
			
			if (stateChanged)
			{
				for (final Player.PlayerEventListener listener : playerListeners)
				{
					messageQueue.post(new Runnable()
					{
						
						@Override
						public void run()
						{
							listener.onPlayingStateChanged(playing);
						}
					});
				}
			}
		}
	}
}
