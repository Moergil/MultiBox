using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Message;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Model.LibraryItems;
using MultiboxClientCSharpCore.Net.Data;
using MultiboxClientCSharpCore.Net.Transformer.Json;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace MultiboxClientCSharpCore.Net.Transformer
{
	public class GetLibraryItemDecoder : IDataTransformer<string, GetLibraryItemResultData>
	{
		public GetLibraryItemResultData Transform(string input)
		{
			JObject rootNode = JObject.Parse(input);

			JObject libraryItemNode = rootNode["libraryItem"] as JObject;

			long id = libraryItemNode["id"].ToObject<long>();
			LibraryItemType type = LibraryItemTypeParser.Parse(libraryItemNode["type"].ToObject<string>());
			string name = libraryItemNode["name"].ToObject<string>();

			ILibraryItem libraryItem;
			switch (type)
			{
				case LibraryItemType.Directory:
					libraryItem = CreateDirectory(id, name, libraryItemNode);
					break;
				case LibraryItemType.Multimedia:
					libraryItem = CreateMultimedia(id, name, libraryItemNode);
					break;
				default:
					throw new ArgumentException("Invalid library item type received from server.");
			}

			return new GetLibraryItemResultData(libraryItem);
		}

		private ILibraryItem CreateMultimedia(long id, String name, JObject rootNode)
		{
			int length = rootNode["length"].ToObject<int>();

			return new MultimediaItem(id, name, length);
		}

		private ILibraryItem CreateDirectory(long id, String name, JObject rootNode)
		{
			DirectoryItem directory = new DirectoryItem(id, name);
		
			foreach (JObject node in (rootNode["items"] as JArray))
			{
				long itemId = node["id"].ToObject<long>();
				LibraryItemType itemType = LibraryItemTypeParser.Parse(node["type"].ToObject<string>());
				string itemName = node["name"].ToObject<string>();
			
				GenericLibraryItem libraryItem = new GenericLibraryItem(itemId, itemType, itemName);
				directory.AddItem(libraryItem);
			}
		
			return directory;
		}
	}
}
