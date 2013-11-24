using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.PlayerEventArgs;

namespace MultiBoxCSharpCore
{
	public interface IPlayer
	{
		void Init();
		void Close();

		bool Playing { get; }

		int PlaybackPosition { get; }
		Multimedia ActiveMultimedia { get; }

		void RequestPlayingStateChange(bool playing);
		void RequestActiveMultimediaSkip();

		event EventHandler<PlayingStateChangedEventArgs> PlayingStateChanged;
		event EventHandler<PlaybackPositionChangedEventArgs> PlaybackPositionChanged;
		event EventHandler<MultimediaChangedEventArgs> MultimediaChanged;
	}

	namespace PlayerEventArgs
	{
		public class PlayingStateChangedEventArgs : EventArgs
		{
			public PlayingStateChangedEventArgs(bool playing)
			{
				this.Playing = playing;
			}

			public bool Playing
			{
				get;
				private set;
			}
		}

		public class PlaybackPositionChangedEventArgs : EventArgs
		{
			public PlaybackPositionChangedEventArgs(int newPosition)
			{
				this.Position = newPosition;
			}

			public int Position
			{
				get;
				private set;
			}
		}

		public class MultimediaChangedEventArgs : EventArgs
		{
			public MultimediaChangedEventArgs(Multimedia multimedia)
			{
			}

			public Multimedia Multimedia
			{
				get;
				private set;
			}
		}
	}
}
