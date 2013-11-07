package sk.hackcraft.multibox;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.model.ServerPlayerShadow;
import sk.hackcraft.multibox.model.ServerPlaylistShadow;
import sk.hackcraft.multibox.net.MockServerInterface;
import sk.hackcraft.multibox.util.HandlerEventLoop;
import android.app.Application;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class MultiBoxApplication extends Application
{	
	private LocalBroadcastManager broadcastManager;
	
	private HandlerEventLoop eventLoop;
	private MockServerInterface serverInterface;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		broadcastManager = LocalBroadcastManager.getInstance(this);
		
		eventLoop = new HandlerEventLoop();
		serverInterface = new MockServerInterface(eventLoop);
		
		final MockServerInterface.Controller controller = serverInterface.getController();

		controller.addRandomSong();
		controller.addRandomSong();
		controller.addRandomSong();
	}
	
	public Player createPlayer()
	{
		return new ServerPlayerShadow(serverInterface, eventLoop);
	}
	
	public Playlist createPlaylist()
	{
		return new ServerPlaylistShadow(serverInterface, eventLoop);
	}
}
