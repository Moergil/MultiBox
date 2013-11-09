package sk.hackcraft.multibox.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOError;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.netinterface.DataMessage;
import sk.hackcraft.netinterface.DataMessageReceiver;
import sk.hackcraft.netinterface.IncomingMessagesRouter;
import sk.hackcraft.netinterface.MessageReceiver;
import sk.hackcraft.netinterface.MessageType;
import sk.hackcraft.netinterface.OldAsynchronousSocketInterface;
import sk.hackcraft.util.MessageQueue;
import sk.hackcraft.multibox.model.Multimedia;

public class SocketServerLazyInterface implements ServerInterface
{
	private final MessageQueue messageQueue;
	private final String serverAddress;
	private final IncomingMessagesRouter incomingMessagesRouter;
	
	private final String username;
	private final String password;
	
	private List<ServerInterfaceEventListener> serverListeners;
	
	private OldAsynchronousSocketInterface socketInterface;
	
	public SocketServerLazyInterface(MessageQueue messageQueue, String serverAddress, String username, String password)
	{
		this.messageQueue = messageQueue;
		this.serverAddress = serverAddress;
		
		this.username = username;
		this.password = password;
		
		serverListeners = new LinkedList<ServerInterface.ServerInterfaceEventListener>();
		
		incomingMessagesRouter = new IncomingMessagesRouter();
		
		createMessageReceivers();
	}
	
	private void createMessageReceivers()
	{
		incomingMessagesRouter.setReceiver(MessageTypes.REQUEST_PLAYER_STATE, new DataMessageReceiver()
		{
			@Override
			protected void parseContent(DataInput input) throws IOException
			{
				for (final ServerInterfaceEventListener listener : serverListeners)
				{
					final Multimedia multimedia;
					final int playbackPosition;
					final boolean playing;
					
					multimedia = MultimediaReader.read(input);
					playbackPosition = input.readInt();
					playing = input.readBoolean();
					
					messageQueue.post(new Runnable()
					{
						@Override
						public void run()
						{
							listener.onPlayerUpdateReceived(multimedia, playbackPosition, playing);
						}
					});
				}
			}
		});
		
		incomingMessagesRouter.setReceiver(MessageTypes.REQUEST_PLAYLIST, new DataMessageReceiver()
		{
			@Override
			protected void parseContent(DataInput input) throws IOException
			{
				// TODO Auto-generated method stub
				
			}
		});
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestPlaylistUpdate()
	{
		// TODO Auto-generated method stub
		
	}
}
