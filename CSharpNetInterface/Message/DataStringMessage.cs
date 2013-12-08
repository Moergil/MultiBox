using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using CSharpNetInterface.Util;

namespace CSharpNetInterface.Message
{
	public class DataStringMessage : IMessage
	{
		private readonly IMessageType messageType;
		private readonly byte[] content;
	
		public DataStringMessage(IMessageType messageType, string data)
		{
			this.messageType = messageType;

			byte[] dataStringBytes = Encoding.UTF8.GetBytes(data);

			MemoryStream output = new MemoryStream();

			NetworkBinaryWriter writer = new NetworkBinaryWriter(output);
			writer.WriteUTF(data);

			content = output.ToArray();
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
