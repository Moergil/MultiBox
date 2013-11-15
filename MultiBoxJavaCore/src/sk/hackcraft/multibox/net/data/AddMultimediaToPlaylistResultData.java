package sk.hackcraft.multibox.net.data;

import sk.hackcraft.multibox.model.Multimedia;

public class AddMultimediaToPlaylistResultData
{
	private final boolean success;
	private final Multimedia multimedia;
	
	public AddMultimediaToPlaylistResultData(boolean success, Multimedia multimedia)
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
