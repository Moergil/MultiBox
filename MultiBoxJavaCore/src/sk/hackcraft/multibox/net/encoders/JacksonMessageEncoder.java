package sk.hackcraft.multibox.net.encoders;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JacksonMessageEncoder<D> implements MessageEncoder<D, String>
{
	@Override
	public String encode(D data) throws MessageEncodeException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			return encodeJson(objectMapper, data);
		}
		catch (Exception e)
		{
			throw new MessageEncodeException(e);
		}
	}
	
	public abstract String encodeJson(ObjectMapper objectMapper, D data) throws Exception;
}
