using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using CSharpNetInterface.Message;
using MultiboxClientCSharpCore.Net.Data;
using MultiboxClientCSharpCore.Net.Transformer.Json;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Util;

namespace MultiboxClientCSharpCore.Net.Transformer
{
	public class GetPlayerStateDecoder : IDataTransformer<string, GetPlayerStateResultData>
	{
		public GetPlayerStateResultData Transform(string input)
		{
			JObject rootNode = JObject.Parse(input);

			MultimediaJsonDecoder multimediaJsonDecoder = new MultimediaJsonDecoder();

			if (rootNode["multimedia"].ToString() == JsonValues.Null)
			{
				return new GetPlayerStateResultData(null, 0, false);
			}
			else
			{
				MultimediaItem multimedia = multimediaJsonDecoder.Transform(rootNode["multimedia"] as JObject);

				int playbackPosition = rootNode["playbackPosition"].ToObject<int>();
				bool playing = rootNode["playing"].ToObject<bool>();

				return new GetPlayerStateResultData(multimedia, playbackPosition, playing);
			}
		}
	}
}
