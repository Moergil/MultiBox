using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;
using MultiboxClientCSharpCore.Model;

namespace MultiboxClientCSharpCore.Net
{
	public interface IServerInterface
	{
		void RequestServerInfo();

		void RequestPlayerUpdate();
		void RequestPlaylistUpdate();

		void RequestLibraryItem(long itemId);
		void AddLibraryItemToPlaylist(long itemId);

		void Close();

		event EventHandler<DisconnectEventArgs> Disconnect;

		event EventHandler<ServerInfoEventArgs> ServerInfo;

		event EventHandler<AuthentificationResponseEventArgs> AuthentificationResponse;

		event EventHandler<PlayerUpdateReceivedEventArgs> PlayerUpdateReceived;
		event EventHandler<PlaylistReceivedEventArgs> PlaylistReceived;

		event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived;

		event EventHandler<AddingLibraryItemToPlaylistResultEventArgs> AddingLibraryItemToPlaylistResult;
	}

	namespace ServerInterfaceEventArgs
	{
		public class DisconnectEventArgs : EventArgs
		{
		}

		public class ServerInfoEventArgs : EventArgs
		{
			public ServerInfoEventArgs(string serverName)
			{
				this.ServerName = serverName;
			}

			public string ServerName
			{
				get;
				private set;
			}
		}

		public class AuthentificationResponseEventArgs : EventArgs
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

		public class PlayerUpdateReceivedEventArgs : EventArgs
		{
			public PlayerUpdateReceivedEventArgs(MultimediaItem multimedia, int playbackPosition, bool playing)
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

		public class PlaylistReceivedEventArgs : EventArgs
		{
			public PlaylistReceivedEventArgs(IList<MultimediaItem> playlist)
			{
				this.Playlist = new List<MultimediaItem>(playlist);
			}

			public IList<MultimediaItem> Playlist
			{
				get;
				private set;
			}
		}

		public class LibraryItemReceivedEventArgs : EventArgs
		{
			public LibraryItemReceivedEventArgs(ILibraryItem item)
			{
				this.LibraryItem = item;
			}

			public ILibraryItem LibraryItem
			{
				get;
				private set;
			}
		}

		public class AddingLibraryItemToPlaylistResultEventArgs : EventArgs
		{
			public AddingLibraryItemToPlaylistResultEventArgs(bool result, MultimediaItem multimedia)
			{
				this.Result = result;
				this.Multimedia = multimedia;
			}

			public bool Result
			{
				get;
				private set;
			}

			public MultimediaItem Multimedia
			{
				get;
				private set;
			}
		}
	}
}
