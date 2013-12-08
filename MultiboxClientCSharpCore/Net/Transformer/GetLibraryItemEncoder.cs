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
	public class GetLibraryItemEncoder : IDataTransformer<GetLibraryItemData, string>
	{
		public string Transform(GetLibraryItemData input)
		{
			long itemId = input.ItemId;

			JObject rootNode = new JObject();

			rootNode["itemId"] = itemId;

			return rootNode.ToString();
		}
	}
}
