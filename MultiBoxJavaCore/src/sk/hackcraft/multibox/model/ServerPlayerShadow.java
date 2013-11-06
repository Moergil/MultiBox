package sk.hackcraft.multibox.model;

import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.server.ServerInterface;

public class ServerPlayerShadow implements Player
{
	private ServerInterface serverInterface;
	
	private ServerListener serverListener;
	
	private boolean playing;
	private Multimedia activeMultimedia;
	private int playbackPosition;
	
	private List<PlayerEventListener> playerListeners;
	
	public ServerPlayerShadow(ServerInterface serverInterface)
	{
		this.serverInterface = serverInterface;
		
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
		
		if (!serverInterface.isConnected())
		{
			serverInterface.connect();
		}
		
		serverInterface.requestPlayerUpdate();
	}

	@Override
	public void close()
	{
		serverInterface.unregisterEventListener(serverListener);
		
		serverInterface.close();
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
			if (multimedia == null)
			{
				if (activeMultimedia != null)
				{
					activeMultimedia = null;
					playing = false;
					
					for (Player.PlayerEventListener listener : playerListeners)
					{
						listener.onMultimediaChanged(null);
					}
				}
			}
			else
			{
				boolean newMultimedia = false;
				if (activeMultimedia == null || !activeMultimedia.equals(multimedia))
				{
					newMultimedia = true;
					ServerPlayerShadow.this.activeMultimedia = multimedia;
				}
	
				ServerPlayerShadow.this.playbackPosition = playbackPosition;
				
				for (Player.PlayerEventListener listener : playerListeners)
				{
					if (newMultimedia)
					{
						listener.onMultimediaChanged(multimedia);
					}
					
					listener.onPlaybackPositionChanged(playbackPosition);
				}
			}
			
			boolean stateChanged = ServerPlayerShadow.this.playing != playing;
			
			ServerPlayerShadow.this.playing = playing;
			
			if (stateChanged)
			{
				for (Player.PlayerEventListener listener : playerListeners)
				{
					listener.onPlayingStateChanged(playing);
				}
			}
		}
	}
}
