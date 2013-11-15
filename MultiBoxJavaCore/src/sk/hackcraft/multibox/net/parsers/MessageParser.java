package sk.hackcraft.multibox.net.parsers;

public interface MessageParser<D, R>
{
	public R parse(D data) throws MessageParseException;
}
