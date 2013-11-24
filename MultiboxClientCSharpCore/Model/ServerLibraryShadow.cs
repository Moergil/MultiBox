using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.ServerInterfaceEventArgs;
using MultiBoxCSharpCore.LibraryEventArgs;

namespace MultiBoxCSharpCore
{
	class ServerLibraryShadow : ILibrary
	{
		private readonly IServerInterface serverInterface;
		private readonly IMessageQueue messageQueue;

		public event EventHandler<ItemReceivedEventArgs> ItemReceived;

		public ServerLibraryShadow(IServerInterface serverInterface, IMessageQueue messageQueue)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;
		}

		private void OnLibraryItemReceived(object sender, LibraryItemReceivedEventArgs eventArgs)
		{
			ILibraryItem item = eventArgs.Item;
			ItemReceivedEventArgs libraryEventArgs = new ItemReceivedEventArgs(item);

			messageQueue.Post(() => ItemReceived(this, libraryEventArgs));
		}
				
		public void Init()
		{
			serverInterface.LibraryItemReceived += OnLibraryItemReceived;
		}

		public void Close()
		{
			serverInterface.LibraryItemReceived -= OnLibraryItemReceived;
		}

		public void RequestItem(long id)
		{
			serverInterface.RequestLibraryItem(id);
		}
	}
}
