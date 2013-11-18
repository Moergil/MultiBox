package sk.hackcraft.multibox.net;

import java.util.List;

import sk.hackcraft.multibox.model.LibraryItem;
import sk.hackcraft.multibox.model.Multimedia;

public interface ServerInterface
{
	public void registerEventListener(ServerInterfaceEventListener listener);
	public void unregisterEventListener(ServerInterfaceEventListener listener);
	
	public void requestPlayerUpdate();
	public void requestPlaylistUpdate();
	
	public void requestLibraryItem(long itemId);
	public void addLibraryItemToPlaylist(long itemId);
	
	public interface ServerInterfaceEventListener
	{
		public void onDisconnect();
		
		public void onAuthentificationResponse(boolean succesfull);
		
		public void onPlayerUpdateReceived(Multimedia multimedia, int playbackPosition, boolean playing);
		public void onPlaylistReceived(List<Multimedia> playlist);
		
		public void onLibraryItemReceived(LibraryItem item);
		
		public void onAddingLibraryItemToPlaylistResult(boolean result, Multimedia multimedia);
	}
	
	public class ServerInterfaceEventAdapter implements ServerInterfaceEventListener
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
		}

		@Override
		public void onLibraryItemReceived(LibraryItem item)
		{
		}

		@Override
		public void onAddingLibraryItemToPlaylistResult(boolean result, Multimedia multimedia)
		{
		}
	}
}
