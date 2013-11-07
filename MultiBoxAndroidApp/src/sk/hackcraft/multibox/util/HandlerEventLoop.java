package sk.hackcraft.multibox.util;

import android.os.Handler;
import sk.hackcraft.util.MessageQueue;

public class HandlerEventLoop implements MessageQueue
{
	private Handler handler;
	
	public HandlerEventLoop()
	{
		this.handler = new Handler();
	}
	
	@Override
	public void post(Runnable runnable)
	{
		handler.post(runnable);
	}

	@Override
	public void postDelayed(Runnable runnable, long delay)
	{
		handler.postDelayed(runnable, delay);
	}
}
