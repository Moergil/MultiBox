package sk.hackcraft.multibox.model;

import java.util.LinkedList;
import java.util.List;

public class Playlist
{
	private List<Multimedia> multimediasList;
	
	private Multimedia playingMultimedia;
	
	public Playlist()
	{
		multimediasList = new LinkedList<Multimedia>();
		playingMultimedia = null;
	}
	
	public List<Multimedia> getMultimediasList()
	{
		return new LinkedList<Multimedia>(multimediasList);
	}
	
	public boolean isPlaying()
	{
		return playingMultimedia != null;
	}
	
	public Multimedia getPlayingMultimedia()
	{
		return playingMultimedia;
	}
	
	public void addMultimedia(Multimedia multimedia)
	{
		multimediasList.add(multimedia);
	}
	
	public void addMultimedia(Multimedia multimedia, int index)
	{
		multimediasList.add(index, multimedia);
	}
}
