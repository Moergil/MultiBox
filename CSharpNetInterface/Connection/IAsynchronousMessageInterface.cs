using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Connection.AsynchronousMessageInterfaceEventArgs;
using CSharpNetInterface.Message;

namespace CSharpNetInterface.Connection
{
	public interface IAsynchronousMessageInterface
	{
		void SendMessage(IMessage message);
		void SendMessage(IMessage message, Action<bool> listener);

		void SetMessageReceiver(IMessageType messageType, IMessageReceiver receiver);

		void Close();

		event EventHandler<SeriousErrorEventArgs> SeriousError;
	}

	namespace AsynchronousMessageInterfaceEventArgs
	{
		public class MessageSendEventArgs : EventArgs
		{
			public MessageSendEventArgs(bool success)
			{
				this.Success = success;
			}

			public bool Success
			{
				get;
				private set;
			}
		}

		public class SeriousErrorEventArgs : EventArgs
		{
			public SeriousErrorEventArgs(string errorDescription)
			{
				this.ErrorDescription = errorDescription;
			}

			public string ErrorDescription
			{
				get;
				private set;
			}
		}
	}
}
