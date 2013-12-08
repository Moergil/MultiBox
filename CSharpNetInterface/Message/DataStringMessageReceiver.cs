using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

using CSharpNetInterface.Util;

namespace CSharpNetInterface.Message
{
	public class DataStringMessageReceiver<R> : IMessageReceiver
	{
		private readonly IMessageQueue messageQueue;
		private readonly IDataTransformer<string, R> decoder;
		private readonly Action<R> listener;

		public DataStringMessageReceiver(IMessageQueue messageQueue, IDataTransformer<string, R> decoder, Action<R> listener)
		{
			this.messageQueue = messageQueue;
			this.decoder = decoder;
			this.listener = listener;
		}

		public void Receive(byte[] content)
		{
			Stream input = new MemoryStream(content);
			NetworkBinaryReader reader = new NetworkBinaryReader(input);

			string dataString = reader.ReadUTF();

			R data = decoder.Transform(dataString);
			messageQueue.Post(() => listener(data));
		}
	}
}
