package sk.hackcraft.util;

import javax.swing.SwingUtilities;

public class SwingMessageQueue implements MessageQueue
{
	@Override
	public void post(Runnable runnable)
	{
		SwingUtilities.invokeLater(runnable);
	}

	@Override
	public void postDelayed(Runnable runnable, long delay)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
