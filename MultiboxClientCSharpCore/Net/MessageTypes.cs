using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Message;

namespace MultiboxClientCSharpCore.Net
{
	public class MessageTypes : IMessageType
	{
		public static MessageTypes GetPlayerState = new MessageTypes(1);
		public static MessageTypes GetPlaylist = new MessageTypes(2);
		public static MessageTypes GetLibraryItem = new MessageTypes(3);
		public static MessageTypes AddLibraryItemToPlaylist = new MessageTypes(4);
		public static MessageTypes GetServerInfo = new MessageTypes(5);

		private readonly int value;

		private MessageTypes(int value)
		{
			this.value = value;
		}

		public int TypeId
		{
			get { return value; }
		}
	}
}
