package sk.hackcraft.netinterface;

public abstract class StoppableRunnable implements Runnable
{
	private volatile boolean run;
	
	public StoppableRunnable()
	{
		run = true;
	}
	
	public void stop()
	{
		run = false;
	}
	
	protected boolean isStopped()
	{
		return !run;
	}
}
