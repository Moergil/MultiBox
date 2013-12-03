package sk.hackcraft.multibox.net.transformers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import sk.hackcraft.multibox.net.data.GetPlaylistResultData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetPlaylistDecoder extends JacksonMessageDecoder<GetPlaylistResultData>
{
	@Override
	public GetPlaylistResultData decodeJson(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);
		Iterator<JsonNode> nodesIterator = rootNode.path("playlist").elements();
		
		List<MultimediaItem> playlist = new LinkedList<MultimediaItem>();
		while (nodesIterator.hasNext())
		{
			JsonNode node = nodesIterator.next();
			String multimediaJson = node.toString();
			
			MultimediaItem.Builder builder = objectMapper.readValue(multimediaJson, MultimediaItem.Builder.class);
			MultimediaItem multimedia = builder.create();
			
			playlist.add(multimedia);
		}

		return new GetPlaylistResultData(playlist);
	}
}
