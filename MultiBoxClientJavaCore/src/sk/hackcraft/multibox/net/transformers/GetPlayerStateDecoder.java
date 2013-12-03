package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import sk.hackcraft.multibox.net.data.GetPlayerStateResultData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetPlayerStateDecoder extends JacksonMessageDecoder<GetPlayerStateResultData>
{
	@Override
	public GetPlayerStateResultData decodeJson(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);
		
		String multimediaJsonString = rootNode.path("multimedia").toString();
				
		MultimediaItem.Builder multimediaBuilder = objectMapper.readValue(multimediaJsonString, MultimediaItem.Builder.class);
		MultimediaItem multimedia = multimediaBuilder.create();
		
		int playbackPosition = rootNode.path("playbackPosition").asInt();
		boolean playing = rootNode.path("playing").asBoolean();
		
		return new GetPlayerStateResultData(multimedia, playbackPosition, playing);
	}
}