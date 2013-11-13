package sk.hackcraft.multibox.model;

import java.util.List;

public interface Playlist
{
	public void init();
	public void close();
	
	public void registerListener(PlaylistEventListener listener);
	public void unregisterListener(PlaylistEventListener listener);
	
	public List<Multimedia> getItems();
	
	public interface PlaylistEventListener
	{
		public void onPlaylistChanged(List<Multimedia> newPlaylist);
	}
}
