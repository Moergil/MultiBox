package sk.hackcraft.multibox.model.libraryitems;

import sk.hackcraft.multibox.model.GenericLibraryItem;
import sk.hackcraft.multibox.model.LibraryItemType;

public class MultimediaItem extends GenericLibraryItem
{
	public MultimediaItem(long id, String name)
	{
		super(id, LibraryItemType.MULTIMEDIA, name);
	}
}
