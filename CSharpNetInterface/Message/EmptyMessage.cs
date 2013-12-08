using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Message
{
	public class EmptyMessage : IMessage
	{
		private readonly IMessageType messageType;
	
		public EmptyMessage(IMessageType messageType)
		{
			this.messageType = messageType;
		}

		public IMessageType Type
		{
			get { return messageType; }
		}

		public byte[] Content
		{
			get { return new byte[0]; }
		}
	}
}
