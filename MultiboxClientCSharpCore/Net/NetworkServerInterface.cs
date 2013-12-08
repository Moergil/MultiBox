using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CSharpNetInterface;
using CSharpNetInterface.Message;
using CSharpNetInterface.Util;
using CSharpNetInterface.Connection;
using CSharpNetInterface.Connection.AsynchronousMessageInterfaceEventArgs;
using MultiboxClientCSharpCore.Net.Data;
using MultiboxClientCSharpCore.Net.Transformer;
using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;
using MultiboxClientCSharpCore.Model;

namespace MultiboxClientCSharpCore.Net
{
	public class NetworkServerInterface : IServerInterface
	{
		private IAsynchronousMessageInterface messageInterface;
		private IMessageQueue messageQueue;

		public NetworkServerInterface(IAsynchronousMessageInterface messageInterface, IMessageQueue messageQueue)
		{
			this.messageInterface = messageInterface;
			this.messageQueue = messageQueue;

			messageInterface.SeriousError += OnSeriousError;

			messageInterface.SetMessageReceiver(MessageTypes.GetPlayerState, new GetPlayerStateReceiver(messageQueue, OnPlayerStateReceived));
			messageInterface.SetMessageReceiver(MessageTypes.GetPlaylist, new GetPlaylistReceiver(messageQueue, OnPlaylistReceived));
			messageInterface.SetMessageReceiver(MessageTypes.GetLibraryItem, new GetLibraryItemReceiver(messageQueue, OnLibraryItemReceived));
			messageInterface.SetMessageReceiver(MessageTypes.AddLibraryItemToPlaylist, new AddMultimediaToPlaylistReceiver(messageQueue, OnAddMultimediaToPlaylistResult));
			messageInterface.SetMessageReceiver(MessageTypes.GetServerInfo, new GetServerInfoReceiver(messageQueue, OnServerInfoReceived));
		}

		public void RequestServerInfo()
		{
			IMessage message = new EmptyMessage(MessageTypes.GetServerInfo);
			messageInterface.SendMessage(message);
		}

		public void RequestPlayerUpdate()
		{
			IMessage message = new EmptyMessage(MessageTypes.GetPlayerState);
			messageInterface.SendMessage(message);
		}

		public void RequestPlaylistUpdate()
		{
			IMessage message = new EmptyMessage(MessageTypes.GetPlaylist);
			messageInterface.SendMessage(message);
		}

		public void RequestLibraryItem(long itemId)
		{
			GetLibraryItemData dataObject = new GetLibraryItemData(itemId);
			GetLibraryItemEncoder encoder = new GetLibraryItemEncoder();

			string data = encoder.Transform(dataObject);

			DataStringMessage message = new DataStringMessage(MessageTypes.GetLibraryItem, data);
			messageInterface.SendMessage(message);
		}

		public void AddLibraryItemToPlaylist(long itemId)
		{
			AddLibraryItemToPlaylistData dataObject = new AddLibraryItemToPlaylistData(itemId);
			AddLibraryItemToPlaylistEncoder encoder = new AddLibraryItemToPlaylistEncoder();

			string data = encoder.Transform(dataObject);

			DataStringMessage message = new DataStringMessage(MessageTypes.AddLibraryItemToPlaylist, data);
			messageInterface.SendMessage(message);
		}

		public void Close()
		{
			messageInterface.Close();
		}

		private void OnSeriousError(object sender, SeriousErrorEventArgs eventArgs)
		{
			DisconnectEventArgs disconnectEventArgs = new DisconnectEventArgs();
			Disconnect(this, disconnectEventArgs);
		}

		public event EventHandler<DisconnectEventArgs> Disconnect = delegate { };

		public event EventHandler<ServerInfoEventArgs> ServerInfo = delegate { };

		public event EventHandler<AuthentificationResponseEventArgs> AuthentificationResponse = delegate { };

		public event EventHandler<PlayerUpdateReceivedEventArgs> PlayerUpdateReceived = delegate { };

		public event EventHandler<PlaylistReceivedEventArgs> PlaylistReceived = delegate { };

		public event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived = delegate { };

		public event EventHandler<AddingLibraryItemToPlaylistResultEventArgs> AddingLibraryItemToPlaylistResult = delegate { };

		private class GetPlayerStateReceiver : DataStringMessageReceiver<GetPlayerStateResultData>
		{
			public GetPlayerStateReceiver(IMessageQueue messageQueue, Action<GetPlayerStateResultData> listener)
			: base(messageQueue, new GetPlayerStateDecoder(), listener)
			{
			}
		}

		private class GetPlaylistReceiver : DataStringMessageReceiver<GetPlaylistResultData>
		{
			public GetPlaylistReceiver(IMessageQueue messageQueue, Action<GetPlaylistResultData> listener)
			: base(messageQueue, new GetPlaylistDecoder(), listener)
			{
			}
		}

		private class GetLibraryItemReceiver : DataStringMessageReceiver<GetLibraryItemResultData>
		{
			public GetLibraryItemReceiver(IMessageQueue messageQueue, Action<GetLibraryItemResultData> listener)
			: base(messageQueue, new GetLibraryItemDecoder(), listener)
			{
			}
		}

		private class AddMultimediaToPlaylistReceiver : DataStringMessageReceiver<AddLibraryItemToPlaylistResultData>
		{
			public AddMultimediaToPlaylistReceiver(IMessageQueue messageQueue, Action<AddLibraryItemToPlaylistResultData> listener)
			: base(messageQueue, new AddLibraryItemToPlaylistDecoder(), listener)
			{
			}
		}

		private class GetServerInfoReceiver : DataStringMessageReceiver<GetServerInfoResultData>
		{
			public GetServerInfoReceiver(IMessageQueue messageQueue, Action<GetServerInfoResultData> listener)
			: base(messageQueue, new GetServerInfoDecoder(), listener)
			{
			}
		}

		private void OnPlayerStateReceived(GetPlayerStateResultData result)
		{
			MultimediaItem multimedia = result.Multimedia;
			int playbackPosition = result.PlaybackPosition;
			bool playing = result.Playing;
			
			PlayerUpdateReceivedEventArgs eventArgs = new PlayerUpdateReceivedEventArgs(multimedia, playbackPosition, playing);
			PlayerUpdateReceived(this, eventArgs);
		}

		private void OnPlaylistReceived(GetPlaylistResultData result)
		{
			IList<MultimediaItem> playlist = result.Playlist;
			
			PlaylistReceivedEventArgs eventArgs = new PlaylistReceivedEventArgs(playlist);
			PlaylistReceived(this, eventArgs);
		}

		private void OnLibraryItemReceived(GetLibraryItemResultData result)
		{
			ILibraryItem libraryItem = result.LibraryItem;

			LibraryItemReceivedEventArgs eventArgs = new LibraryItemReceivedEventArgs(libraryItem);
			LibraryItemReceived(this, eventArgs);
		}

		private void OnAddMultimediaToPlaylistResult(AddLibraryItemToPlaylistResultData result)
		{
			bool success = result.Success;
			MultimediaItem multimedia = (MultimediaItem)result.LibraryItem;
			
			AddingLibraryItemToPlaylistResultEventArgs eventArgs = new AddingLibraryItemToPlaylistResultEventArgs(success, multimedia);
			AddingLibraryItemToPlaylistResult(this, eventArgs);
		}

		private void OnServerInfoReceived(GetServerInfoResultData result)
		{
			string serverName = result.ServerName;

			ServerInfoEventArgs eventArgs = new ServerInfoEventArgs(serverName);
			ServerInfo(this, eventArgs);
		}
	}
}
