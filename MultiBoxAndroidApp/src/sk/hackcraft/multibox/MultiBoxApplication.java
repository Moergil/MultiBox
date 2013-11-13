package sk.hackcraft.multibox;

import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.model.ServerLibraryShadow;
import sk.hackcraft.multibox.model.ServerPlayerShadow;
import sk.hackcraft.multibox.model.ServerPlaylistShadow;
import sk.hackcraft.multibox.net.MockServerInterface;
import sk.hackcraft.multibox.util.HandlerEventLoop;
import android.app.Application;
import android.support.v4.content.LocalBroadcastManager;

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

		controller.addRandomSongToPlaylist();
		controller.addRandomSongToPlaylist();
		controller.addRandomSongToPlaylist();
	}
	
	public Player createPlayer()
	{
		return new ServerPlayerShadow(serverInterface, eventLoop);
	}
	
	public Playlist createPlaylist()
	{
		return new ServerPlaylistShadow(serverInterface, eventLoop);
	}
	
	public Library createLibrary()
	{
		return new ServerLibraryShadow(serverInterface, eventLoop);
	}
}
