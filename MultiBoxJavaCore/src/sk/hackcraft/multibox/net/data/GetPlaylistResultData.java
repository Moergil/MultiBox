package sk.hackcraft.multibox.net.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;

public class GetPlaylistResultData
{
	private final List<Multimedia> playlist;
	
	public GetPlaylistResultData(List<Multimedia> playlist)
	{
		this.playlist = Collections.unmodifiableList(new LinkedList<Multimedia>(playlist));
	}
	
	public List<Multimedia> getPlaylist()
	{
		return playlist;
	}
}
