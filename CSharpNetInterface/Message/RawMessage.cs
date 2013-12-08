using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Message
{
	public class RawMessage : IMessage
	{
		private IMessageType messageType;
		private byte[] content;

		public RawMessage(IMessageType messageType, byte[] content)
		{
			this.messageType = messageType;
			this.content = content;
		}

		public IMessageType Type
		{
			get { return messageType; }
		}

		public byte[] Content
		{
			get { return content; }
		}
	}
}
