package sk.hackcraft.multibox.model;

import java.util.List;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.server.ServerInterface;

public class HuhPlayer
{
	/*private boolean active;
	
	private ServerInterface serverInterface;
	private EventListener listener;
	
	private Handler handler;
	
	private boolean actualPlayState;
	private Multimedia actualMultimedia;
	
	public HuhPlayer(ServerInterface serverInterface)
	{
		this.serverInterface = serverInterface;
		serverInterface.registerEventListener(serverListener);
		
		handler = new Handler();
	}
	
	public void setEventListener(EventListener eventListener)
	{
		this.listener = eventListener;
	}
	
	public void activate()
	{
		active = true;
		
		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				if (!active)
				{
					return;
				}
				
				serverInterface.requestPlayerState();
				handler.postDelayed(this, 5000);
			}
		});
	}
	
	public void deactivate()
	{
		active = false;
	}
	
	public interface EventListener
	{
		public void onMultimediaChange(Multimedia newMultimedia, int playPosition);
		public void onMultimediaUpdate(int playPosition);
		public void onStateChange(boolean playing);
		public void onNothingToPlay();
	}
	
	private ServerInterface.ServerInterfaceEventListener serverListener = new ServerInterface.EventAdapter()
	{
		@Override
		public void onPlayerStateReceived(Multimedia multimedia, boolean playing, int playPosition)
		{
			if (actualMultimedia == null)
			{
				if (multimedia != null)
				{
					actualMultimedia = multimedia;	
					listener.onMultimediaChange(actualMultimedia, playPosition);
				}
			}
			else
			{
				if (multimedia == null)
				{
					actualMultimedia = null;
					listener.onNothingToPlay();
				}
				else
				{
					if (playing && actualMultimedia.equals(multimedia))
					{
						listener.onMultimediaUpdate(playPosition);
					}
					else
					{
						listener.onMultimediaChange(multimedia, playPosition);
					}
				}
			}

			if (actualPlayState != playing)
			{
				actualPlayState = playing;
				listener.onStateChange(playing);
			}
		}
	};*/
}
