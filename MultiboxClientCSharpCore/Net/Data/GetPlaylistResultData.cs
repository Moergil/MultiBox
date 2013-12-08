using MultiboxClientCSharpCore.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MultiboxClientCSharpCore.Net.Data
{
	public class GetPlaylistResultData
	{
		public GetPlaylistResultData(IList<MultimediaItem> playlist)
		{
			this.Playlist = new List<MultimediaItem>(playlist);
		}

		public IList<MultimediaItem> Playlist
		{
			get;
			private set;
		}
	}
}
