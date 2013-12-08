using MultiboxClientCSharpCore.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MultiboxClientCSharpCore.Net.Data
{
	public class GetPlayerStateResultData
	{
		public GetPlayerStateResultData(MultimediaItem multimedia, int playbackPosition, bool playing)
		{
			this.Multimedia = multimedia;
			this.PlaybackPosition = playbackPosition;
			this.Playing = playing;
		}
	
		public MultimediaItem Multimedia
		{
			get;
			private set;
		}

		public int PlaybackPosition
		{
			get;
			private set;
		}

		public bool Playing
		{
			get;
			private set;
		}
	}
}
