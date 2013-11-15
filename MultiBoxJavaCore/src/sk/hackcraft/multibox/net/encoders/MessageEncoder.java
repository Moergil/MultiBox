package sk.hackcraft.multibox.net.encoders;

public interface MessageEncoder<D, R>
{
	public R encode(D data) throws MessageEncodeException;
}
