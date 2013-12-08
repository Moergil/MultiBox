using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Newtonsoft.Json.Linq;

using CSharpNetInterface.Message;
using MultiboxClientCSharpCore.Model;

namespace MultiboxClientCSharpCore.Net.Transformer.Json
{
	public class MultimediaJsonDecoder : IDataTransformer<JObject, MultimediaItem>
	{
		public MultimediaItem Transform(JObject input)
		{
			long id = input["id"].ToObject<long>();
			string name = input["name"].ToObject<string>();
			int length = input["length"].ToObject<int>();

			return new MultimediaItem(id, name, length);
		}
	}
}
