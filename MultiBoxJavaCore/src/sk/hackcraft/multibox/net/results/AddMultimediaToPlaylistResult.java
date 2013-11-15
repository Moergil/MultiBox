package sk.hackcraft.multibox.net.results;

import sk.hackcraft.multibox.model.Multimedia;

public class AddMultimediaToPlaylistResult
{
	private final boolean success;
	private final Multimedia multimedia;
	
	public AddMultimediaToPlaylistResult(boolean success, Multimedia multimedia)
	{
		this.success = success;
		this.multimedia = multimedia;
	}
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public Multimedia getMultimedia()
	{
		return multimedia;
	}
}
