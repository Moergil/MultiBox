using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.ServerInterfaceEventArgs;
using MultiBoxCSharpCore.PlaylistEventArgs;

namespace MultiBoxCSharpCore
{
	class ServerPlaylistShadow : IPlaylist
	{
		private readonly IServerInterface serverInterface;
		private readonly IMessageQueue messageQueue;

		private IList<Multimedia> actualPlaylist;

		public event EventHandler<NewPlaylistEventArgs> PlaylistChanged;
		public event EventHandler<ItemAddedEventArgs> ItemAdded;

		public ServerPlaylistShadow(IServerInterface serverInterface, IMessageQueue messageQueue)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;

			actualPlaylist = new List<Multimedia>();
		}

		public void Init()
		{
			serverInterface.PlaylistReceived += OnPlaylistReceived;
			serverInterface.AddingLibraryItemToPlaylistResult += OnAddingLibraryItemToPlaylistResult;
			serverInterface.RequestPlaylistUpdate();
		}

		public void Close()
		{
			serverInterface.PlaylistReceived -= OnPlaylistReceived;
			serverInterface.AddingLibraryItemToPlaylistResult -= OnAddingLibraryItemToPlaylistResult;
		}

		public IList<Multimedia> Items
		{
			get { return new List<Multimedia>(actualPlaylist); }
		}

		public void AddItem(long itemId)
		{
			serverInterface.AddLibraryItemToPlaylist(itemId);
		}

		private void OnPlaylistReceived(object sender, PlaylistReceivedEventArgs eventArgs)
		{
		}

		private void updatePlaylist(IList<Multimedia> playlist)
		{
			actualPlaylist.Clear();
			foreach (Multimedia multimedia in playlist)
			{
				actualPlaylist.Add(multimedia);
			}

			NewPlaylistEventArgs eventArgs = new NewPlaylistEventArgs(new List<Multimedia>(actualPlaylist));
			messageQueue.Post(() => PlaylistChanged(this, eventArgs));
		}

		private void OnAddingLibraryItemToPlaylistResult(object sender, AddingLibraryItemToPlaylistResultEventArgs eventArgs)
		{
			bool success = eventArgs.Result;
			Multimedia multimedia = eventArgs.Multimedia;

			ItemAddedEventArgs itemAddedEventArgs = new ItemAddedEventArgs(success, multimedia);
			messageQueue.Post(() => ItemAdded(this, itemAddedEventArgs));
		}
	}
}
