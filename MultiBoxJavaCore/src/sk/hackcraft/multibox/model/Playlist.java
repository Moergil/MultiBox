package sk.hackcraft.multibox.model;

import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.server.ServerInterface;

public class Playlist
{
	/*private List<Multimedia> items;
	
	private ServerInterface serverInterface;
	
	private PlaylistListener listener;
	
	private boolean active;
	private Handler handler;
	
	public Playlist(ServerInterface serverInterface, PlaylistListener listener)
	{
		items = new LinkedList<Multimedia>();
		
		this.serverInterface = serverInterface;
		this.listener = listener;
		
		handler = new Handler();
	}
	
	public void activate()
	{
		active = true;
		
		serverInterface.registerEventListener(serverListener);
		
		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				if (!active)
				{
					return;
				}
				
				serverInterface.requestPlaylist();
				
				handler.postDelayed(this, 5000);
			}
		});
	}
	
	public void deactivate()
	{
		active = false;
		
		serverInterface.unregisterEventListener(serverListener);
	}
	
	public interface PlaylistListener
	{
		public void onPlaylistReceived(List<Multimedia> playlist);
	}
	
	private ServerInterface.EventAdapter serverListener = new ServerInterface.EventAdapter()
	{
		@Override
		public void onPlaylistReceived(java.util.List<Multimedia> playlist)
		{
			listener.onPlaylistReceived(playlist);
		}
	};*/
}
