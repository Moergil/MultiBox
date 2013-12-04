package sk.hackcraft.multibox.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.multibox.net.ServerInterface.ServerInterfaceEventAdapter;
import sk.hackcraft.util.MessageQueue;

public class ServerPlayerShadow implements Player
{
	private final ServerInterface serverInterface;
	private final MessageQueue messageQueue;
	
	private ServerListener serverListener;
	
	private long lastUpdateTimestamp;
	
	private boolean playing;
	private MultimediaItem activeMultimedia;
	private int playbackPosition;
	
	private List<PlayerEventListener> playerListeners;
	
	public ServerPlayerShadow(ServerInterface serverInterface, MessageQueue messageQueue)
	{
		this.serverInterface = serverInterface;
		this.messageQueue = messageQueue;
		
		this.lastUpdateTimestamp = System.currentTimeMillis();
		
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
		int offset = (int)TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastUpdateTimestamp);
		return playbackPosition + offset;
	}

	@Override
	public MultimediaItem getActiveMultimedia()
	{
		return activeMultimedia;
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
	public void unregisterListener(PlayerEventListener listener)
	{
		playerListeners.remove(listener);
	}
	
	private class ServerListener extends ServerInterfaceEventAdapter
	{
		@Override
		public void onPlayerUpdateReceived(final MultimediaItem multimedia, final int playbackPosition, final boolean playing)
		{
			ServerPlayerShadow player = ServerPlayerShadow.this;

			final boolean wasPlaying = player.playing;
			
			final boolean playingStateChanged = player.playing != playing;
			final boolean playbackPositionChanged = player.playbackPosition != playbackPosition;
			final boolean multimediaChanged;
			
			if (activeMultimedia == null)
			{
				multimediaChanged = multimedia != null;
			}
			else
			{
				multimediaChanged = !player.activeMultimedia.equals(multimedia);
			}
			
			lastUpdateTimestamp = System.currentTimeMillis();
			
			player.activeMultimedia = multimedia;
			player.playing = playing;
			player.playbackPosition = playbackPosition;
			
			messageQueue.post(new Runnable()
			{
				@Override
				public void run()
				{
					for (final Player.PlayerEventListener listener : playerListeners)
					{
						if (multimediaChanged)
						{
							if (wasPlaying)
							{
								listener.pause();
							}
							
							listener.set(multimedia);
							
							if (playing)
							{
								listener.play();
							}
						}
						else
						{
							if (playingStateChanged)
							{
								if (playing)
								{
									listener.play(playbackPosition);
								}
								else
								{
									listener.pause();
								}
							}
							else
							{
								if (playbackPositionChanged)
								{
									listener.synchronizePlaybackPosition(playbackPosition);
								}
							}
						}
					}
				}
			});
		}
	}
}
