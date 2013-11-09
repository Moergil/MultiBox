package sk.hackcraft.netinterface;


public interface AsynchronousSocketInterface
{
	public void setSeriousErrorListener(SeriousErrorListener listener);
	
	public void sendMessage(Message message);
	public void sendMessage(Message message, MessageSendListener listener);
	
	public void setMessageReceiver(MessageType messageType, MessageReceiver receiver);
	
	public interface MessageSendListener
	{
		public void onFinish(boolean success);
	}
	
	public interface SeriousErrorListener
	{
		public void onSeriousError();
	}
}
