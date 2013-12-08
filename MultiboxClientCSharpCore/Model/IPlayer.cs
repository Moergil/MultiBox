using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiboxClientCSharpCore.Model.PlayerEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	public interface IPlayer
	{
		void Init();
		void Close();

		bool Playing { get; }

		int PlaybackPosition { get; }
		MultimediaItem ActiveMultimedia { get; }

		void RequestPlayingStateChange(bool playing);
		void RequestActiveMultimediaSkip();

		event EventHandler<PlayerStateChangedEventArgs> PlayerStateChanged;
	}

	namespace PlayerEventArgs
	{
		public class PlayerStateChangedEventArgs : EventArgs
		{
			public PlayerStateChangedEventArgs(MultimediaItem multimedia, int playbackPosition, bool playing)
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
}
