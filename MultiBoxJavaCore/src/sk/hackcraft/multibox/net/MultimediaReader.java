package sk.hackcraft.multibox.net;

import java.io.DataInput;
import java.io.IOError;
import java.io.IOException;

import sk.hackcraft.multibox.model.Multimedia;

public class MultimediaReader
{
	public static Multimedia read(DataInput input) throws IOException
	{
		long id = input.readLong();
		String name = input.readUTF();
		int length = input.readInt();
		
		return new Multimedia(id, name, length);
	}
}
