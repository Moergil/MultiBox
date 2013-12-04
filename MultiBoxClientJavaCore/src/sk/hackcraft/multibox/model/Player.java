package sk.hackcraft.multibox.model;

import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;

public interface Player
{
	public void init();
	public void close();
	
	public boolean isPlaying();
	public boolean hasActiveMultimedia();
	
	public int getPlaybackPosition();
	public MultimediaItem getActiveMultimedia();

	public void requestPlayingStateChange(boolean playing);
	public void requestActiveMultimediaSkip();
	
	public void registerPlayerEventListener(PlayerEventListener listener);
	public void unregisterListener(PlayerEventListener listener);
	
	public interface PlayerEventListener
	{
		public void set(MultimediaItem multimedia);
		public void play();
		public void play(int playbackPosition);
		public void pause();
		public void synchronizePlaybackPosition(int playbackPosition);
	}
}
