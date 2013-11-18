package sk.hackcraft.multibox.android.client;

public enum Intents
{
	PLAYER_PLAYING_STATE_CHANGED,
	PLAYER_PLAYBACK_POSITION_CHANGED,
	PLAYER_MULTIMEDIA_CHANGED;
	
	@Override
	public String toString()
	{
		return Intents.class.getPackage() + "." + super.toString();
	}
}
