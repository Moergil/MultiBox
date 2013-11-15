package sk.hackcraft.multibox.net.parsers;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.net.results.AddMultimediaToPlaylistResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddMultimediaToPlaylistParser extends JacksonMessageParser<AddMultimediaToPlaylistResult>
{
	@Override
	public AddMultimediaToPlaylistResult jacksonParse(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);
		
		boolean result = rootNode.path("result").asBoolean();
		
		String multimediaJson = rootNode.path("multimedia").toString();
		Multimedia.Builder multimediaBuilder = objectMapper.readValue(multimediaJson, Multimedia.Builder.class);
		Multimedia multimedia = multimediaBuilder.create();
		
		return new AddMultimediaToPlaylistResult(result, multimedia);
	}
}
