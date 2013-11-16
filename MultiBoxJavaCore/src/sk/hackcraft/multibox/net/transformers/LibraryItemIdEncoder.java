package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.net.data.LibraryItemIdData;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LibraryItemIdEncoder extends JacksonMessageEncoder<LibraryItemIdData>
{
	@Override
	public String encodeJson(ObjectMapper objectMapper, LibraryItemIdData data) throws Exception
	{
		return objectMapper.writeValueAsString(data);
	}
}
