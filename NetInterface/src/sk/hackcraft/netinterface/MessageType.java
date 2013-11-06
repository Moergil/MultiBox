package sk.hackcraft.netinterface;

public interface MessageType
{
	public int toInt();
	
	public interface Parser<T>
	{
		public T parse(int value);
	}
}
