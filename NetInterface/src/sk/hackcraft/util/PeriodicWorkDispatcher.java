package sk.hackcraft.util;


public abstract class PeriodicWorkDispatcher
{
	private MessageQueue messageQueue;
	private long pause;
	
	private boolean active;
	
	public PeriodicWorkDispatcher(MessageQueue messageQueue, long pause)
	{
		this.messageQueue = messageQueue;
		this.pause = pause;
		this.active = false;
	}
	
	public void start()
	{
		if (active)
		{
			return;
		}
		
		active = true;
		
		messageQueue.post(new Runnable()
		{
			@Override
			public void run()
			{
				if (active)
				{
					doWork();
					messageQueue.postDelayed(this, pause);
				}
			}
		});
	}
	
	public void stop()
	{
		if (!active)
		{
			return;
		}
		
		active = false;
	}
	
	protected abstract void doWork();
}
