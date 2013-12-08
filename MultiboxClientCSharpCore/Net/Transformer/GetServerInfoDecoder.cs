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
	class GetServerInfoDecoder : IDataTransformer<string, GetServerInfoResultData>
	{
		public GetServerInfoResultData Transform(string input)
		{
			JObject rootNode = JObject.Parse(input);

			string serverName = rootNode["serverName"].ToString();

			return new GetServerInfoResultData(serverName);
		}
	}
}
