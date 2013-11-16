package sk.hackcraft.multibox.net.data;

import sk.hackcraft.multibox.model.Multimedia;

public class GetPlayerStateResultData
{
	private final Multimedia multimedia;
	private final int playbackPosition;
	private final boolean playing;
	
	public GetPlayerStateResultData(Multimedia multimedia, int playbackPosition, boolean playing)
	{
		this.multimedia = multimedia;
		this.playbackPosition = playbackPosition;
		this.playing = playing;
	}
	
	public Multimedia getMultimedia()
	{
		return multimedia;
	}

	public int getPlaybackPosition()
	{
		return playbackPosition;
	}

	public boolean isPlaying()
	{
		return playing;
	}
}
