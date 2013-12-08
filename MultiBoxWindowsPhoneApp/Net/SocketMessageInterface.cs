using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.IO;
using CSharpNetInterface;
using CSharpNetInterface.Message;
using CSharpNetInterface.Connection;

namespace MultiBoxWindowsPhoneApp.Net
{
	public class SocketMessageInterface : IMessageInterface
	{
		private readonly Socket socket;

		private byte[] messageTypeByteArray;

		public SocketMessageInterface(Socket socket)
		{
			this.socket = socket;
		}

		public void SendMessage(IMessage message)
		{
			int messageTypeId = message.Type.TypeId;
			byte[] messageTypeIdArray = BitConverter.GetBytes(messageTypeId);

			byte[] messageBytes = messageTypeIdArray.Concat(message.Content).ToArray();

			//socket.SendAsync();
		}

		public IMessage WaitForMessage()
		{
			
			//int messageTypeId = socket.Rece
			IMessage message = new RawMessage(null, null);
			throw new NotImplementedException();
		}

		public void Dispose()
		{
			socket.Close();
		}
	}
}
