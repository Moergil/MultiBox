using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.ServerInterfaceEventArgs;

namespace MultiBoxCSharpCore
{
	interface IServerInterface
	{
		void RequestPlayerUpdate();
		void RequestPlaylistUpdate();

		void RequestLibraryItem(long itemId);
		void AddLibraryItemToPlaylist(long itemId);

		event EventHandler<EventArgs> Disconnect;

		event EventHandler<AuthentificationResponseEventArgs> AuthentificationResponse;

		event EventHandler<PlayerUpdateReceivedEventArgs> PlayerUpdateReceived;
		event EventHandler<PlaylistReceivedEventArgs> PlaylistReceived;

		event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived;

		event EventHandler<AddingLibraryItemToPlaylistResultEventArgs> AddingLibraryItemToPlaylistResult;
	}

	namespace ServerInterfaceEventArgs
	{
		class AuthentificationResponseEventArgs : EventArgs
		{
			public AuthentificationResponseEventArgs(bool succesfull)
			{
				this.Succesfull = succesfull;
			}

			public bool Succesfull
			{
				get;
				private set;
			}
		}

		class PlayerUpdateReceivedEventArgs : EventArgs
		{
			public PlayerUpdateReceivedEventArgs(Multimedia multimedia, int playbackPosition, bool playing)
			{
				this.Multimedia = multimedia;
				this.PlaybackPosition = playbackPosition;
				this.Playing = playing;
			}

			public Multimedia Multimedia
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

		public class PlaylistReceivedEventArgs : EventArgs
		{
			public PlaylistReceivedEventArgs(IList<Multimedia> playlist)
			{
				this.Playlist = new List<Multimedia>(playlist);
			}

			public IList<Multimedia> Playlist
			{
				get;
				private set;
			}
		}

		public class LibraryItemReceivedEventArgs : EventArgs
		{
			public LibraryItemReceivedEventArgs(ILibraryItem item)
			{
				this.Item = item;
			}

			public ILibraryItem Item
			{
				get;
				private set;
			}
		}

		public class AddingLibraryItemToPlaylistResultEventArgs : EventArgs
		{
			public AddingLibraryItemToPlaylistResultEventArgs(bool result, Multimedia multimedia)
			{
				this.Result = result;
				this.Multimedia = multimedia;
			}

			public bool Result
			{
				get;
				private set;
			}

			public Multimedia Multimedia
			{
				get;
				private set;
			}
		}
	}
}
