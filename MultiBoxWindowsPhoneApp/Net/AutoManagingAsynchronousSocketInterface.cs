using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.IO;
using CSharpNetInterface;
using CSharpNetInterface.Connection;
using CSharpNetInterface.Connection.AsynchronousMessageInterfaceEventArgs;
using CSharpNetInterface.Message;
using CSharpNetInterface.Util;
using MultiboxClientCSharpCore.Net.Transformer;

namespace MultiBoxWindowsPhoneApp.Net
{
	public class AutoManagingAsynchronousSocketInterface : IAsynchronousMessageInterface
	{
		private const long SocketReceiveTimenout = 15000;

		private readonly string address;
		private readonly int port;

		private IMessageQueue messageQueue;
		private ILog log;

		Socket socket;

		const int TIMEOUT_MILLISECONDS = 5000;
		const int MAX_BUFFER_SIZE = 2048;

		public IncomingMessagesRouter incomingMessagesRouter;

		private MemoryStream actualInputBuffer;
		private readonly object inputBufferSwapLock = new object();
		private bool waitingForReceive;

		private bool shouldClose;

		private bool active;
		private bool connecting;

		private Queue<MessageEntry> pendingMessagesQueue;

		public AutoManagingAsynchronousSocketInterface(string address, int port, IMessageQueue messageQueue, ILog log)
		{
			this.address = address;
			this.port = port;

			this.messageQueue = messageQueue;
			this.log = log;

			this.shouldClose = false;

			this.actualInputBuffer = new MemoryStream();

			this.connecting = false;
			this.active = false;

			this.incomingMessagesRouter = new IncomingMessagesRouter();
			this.pendingMessagesQueue = new Queue<MessageEntry>();
		}

		public void StartNetworking()
		{
			if (active || connecting)
			{
				return;
			}

			this.connecting = true;

			DnsEndPoint hostEntry = new DnsEndPoint(address, port);

			socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

			SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();
			socketEventArg.RemoteEndPoint = hostEntry;
			socketEventArg.Completed += OnConnectCompleted;

			socket.ConnectAsync(socketEventArg);
		}

		private void CleanNetworking()
		{
			if (!active)
			{
				return;
			}

			if (socket != null)
			{
				socket.Close();
				socket = null;
			}

			this.active = false;
		}

		private void ReportError()
		{
			SeriousErrorEventArgs seriousErrorEventArgs = new SeriousErrorEventArgs("Error during network operation.");
			messageQueue.Post(() => SeriousError(this, seriousErrorEventArgs));
		}

		private void OnConnectCompleted(object sender, SocketAsyncEventArgs eventArgs)
		{
			this.connecting = false;

			if (eventArgs.SocketError != SocketError.Success)
			{
				ReportError();
				CleanNetworking();
				return;
			}

			this.active = true;

			RunSender();
			Receive();
		}

		public void SendMessage(IMessage message)
		{
			SendMessage(message, delegate { });
		}

		public void SendMessage(IMessage message, Action<bool> listener)
		{
			pendingMessagesQueue.Enqueue(new MessageEntry(message, listener));

			if (!active)
			{
				if (!connecting)
				{
					StartNetworking();
				}
			}
			else
			{
				RunSender();
			}
		}

		private void RunSender()
		{
			if (!active)
			{
				return;
			}

			if (shouldClose)
			{
				CleanNetworking();
				return;
			}

			if (pendingMessagesQueue.Count == 0)
			{
				return;
			}

			MessageEntry entry = pendingMessagesQueue.Dequeue();

			IMessage message = entry.Message;
			Action<bool> listener = entry.Listener;

			MemoryStream output = new MemoryStream();
			NetworkBinaryWriter writer = new NetworkBinaryWriter(output);

			int messageTypeId = message.Type.TypeId;
			int contentSize = message.Content.Length;

			byte[] content = message.Content;

			writer.WriteInt(messageTypeId);
			writer.WriteInt(contentSize);
			writer.WriteBytes(content);

			byte[] messageTypeIdArray = BitConverter.GetBytes(messageTypeId);
			byte[] messageContentSizeArray = BitConverter.GetBytes(message.Content.Length);

			byte[] messageBytes = output.ToArray();

			output.Close();

			SocketAsyncEventArgs socketEventArgs = new SocketAsyncEventArgs();

			socketEventArgs.RemoteEndPoint = socket.RemoteEndPoint;
			socketEventArgs.UserToken = null;

			SendListener sendListener = new SendListener(listener);
			socketEventArgs.Completed += sendListener.OnSendCompleted;
			socketEventArgs.Completed += OnMessageSendCompleted;

			socketEventArgs.SetBuffer(messageBytes, 0, messageBytes.Length);

			socket.SendAsync(socketEventArgs);
		}

		private void OnMessageSendCompleted(object sender, SocketAsyncEventArgs eventArgs)
		{
			if (eventArgs.SocketError != SocketError.Success)
			{
				ReportError();
				CleanNetworking();
			}
			else
			{
				RunSender();
			}
		}

		public void SetMessageReceiver(IMessageType messageType, IMessageReceiver receiver)
		{
			incomingMessagesRouter.SetReceiver(messageType, receiver);
		}

		private void Receive()
		{
			SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();
			socketEventArg.RemoteEndPoint = socket.RemoteEndPoint;

			socketEventArg.SetBuffer(new Byte[MAX_BUFFER_SIZE], 0, MAX_BUFFER_SIZE);
			socketEventArg.Completed += OnDataReceived;

			try
			{
				socket.ReceiveAsync(socketEventArg);
			}
			catch (ObjectDisposedException e)
			{
				log.print("Socket was disposed, interrupting receiving.");
			}
		}

		public void Close()
		{
			this.shouldClose = true;
		}

		private void OnDataReceived(object sender, SocketAsyncEventArgs eventArgs)
		{
			if (eventArgs.SocketError != SocketError.Success)
			{
				ReportError();
				CleanNetworking();
				return;
			}

			lock (inputBufferSwapLock)
			{
				byte[] buffer = eventArgs.Buffer;
				int readLength = eventArgs.BytesTransferred;

				log.print("Received " + readLength + " bytes.");

				actualInputBuffer.Write(buffer, 0, readLength);

				byte[] messageBytes = actualInputBuffer.ToArray();

				int headerSize = sizeof(int) * 2;
				if (messageBytes.Length >= headerSize)
				{
					MemoryStream messageStream = new MemoryStream(messageBytes);

					NetworkBinaryReader reader = new NetworkBinaryReader(messageStream);

					int messageTypeId = reader.ReadInt();
					int contentSize = reader.ReadInt();

					int messageSize = headerSize + contentSize;
					if (messageBytes.Length >= messageSize)
					{
						byte[] content = new byte[contentSize];
						reader.ReadBytes(content);

						RawMessageType messageType = new RawMessageType(messageTypeId);
						incomingMessagesRouter.ReceiveMessage(messageType, content);

						SwitchInputBuffers(messageStream);
					}
				}
			}

			if (!active)
			{
				return;
			}

			if (shouldClose)
			{
				CleanNetworking();
				return;
			}

			// wait for another data
			Receive();
		}

		private void SwitchInputBuffers(MemoryStream readedStream)
		{
			// check how many bytes are left in buffer which was created from actual buffer bytes
			MemoryStream newInputBuffer = new MemoryStream();
			int length = (int)actualInputBuffer.Length;
			int position = (int)readedStream.Position;
			int unreadedBytesCount = length - position;

			if (unreadedBytesCount > 0)
			{
				// read all left bytes
				byte[] transferBuffer = new byte[unreadedBytesCount];
				readedStream.Read(transferBuffer, 0, transferBuffer.Length);

				// write bytes to new buffer
				newInputBuffer.Write(transferBuffer, 0, unreadedBytesCount);
			}

			// replace actual buffer for new
			actualInputBuffer.Close();
			actualInputBuffer = newInputBuffer;
		}

		public event EventHandler<SeriousErrorEventArgs> SeriousError;

		private class SendListener
		{
			public readonly Action<bool> listener;

			public SendListener(Action<bool> listener)
			{
				this.listener = listener;
			}

			public void OnSendCompleted(object sender, SocketAsyncEventArgs eventArgs)
			{
				listener(eventArgs.SocketError == SocketError.Success);
			}
		}

		private class RawMessageType : IMessageType
		{
			private readonly int typeId;

			public RawMessageType(int typeId)
			{
				this.typeId = typeId;
			}

			public int TypeId
			{
				get { return typeId; }
			}
		}

		private class MessageEntry
		{
			private readonly IMessage message;
			private readonly Action<bool> listener;

			public MessageEntry(IMessage message, Action<bool> listener)
			{
				this.message = message;
				this.listener = listener;
			}

			public IMessage Message
			{
				get { return message; }
			}

			public Action<bool> Listener
			{
				get { return listener; }
			}
		}
	}
}
