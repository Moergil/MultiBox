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
	
	public void requestLibraryDirectory(long id);
	
	public interface ServerInterfaceEventListener
	{
		public void onDisconnect();
		
		public void onAuthentificationResponse(boolean succesfull);
		
		public void onPlayerUpdateReceived(Multimedia multimedia, int playbackPosition, boolean playing);
		public void onPlaylistReceived(List<Multimedia> playlist);
		
		public void onLibraryDirectoryReceived(String directoryName, List<LibraryItem> items);
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
		public void onLibraryDirectoryReceived(String directoryName, List<LibraryItem> items)
		{
		}
	}
}
