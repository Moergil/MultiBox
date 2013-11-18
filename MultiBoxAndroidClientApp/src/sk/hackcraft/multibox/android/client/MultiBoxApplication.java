package sk.hackcraft.multibox.android.client;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.android.client.util.HandlerEventLoop;
import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.model.ServerLibraryShadow;
import sk.hackcraft.multibox.model.ServerPlayerShadow;
import sk.hackcraft.multibox.model.ServerPlaylistShadow;
import sk.hackcraft.multibox.net.MockServerInterface;
import sk.hackcraft.multibox.net.ServerInterface;
import android.app.Application;
import android.widget.Toast;

public class MultiBoxApplication extends Application
{
	private HandlerEventLoop eventLoop;
	private MockServerInterface serverInterface;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		eventLoop = new HandlerEventLoop();
		serverInterface = new MockServerInterface(eventLoop);
		serverInterface.registerEventListener(new ServerInterface.ServerInterfaceEventAdapter()
		{
			@Override
			public void onDisconnect()
			{
				Toast.makeText(MultiBoxApplication.this, R.string.message_connection_problem, Toast.LENGTH_LONG).show();
			}
		});
		
		final MockServerInterface.Controller controller = serverInterface.getController();

		controller.addRandomSongToPlaylist();
		controller.addRandomSongToPlaylist();
		controller.addRandomSongToPlaylist();
	}
	
	public Player getPlayer()
	{
		return new ServerPlayerShadow(serverInterface, eventLoop);
	}
	
	public Playlist getPlaylist()
	{
		return new ServerPlaylistShadow(serverInterface, eventLoop);
	}
	
	public Library getLibrary()
	{
		return new ServerLibraryShadow(serverInterface, eventLoop);
	}
}
