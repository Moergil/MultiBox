package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.net.data.AddMultimediaToPlaylistResultData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddMultimediaToPlaylistDecoder extends JacksonMessageDecoder<AddMultimediaToPlaylistResultData>
{
	@Override
	public AddMultimediaToPlaylistResultData decodeJson(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);
		
		boolean result = rootNode.path("result").asBoolean();
		
		String multimediaJson = rootNode.path("multimedia").toString();
		Multimedia.Builder multimediaBuilder = objectMapper.readValue(multimediaJson, Multimedia.Builder.class);
		Multimedia multimedia = multimediaBuilder.create();
		
		return new AddMultimediaToPlaylistResultData(result, multimedia);
	}
}
