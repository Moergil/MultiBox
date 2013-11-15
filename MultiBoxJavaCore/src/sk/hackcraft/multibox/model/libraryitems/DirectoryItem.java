package sk.hackcraft.multibox.model.libraryitems;

import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.model.GenericLibraryItem;
import sk.hackcraft.multibox.model.LibraryItem;
import sk.hackcraft.multibox.model.LibraryItemType;

public class DirectoryItem extends GenericLibraryItem
{
	private List<LibraryItem> contentIds;
	
	public DirectoryItem(long id, String name)
	{
		super(id, LibraryItemType.DIRECTORY, name);
		
		this.contentIds = new LinkedList<LibraryItem>();
	}
	
	public void addItem(LibraryItem item)
	{
		contentIds.add(item);
	}
	
	public List<LibraryItem> getItems()
	{
		return new LinkedList<LibraryItem>(contentIds);
	}
}
