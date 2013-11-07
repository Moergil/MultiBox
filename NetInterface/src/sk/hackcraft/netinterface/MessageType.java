package sk.hackcraft.netinterface;

public interface MessageType
{
	public int toTypeId();
	
	public interface Parser<T>
	{
		public T parse(int typeId);
	}
}
