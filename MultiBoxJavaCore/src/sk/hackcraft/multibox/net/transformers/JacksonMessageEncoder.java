package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.util.DataTransformException;
import sk.hackcraft.multibox.util.DataTransformer;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JacksonMessageEncoder<D> implements DataTransformer<D, String>
{
	@Override
	public String transform(D data) throws DataTransformException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			return encodeJson(objectMapper, data);
		}
		catch (Exception e)
		{
			throw new DataTransformException(e);
		}
	}
	
	public abstract String encodeJson(ObjectMapper objectMapper, D data) throws Exception;
}
