package sk.hackcraft.multibox.net.transformers;

import sk.hackcraft.multibox.model.GenericLibraryItem;
import sk.hackcraft.multibox.model.LibraryItem;
import sk.hackcraft.multibox.model.LibraryItemType;
import sk.hackcraft.multibox.model.libraryitems.DirectoryItem;
import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import sk.hackcraft.multibox.net.data.GetLibraryItemData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetLibraryItemDecoder extends JacksonMessageDecoder<GetLibraryItemData>
{
	@Override
	public GetLibraryItemData decodeJson(ObjectMapper objectMapper, String jsonString) throws Exception
	{
		JsonNode rootNode = objectMapper.readTree(jsonString);

		try
		{
			long id = rootNode.path("id").asLong();
			LibraryItemType type = LibraryItemType.valueOf(rootNode.path("type").asText());
			String name = rootNode.path("name").asText();
		
			LibraryItem libraryItem;
			switch (type)
			{
				case DIRECTORY:
					libraryItem = createDirectory(id, name, rootNode);
					break;
				case MULTIMEDIA:
					libraryItem = createMultimedia(id, name, rootNode);
					break;
				default:
					throw new IllegalArgumentException("Invalid library item type received from server.");
			}
			
			return new GetLibraryItemData(libraryItem);
		}
		catch (IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	private LibraryItem createMultimedia(long id, String name, JsonNode rootNode)
	{
		int length = rootNode.path("length").asInt();
		
		return new MultimediaItem(id, name, length);
	}

	private LibraryItem createDirectory(long id, String name, JsonNode rootNode)
	{
		DirectoryItem directory = new DirectoryItem(id, name);
		
		for (JsonNode node : rootNode.path("items"))
		{
			long itemId = node.path("id").asLong();
			LibraryItemType itemType = LibraryItemType.valueOf(node.path("type").asText());
			String itemName = rootNode.path("name").asText();
			
			GenericLibraryItem libraryItem = new GenericLibraryItem(itemId, itemType, itemName);
			directory.addItem(libraryItem);
		}
		
		return directory;
	}
}
