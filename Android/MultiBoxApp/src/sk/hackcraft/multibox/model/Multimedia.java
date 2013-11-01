package sk.hackcraft.multibox.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Multimedia implements Parcelable
{
	private final long id;
	private final String name;
	private final int length;
	private int playPosition;
	
	public Multimedia(long id, String name, int length, int playPosition)
	{
		this.id = id;
		this.name = name;
		this.length = length;
		this.playPosition = playPosition;
	}
	
	public Multimedia(Parcel source)
	{
		this.id = source.readLong();
		this.name = source.readString();
		this.length = source.readInt();
		this.playPosition = source.readInt();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public int getPlayPosition()
	{
		return playPosition;
	}
	
	public void setPlayPosition(int position)
	{
		if (position < 0 || position > length)
		{
			throw new IllegalArgumentException("Play positions must be between 0 and multimedia length.");
		}
		
		this.playPosition = position;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeInt(length);
		dest.writeInt(playPosition);
	}
	
	public static Parcelable.Creator<Multimedia> CREATOR = new Parcelable.Creator<Multimedia>()
	{
		@Override
		public Multimedia createFromParcel(Parcel source)
		{
			return new Multimedia(source);
		}

		@Override
		public Multimedia[] newArray(int size)
		{
			return new Multimedia[size];
		}
	};
}
