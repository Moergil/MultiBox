package sk.hackcraft.multibox.net.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JacksonMessageParser<R> implements MessageParser<String, R>
{
	@Override
	public R parse(String jsonString) throws MessageParseException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			return jacksonParse(objectMapper, jsonString);
		}
		catch (Exception e)
		{
			throw new MessageParseException(e);
		}
	}
	
	public abstract R jacksonParse(ObjectMapper objectMapper, String jsonString) throws Exception;
}
