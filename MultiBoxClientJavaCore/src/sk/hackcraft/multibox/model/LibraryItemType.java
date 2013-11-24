package sk.hackcraft.multibox.model;

public enum LibraryItemType
{
	DIRECTORY(1),
	MULTIMEDIA(2);
	
	private final long id;
	
	private LibraryItemType(long id)
	{
		this.id = id;
	}
	
	public long getId()
	{
		return id;
	}
}
