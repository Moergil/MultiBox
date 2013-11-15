package sk.hackcraft.multibox.net.parsers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.net.results.GetPlaylistResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetPlaylistParser extends JacksonMessageParser<GetPlaylistResult>
{
	@Override
	public GetPlaylistResult jacksonParse(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);
		Iterator<JsonNode> nodesIterator = rootNode.path("playlist").elements();
		
		List<Multimedia> playlist = new LinkedList<Multimedia>();
		while (nodesIterator.hasNext())
		{
			JsonNode node = nodesIterator.next();
			String multimediaJson = node.toString();
			
			Multimedia.Builder builder = objectMapper.readValue(multimediaJson, Multimedia.Builder.class);
			Multimedia multimedia = builder.create();
			
			playlist.add(multimedia);
		}

		return new GetPlaylistResult(playlist);
	}
}
