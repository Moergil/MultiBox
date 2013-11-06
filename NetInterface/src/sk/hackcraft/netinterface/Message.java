package sk.hackcraft.netinterface;

public interface Message
{
	public int getType();
	public byte[] getContent();
}