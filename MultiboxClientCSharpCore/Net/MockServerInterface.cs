using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Util;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Model.LibraryItems;
using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;

namespace MultiboxClientCSharpCore.Net
{
	public class MockServerInterface : IServerInterface
	{
		private IMessageQueue messageQueue;

		public MockServerInterface(IMessageQueue messageQueue)
		{
			this.messageQueue = messageQueue;
		}

		public void RequestServerInfo()
		{
		}

		public void RequestPlayerUpdate()
		{
			MultimediaItem multimedia = new MultimediaItem(1, "Mock", 13);
			int playbackPosition = 5;
			bool playing = true;

			PlayerUpdateReceivedEventArgs eventArgs = new PlayerUpdateReceivedEventArgs(multimedia, playbackPosition, playing);

			messageQueue.Post(() => PlayerUpdateReceived(this, eventArgs));
		}

		public void RequestPlaylistUpdate()
		{
			IList<MultimediaItem> playlist = new List<MultimediaItem>();
			playlist.Add(new MultimediaItem(1, "song 1", 5));
			playlist.Add(new MultimediaItem(2, "song 2", 5));
			playlist.Add(new MultimediaItem(3, "song 3", 5));

			PlaylistReceivedEventArgs eventArgs = new PlaylistReceivedEventArgs(playlist);

			messageQueue.Post(() => PlaylistReceived(this, eventArgs));
		}

		public void RequestLibraryItem(long itemId)
		{
		}

		public void AddLibraryItemToPlaylist(long itemId)
		{
		}

		public void Close()
		{
		}

		public event EventHandler<DisconnectEventArgs> Disconnect;

		public event EventHandler<ServerInfoEventArgs> ServerInfo;

		public event EventHandler<AuthentificationResponseEventArgs> AuthentificationResponse;

		public event EventHandler<PlayerUpdateReceivedEventArgs> PlayerUpdateReceived;

		public event EventHandler<PlaylistReceivedEventArgs> PlaylistReceived;

		public event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived;

		public event EventHandler<AddingLibraryItemToPlaylistResultEventArgs> AddingLibraryItemToPlaylistResult;
	}
}
