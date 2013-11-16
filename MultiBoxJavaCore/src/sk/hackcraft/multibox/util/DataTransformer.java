package sk.hackcraft.multibox.util;


public interface DataTransformer<I, O>
{
	public O transform(I input) throws DataTransformException;
}
