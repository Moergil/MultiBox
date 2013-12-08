using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Message;
using MultiboxClientCSharpCore.Net.Data;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace MultiboxClientCSharpCore.Net.Transformer
{
	public class AddLibraryItemToPlaylistEncoder : IDataTransformer<AddLibraryItemToPlaylistData, string>
	{
		public string Transform(AddLibraryItemToPlaylistData input)
		{
			long itemId = input.ItemId;

			JObject rootNode = new JObject();

			rootNode["multimediaId"] = itemId;

			return rootNode.ToString();
		}
	}
}
