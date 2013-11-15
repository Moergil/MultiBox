package sk.hackcraft.multibox.net.parsers;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.net.results.GetPlayerStateResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetPlayerStateParser extends JacksonMessageParser<GetPlayerStateResult>
{
	@Override
	public GetPlayerStateResult jacksonParse(ObjectMapper objectMapper, String dataString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(dataString);
		
		String multimediaJsonString = rootNode.path("multimedia").toString();
				
		Multimedia.Builder multimediaBuilder = objectMapper.readValue(multimediaJsonString, Multimedia.Builder.class);
		Multimedia multimedia = multimediaBuilder.create();
		
		int playbackPosition = rootNode.path("playbackPosition").asInt();
		boolean playing = rootNode.path("playing").asBoolean();
		
		return new GetPlayerStateResult(multimedia, playbackPosition, playing);
	}
}