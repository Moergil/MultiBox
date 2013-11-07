package sk.hackcraft.multibox.net;

import java.io.Closeable;
import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.netinterface.MessageReceiver;
import sk.hackcraft.netinterface.MessageType;

public interface ServerInterface
{
	public void registerEventListener(ServerInterfaceEventListener listener);
	public void unregisterEventListener(ServerInterfaceEventListener listener);
	
	public void requestPlayerUpdate();
	public void requestPlaylistUpdate();
	
	public interface ServerInterfaceEventListener
	{
		public void onDisconnect();
		
		public void onAuthentificationResponse(boolean succesfull);
		
		public void onPlayerUpdateReceived(Multimedia multimedia, int playbackPosition, boolean playing);
		public void onPlaylistReceived(List<Multimedia> playlist);
	}
}
