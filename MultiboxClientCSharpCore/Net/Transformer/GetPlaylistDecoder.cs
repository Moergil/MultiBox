using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Newtonsoft.Json.Linq;

using CSharpNetInterface.Message;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Net.Data;
using MultiboxClientCSharpCore.Net.Transformer.Json;

namespace MultiboxClientCSharpCore.Net.Transformer
{
	public class GetPlaylistDecoder : IDataTransformer<string, GetPlaylistResultData>
	{
		public GetPlaylistResultData Transform(string input)
		{
			JObject rootNode = JObject.Parse(input);

			JArray playlistArray = rootNode["playlist"] as JArray;

			IList<MultimediaItem> playlist = new List<MultimediaItem>();

			MultimediaJsonDecoder decoder = new MultimediaJsonDecoder();
			foreach (JObject entry in playlistArray)
			{
				MultimediaItem multimedia = decoder.Transform(entry);
				playlist.Add(multimedia);
			}

			return new GetPlaylistResultData(playlist);
		}
	}
}
