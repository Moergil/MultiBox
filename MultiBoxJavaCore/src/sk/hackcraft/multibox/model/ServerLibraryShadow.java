package sk.hackcraft.multibox.model;

import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.multibox.net.ServerInterface.ServerInterfaceEventAdapter;
import sk.hackcraft.util.MessageQueue;

public class ServerLibraryShadow implements Library
{	
	private final ServerInterface serverInterface;
	private final MessageQueue messageQueue;
	
	private ServerListener serverListener;
	
	private final List<LibraryEventListener> libraryListeners;

	public ServerLibraryShadow(ServerInterface serverInterface, MessageQueue messageQueue)
	{
		this.serverInterface = serverInterface;
		this.messageQueue = messageQueue;
		
		this.serverListener = new ServerListener();
		
		libraryListeners = new LinkedList<Library.LibraryEventListener>();
	}
	
	@Override
	public void init()
	{
		serverInterface.registerEventListener(serverListener);
	}

	@Override
	public void close()
	{
		serverInterface.unregisterEventListener(serverListener);
	}

	@Override
	public void requestItem(long id)
	{
		serverInterface.requestLibraryItem(id);
	}

	@Override
	public void registerLibraryEventListener(LibraryEventListener listener)
	{
		libraryListeners.add(listener);
	}

	@Override
	public void unregisterLibraryEventListener(LibraryEventListener listener)
	{
		libraryListeners.remove(listener);
	}
	
	private void notifyLibraryItemReceived(final LibraryItem item)
	{
		for (final Library.LibraryEventListener listener : libraryListeners)
		{
			messageQueue.post(new Runnable()
			{
				@Override
				public void run()
				{
					listener.onItemReceived(item);
				}
			});
		}
	}
	
	private class ServerListener extends ServerInterfaceEventAdapter
	{
		@Override
		public void onLibraryItemReceived(LibraryItem item)
		{
			notifyLibraryItemReceived(item);
		}
	}
}
