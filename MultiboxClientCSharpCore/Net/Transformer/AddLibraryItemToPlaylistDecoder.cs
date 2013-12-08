using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Message;
using MultiboxClientCSharpCore.Net.Data;
using MultiboxClientCSharpCore.Model;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace MultiboxClientCSharpCore.Net.Transformer
{
	public class AddLibraryItemToPlaylistDecoder : IDataTransformer<string, AddLibraryItemToPlaylistResultData>
	{
		public AddLibraryItemToPlaylistResultData Transform(string input)
		{
			JObject rootNode = JObject.Parse(input);

			bool result = rootNode["result"].ToObject<bool>();

			if (!result)
			{
				return new AddLibraryItemToPlaylistResultData(result, null);
			}
			else
			{

				JObject libraryItemNode = rootNode["multimedia"] as JObject;

				long id = libraryItemNode["id"].ToObject<long>();
				LibraryItemType type = LibraryItemTypeParser.Parse(libraryItemNode["type"].ToObject<string>());

				if (type != LibraryItemType.Multimedia)
				{
					throw new ArgumentException("Only MULTIMEDIA type is supported for now.");
				}
				
				string name = libraryItemNode["name"].ToObject<string>();
				int length = libraryItemNode["length"].ToObject<int>();

				MultimediaItem multimedia = new MultimediaItem(id, name, length);

				return new AddLibraryItemToPlaylistResultData(result, multimedia);
			}
		}
	}
}
