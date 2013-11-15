package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.util.DataTransformException;
import sk.hackcraft.multibox.util.DataTransformer;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JacksonMessageDecoder<D> implements DataTransformer<String, D>
{
	@Override
	public D transform(String jsonString) throws DataTransformException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			return decodeJson(objectMapper, jsonString);
		}
		catch (Exception e)
		{
			throw new DataTransformException(e);
		}
	}
	
	public abstract D decodeJson(ObjectMapper objectMapper, String jsonString) throws Exception;
}
