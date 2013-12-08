using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CSharpNetInterface.Util;
using MultiboxClientCSharpCore.Net;
using MultiboxClientCSharpCore.Model.LibraryEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	class ServerLibraryShadow : ILibrary
	{
		private readonly IServerInterface serverInterface;
		private readonly IMessageQueue messageQueue;

		public event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived;

		private readonly PeriodicWorkDispatcher stateChecker;

		private long lastRequestedId = LibraryConstants.RootDirectory;

		public ServerLibraryShadow(IServerInterface serverInterface, IMessageQueue messageQueue)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;

			this.stateChecker = new PeriodicWorkDispatcher(messageQueue, 5000, UpdateLibrary);
		}

		private void OnLibraryItemReceived(object sender, MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs.LibraryItemReceivedEventArgs eventArgs)
		{
			ILibraryItem item = eventArgs.LibraryItem;
			LibraryItemReceivedEventArgs libraryEventArgs = new LibraryItemReceivedEventArgs(item);

			messageQueue.Post(() => LibraryItemReceived(this, libraryEventArgs));
		}
				
		public void Init()
		{
			serverInterface.LibraryItemReceived += OnLibraryItemReceived;

			stateChecker.Start();
		}

		public void Close()
		{
			serverInterface.LibraryItemReceived -= OnLibraryItemReceived;

			stateChecker.Stop();
		}

		private void UpdateLibrary()
		{
			RequestItem(lastRequestedId);
		}

		public void RequestItem(long id)
		{
			serverInterface.RequestLibraryItem(id);

			this.lastRequestedId = id;
		}
	}
}
