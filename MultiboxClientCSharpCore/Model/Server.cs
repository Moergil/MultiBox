using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MultiboxClientCSharpCore.Net;
using CSharpNetInterface.Util;
using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	public class Server
	{
		private IServerInterface serverInterface;
		private IMessageQueue messageQueue;
		private ILog log;

		public Server(IServerInterface serverInterface, IMessageQueue messageQueue, ILog log)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;
			this.log = log;
		}

		public void RequestInfo(Action<string> listener)
		{
			RequestInfoListener requestInfoListener = new RequestInfoListener(listener);
			serverInterface.ServerInfo += requestInfoListener.OnServerInfoReceived;

			serverInterface.RequestServerInfo();
		}

		public IPlayer Player
		{
			get { return new ServerPlayerShadow(serverInterface, messageQueue); }
		}

		public IPlaylist Playlist
		{
			get { return new ServerPlaylistShadow(serverInterface, messageQueue); }
		}

		public ILibrary Library
		{
			get { return new ServerLibraryShadow(serverInterface, messageQueue); }
		}

		private class RequestInfoListener
		{
			private readonly Action<string> listener;

			public RequestInfoListener(Action<string> listener)
			{
				this.listener = listener;
			}

			public void OnServerInfoReceived(object sender, ServerInfoEventArgs eventArgs)
			{
				string serverName = eventArgs.ServerName;
				listener(serverName);

				IServerInterface serverInterface = sender as IServerInterface;
				serverInterface.ServerInfo -= OnServerInfoReceived;
			}
		}
	}
}
