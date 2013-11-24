package sk.hackcraft.multibox.model.libraryitems;

import sk.hackcraft.multibox.model.GenericLibraryItem;
import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.LibraryItemType;

public class BackNavigationLibraryItem extends GenericLibraryItem
{
	public BackNavigationLibraryItem()
	{
		super(Library.BACK_NAVIGATION, LibraryItemType.BACK_NAVIGATION, "..");
	}
}
