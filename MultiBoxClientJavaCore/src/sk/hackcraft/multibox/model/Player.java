package sk.hackcraft.multibox.model;

public interface Player
{
	public void init();
	public void close();
	
	public boolean isPlaying();
	public boolean hasActiveMultimedia();
	
	public int getPlaybackPosition();
	public Multimedia getActiveMultimedia();

	public void requestPlayingStateChange(boolean playing);
	public void requestActiveMultimediaSkip();
	
	public void registerPlayerEventListener(PlayerEventListener listener);
	public void unregisterListener(PlayerEventListener listener);
	
	public interface PlayerEventListener
	{
		public void onPlayingStateChanged(boolean playing);
		public void onPlaybackPositionChanged(int newPosition);
		public void onMultimediaChanged(Multimedia newMultimedia);
	}
}
