package sk.hackcraft.multibox.net;

import java.util.HashMap;
import java.util.Map;

import sk.hackcraft.netinterface.MessageType;

public enum MessageTypes implements MessageType
{
	GET_PLAYER_STATE(1),
	GET_PLAYLIST(2);

	private static Map<Integer, MessageTypes> convertMap;
	
	static
	{
		convertMap = new HashMap<Integer, MessageTypes>();

		for (MessageTypes type : values())
		{
			convertMap.put(type.toTypeId(), type);
		}
	}
	
	private final int id;
	
	private MessageTypes(int id)
	{
		this.id = id;
	}
	
	@Override
	public int toTypeId()
	{
		return id;
	}
	
	public class MessageParser implements Parser<MessageTypes>
	{
		@Override
		public MessageTypes parse(int value)
		{
			return convertMap.get(value);
		}
	}
}
