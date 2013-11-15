package sk.hackcraft.multibox.net.results;

import sk.hackcraft.multibox.model.Multimedia;

public class GetPlayerStateResult
{
	private final Multimedia multimedia;
	private final int playbackPosition;
	private final boolean playing;
	
	public GetPlayerStateResult(Multimedia multimedia, int playbackPosition, boolean playing)
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
