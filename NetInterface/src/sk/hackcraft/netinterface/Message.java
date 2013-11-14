package sk.hackcraft.netinterface;

public interface Message
{
	public MessageType getType();
	public byte[] getContent();
}