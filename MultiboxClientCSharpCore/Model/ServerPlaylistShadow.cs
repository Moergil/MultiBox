using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CSharpNetInterface.Util;
using MultiboxClientCSharpCore.Net;
using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;
using MultiboxClientCSharpCore.Model.PlaylistEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	class ServerPlaylistShadow : IPlaylist
	{
		private readonly IServerInterface serverInterface;
		private readonly IMessageQueue messageQueue;

		private IList<MultimediaItem> actualPlaylist;

		public event EventHandler<PlaylistChangedEventArgs> PlaylistChanged = delegate { };
		public event EventHandler<ItemAddedEventArgs> ItemAdded = delegate { };

		private PeriodicWorkDispatcher stateChecker;

		public ServerPlaylistShadow(IServerInterface serverInterface, IMessageQueue messageQueue)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;

			actualPlaylist = new List<MultimediaItem>();

			this.stateChecker = new PeriodicWorkDispatcher(messageQueue, 5000, UpdatePlaylist);
		}

		public void Init()
		{
			serverInterface.PlaylistReceived += OnPlaylistReceived;
			serverInterface.AddingLibraryItemToPlaylistResult += OnAddingLibraryItemToPlaylistResult;

			UpdatePlaylist();

			stateChecker.Start();
		}

		public void Close()
		{
			serverInterface.PlaylistReceived -= OnPlaylistReceived;
			serverInterface.AddingLibraryItemToPlaylistResult -= OnAddingLibraryItemToPlaylistResult;

			stateChecker.Stop();
		}

		private void UpdatePlaylist()
		{
			serverInterface.RequestPlaylistUpdate();
		}

		public IList<MultimediaItem> Items
		{
			get { return new List<MultimediaItem>(actualPlaylist); }
		}

		public void AddItem(long itemId)
		{
			serverInterface.AddLibraryItemToPlaylist(itemId);
		}

		private void OnPlaylistReceived(object sender, PlaylistReceivedEventArgs eventArgs)
		{
			UpdatePlaylist(eventArgs.Playlist);
		}

		private void UpdatePlaylist(IList<MultimediaItem> playlist)
		{
			actualPlaylist.Clear();
			foreach (MultimediaItem multimedia in playlist)
			{
				actualPlaylist.Add(multimedia);
			}

			PlaylistChangedEventArgs eventArgs = new PlaylistChangedEventArgs(new List<MultimediaItem>(actualPlaylist));
			messageQueue.Post(() => PlaylistChanged(this, eventArgs));
		}

		private void OnAddingLibraryItemToPlaylistResult(object sender, AddingLibraryItemToPlaylistResultEventArgs eventArgs)
		{
			bool success = eventArgs.Result;
			MultimediaItem multimedia = eventArgs.Multimedia;

			if (actualPlaylist.Count <= 1)
			{
				return;
			}

			IList<MultimediaItem> newPlaylist = new List<MultimediaItem>(actualPlaylist);
			newPlaylist.Add(multimedia);

			UpdatePlaylist(newPlaylist);

			ItemAddedEventArgs itemAddedEventArgs = new ItemAddedEventArgs(success, multimedia);
			messageQueue.Post(() => ItemAdded(this, itemAddedEventArgs));
		}
	}
}
