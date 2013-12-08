using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Message;

namespace MultiBoxWindowsPhoneApp.Net
{
	public class MessageTypes : IMessageType
	{
		public static readonly MessageTypes GetPlayerState = new MessageTypes(1);
		public static readonly MessageTypes GetPlayliste = new MessageTypes(2);
		public static readonly MessageTypes GetLibraryItem = new MessageTypes(3);
		public static readonly MessageTypes AddLibraryItemToPlaylist = new MessageTypes(4);
		public static readonly MessageTypes GetServerInfo = new MessageTypes(5);

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
