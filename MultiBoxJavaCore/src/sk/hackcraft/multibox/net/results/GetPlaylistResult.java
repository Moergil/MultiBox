package sk.hackcraft.multibox.net.results;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;

public class GetPlaylistResult
{
	private final List<Multimedia> playlist;
	
	public GetPlaylistResult(List<Multimedia> playlist)
	{
		this.playlist = Collections.unmodifiableList(new LinkedList<Multimedia>(playlist));
	}
	
	public List<Multimedia> getPlaylist()
	{
		return playlist;
	}
}
