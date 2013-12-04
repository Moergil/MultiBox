package sk.hackcraft.multibox.android.client;

import java.io.IOException;
import java.net.Socket;

import sk.hackcraft.multibox.android.client.util.AndroidLog;
import sk.hackcraft.multibox.android.client.util.HandlerEventLoop;
import sk.hackcraft.multibox.model.Server;
import sk.hackcraft.multibox.net.AutoManagingAsynchronousSocketInterface;
import sk.hackcraft.multibox.net.MockServerInterface;
import sk.hackcraft.multibox.net.NetworkServerInterface;
import sk.hackcraft.multibox.net.NetworkStandards;
import sk.hackcraft.multibox.net.ServerInterface;
import sk.hackcraft.netinterface.connection.AsynchronousMessageInterface;
import sk.hackcraft.netinterface.connection.MessageInterface;
import sk.hackcraft.netinterface.connection.MessageInterfaceFactory;
import sk.hackcraft.netinterface.connection.SocketMessageInterface;
import sk.hackcraft.util.Log;
import android.app.Application;
import android.content.Intent;

public class MultiBoxApplication extends Application
{	
	private HandlerEventLoop eventLoop;
	private ServerInterface serverInterface;
	
	private Server server;
	
	private Log log;

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		eventLoop = new HandlerEventLoop();
		
		log = new AndroidLog();
	}
	
	public void createServerConnection(final String address)
	{
		/*MessageInterfaceFactory factory = new MessageInterfaceFactory()
		{
			@Override
			public MessageInterface create() throws IOException
			{
				Socket socket = new Socket(address, NetworkStandards.SOCKET_PORT);
				return new SocketMessageInterface(socket);
			}
		};
		
		AsynchronousMessageInterface messageInterface = new AutoManagingAsynchronousSocketInterface(factory, eventLoop, log);
		serverInterface = new NetworkServerInterface(messageInterface, eventLoop);
		serverInterface.registerEventListener(new ServerInterface.ServerInterfaceEventAdapter()
		{
			@Override
			public void onDisconnect()
			{
				serverInterface = null;
				startConnectActivityAfterDisconnect();
			}
		});*/
		
		serverInterface = new MockServerInterface(eventLoop);
		
		server = new Server(serverInterface, eventLoop);
	}
	
	public void destroyServerConnection()
	{
		serverInterface.close();
		serverInterface = null;
	}
	
	public boolean hasActiveConnection()
	{
		return serverInterface != null;
	}
	
	public Server getServer()
	{
		return server;
	}
	
	private void startConnectActivityAfterDisconnect()
	{
		Intent intent = new Intent(this, ServerSelectActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		intent.putExtra(ServerSelectActivity.EXTRA_KEY_DISCONNECT, true);
		
		startActivity(intent);
	}
}
