package sk.hackcraft.multibox.net;

import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.net.data.AddMultimediaToPlaylistResultData;
import sk.hackcraft.multibox.net.data.GetPlayerStateResultData;
import sk.hackcraft.multibox.net.data.GetPlaylistResultData;
import sk.hackcraft.multibox.net.data.LibraryItemIdData;
import sk.hackcraft.multibox.net.messages.DataStringMessage;
import sk.hackcraft.multibox.net.messages.PlainRequestMessage;
import sk.hackcraft.multibox.net.receivers.DataStringMessageReceiver;
import sk.hackcraft.multibox.net.transformers.AddMultimediaToPlaylistDecoder;
import sk.hackcraft.multibox.net.transformers.GetPlayerStateDecoder;
import sk.hackcraft.multibox.net.transformers.GetPlaylistDecoder;
import sk.hackcraft.multibox.net.transformers.LibraryItemIdEncoder;
import sk.hackcraft.multibox.util.DataTransformer;
import sk.hackcraft.netinterface.AsynchronousMessageInterface;
import sk.hackcraft.netinterface.AsynchronousMessageInterface.SeriousErrorListener;
import sk.hackcraft.netinterface.Message;
import sk.hackcraft.util.MessageQueue;

public class NetworkServerInterface implements ServerInterface
{
	private final AsynchronousMessageInterface messageInterface;
	private final MessageQueue messageQueue;
	
	private final List<ServerInterfaceEventListener> serverListeners;
	
	public NetworkServerInterface(AsynchronousMessageInterface messageInterface, MessageQueue messageQueue)
	{
		this.messageInterface = messageInterface;
		this.messageQueue = messageQueue;
		
		messageInterface.setSeriousErrorListener(new NetworkSeriousErrorListener());
		
		this.serverListeners = new LinkedList<ServerInterface.ServerInterfaceEventListener>();

		messageInterface.setMessageReceiver(MessageTypes.GET_PLAYER_STATE, new GetPlayerStateReceiver(messageQueue));
		messageInterface.setMessageReceiver(MessageTypes.GET_PLAYLIST, new GetPlaylistReceiver(messageQueue));
		messageInterface.setMessageReceiver(MessageTypes.ADD_LIBRARY_ITEM_TO_PLAYLIST, new AddMultimediaToPlaylistReceiver(messageQueue));
	}

	@Override
	public void registerEventListener(ServerInterfaceEventListener listener)
	{
		serverListeners.add(listener);
	}

	@Override
	public void unregisterEventListener(ServerInterfaceEventListener listener)
	{
		serverListeners.remove(listener);
	}

	@Override
	public void requestPlayerUpdate()
	{
		Message message = new PlainRequestMessage(MessageTypes.GET_PLAYER_STATE);
		messageInterface.sendMessage(message);
	}

	@Override
	public void requestPlaylistUpdate()
	{
		Message message = new PlainRequestMessage(MessageTypes.GET_PLAYLIST);
		messageInterface.sendMessage(message);
	}

	@Override
	public void requestLibraryItem(long itemId)
	{
		LibraryItemIdData data = new LibraryItemIdData(itemId);
		Message message = new DataStringMessage<LibraryItemIdData>(MessageTypes.GET_LIBRARY_ITEM, data)
		{
			@Override
			protected DataTransformer<LibraryItemIdData, String> createEncoder()
			{
				return new LibraryItemIdEncoder();
			}
		};
		
		messageInterface.sendMessage(message);
	}
	
	@Override
	public void addLibraryItemToPlaylist(long itemId)
	{
		LibraryItemIdData data = new LibraryItemIdData(itemId);
		Message message = new DataStringMessage<LibraryItemIdData>(MessageTypes.ADD_LIBRARY_ITEM_TO_PLAYLIST, data)
		{
			@Override
			protected DataTransformer<LibraryItemIdData, String> createEncoder()
			{
				return new LibraryItemIdEncoder();
			}
		};
		
		messageInterface.sendMessage(message);
	}
	
	private class NetworkSeriousErrorListener implements SeriousErrorListener
	{
		@Override
		public void onSeriousError(String errorDescription)
		{
			for (final ServerInterface.ServerInterfaceEventListener listener : serverListeners)
			{
				messageQueue.post(new Runnable()
				{
					@Override
					public void run()
					{
						listener.onDisconnect();
					}
				});
			}
		}
	}
	
	private class GetPlayerStateReceiver extends DataStringMessageReceiver<GetPlayerStateResultData>
	{
		public GetPlayerStateReceiver(MessageQueue messageQueue)
		{
			super(messageQueue);
		}
		
		@Override
		protected DataTransformer<String, GetPlayerStateResultData> createParser()
		{
			return new GetPlayerStateDecoder();
		}

		@Override
		public void onResult(GetPlayerStateResultData result)
		{
			Multimedia multimedia = result.getMultimedia();
			int playbackPosition = result.getPlaybackPosition();
			boolean playing = result.isPlaying();
			
			for (ServerInterfaceEventListener listener : serverListeners)
			{
				listener.onPlayerUpdateReceived(multimedia, playbackPosition, playing);
			}
		}
	}
	
	private class GetPlaylistReceiver extends DataStringMessageReceiver<GetPlaylistResultData>
	{
		public GetPlaylistReceiver(MessageQueue messageQueue)
		{
			super(messageQueue);
		}

		@Override
		protected DataTransformer<String, GetPlaylistResultData> createParser()
		{
			return new GetPlaylistDecoder();
		}

		@Override
		protected void onResult(GetPlaylistResultData result)
		{
			List<Multimedia> playlist = result.getPlaylist();
			
			for (ServerInterfaceEventListener listener : serverListeners)
			{
				listener.onPlaylistReceived(playlist);
			}
		}
	}
	
	private class AddMultimediaToPlaylistReceiver extends DataStringMessageReceiver<AddMultimediaToPlaylistResultData>
	{
		public AddMultimediaToPlaylistReceiver(MessageQueue messageQueue)
		{
			super(messageQueue);
		}

		@Override
		protected DataTransformer<String, AddMultimediaToPlaylistResultData> createParser()
		{
			return new AddMultimediaToPlaylistDecoder();
		}

		@Override
		protected void onResult(AddMultimediaToPlaylistResultData result)
		{
			final boolean success = result.isSuccess();
			final Multimedia multimedia = result.getMultimedia();
			
			for (ServerInterfaceEventListener listener : serverListeners)
			{
				listener.onAddingLibraryItemToPlaylistResult(success, multimedia);
			}
		}
	}
}
