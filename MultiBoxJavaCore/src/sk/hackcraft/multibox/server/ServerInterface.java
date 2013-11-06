package sk.hackcraft.multibox.server;

import java.io.Closeable;
import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;

public interface ServerInterface
{
	public void connect();
	public void close();
	
	public boolean isConnected();
	
	public void registerEventListener(ServerInterfaceEventListener listener);
	public void unregisterEventListener(ServerInterfaceEventListener listener);
	
	public void authentificate(String id, String password);
	
	public void requestPlayerUpdate();
	
	public interface ServerInterfaceEventListener
	{
		public void onDisconnect();
		
		public void onAuthentificationResponse(boolean succesfull);
		
		public void onPlayerUpdateReceived(Multimedia multimedia, int playbackPosition, boolean playing);
	}
}
