package sk.hackcraft.multibox.net.clientmessages;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.netinterface.MessageReceiver;

public abstract class PlayerStateReceiver implements MessageReceiver
{
	private Multimedia multimedia;
	private int playbackPosition;
	private boolean playing;
	
	@Override
	public void receive(byte[] content)
	{
		// TODO Auto-generated method stub
		
	}
	
	public abstract void onPlayerStateReceived(Multimedia multimedia, int playbackPosition, boolean playing);
}
