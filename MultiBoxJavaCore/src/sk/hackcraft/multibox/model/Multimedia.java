package sk.hackcraft.multibox.model;

public class Multimedia
{
	private final long id;
	private final String name;
	private final int length;
	
	public Multimedia(long id, String name, int length)
	{
		this.id = id;
		this.name = name;
		this.length = length;
	}
	
	public Multimedia(Multimedia multimedia)
	{
		this.id = multimedia.id;
		this.name = multimedia.name;
		this.length = multimedia.length;
	}
	
	public long getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getLength()
	{
		return length;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof Multimedia))
		{
			return false;
		}
		
		Multimedia multimedia = (Multimedia)o;
		
		return equals(multimedia);
	}
	
	public boolean equals(Multimedia multimedia)
	{
		return multimedia.getId() == id;
	}
	
	@Override
	public int hashCode()
	{
		return Long.valueOf(id).hashCode();
	}
	
	@Override
	public String toString()
	{
		return "#" + getId() + " " + getName();
	}
}
