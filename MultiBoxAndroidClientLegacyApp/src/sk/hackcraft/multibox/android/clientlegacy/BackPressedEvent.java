package sk.hackcraft.multibox.android.clientlegacy;

public interface BackPressedEvent
{
	public void registerBackPressedListener(BackPressedListener listener);
	public void unregisterBackPressedListener(BackPressedListener listener);
}
