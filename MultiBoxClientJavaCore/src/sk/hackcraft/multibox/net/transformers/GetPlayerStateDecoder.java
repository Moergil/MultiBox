package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import sk.hackcraft.multibox.net.data.GetPlayerStateResultData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GetPlayerStateDecoder extends JacksonMessageDecoder<GetPlayerStateResultData>
{
	@Override
	public GetPlayerStateResultData decodeJson(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);

		ObjectNode multimediaObjectNode = (ObjectNode)rootNode.path("multimedia");
		
		long id = multimediaObjectNode.path("id").asLong();
		String name = multimediaObjectNode.path("name").asText();
		int length = multimediaObjectNode.path("length").asInt();
		
		MultimediaItem multimedia = new MultimediaItem(id, name, length);
		
		int playbackPosition = rootNode.path("playbackPosition").asInt();
		boolean playing = rootNode.path("playing").asBoolean();
		
		return new GetPlayerStateResultData(multimedia, playbackPosition, playing);
	}
}