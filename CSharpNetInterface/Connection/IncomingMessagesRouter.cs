using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Message;

namespace CSharpNetInterface.Connection
{
	public class IncomingMessagesRouter
	{
		private readonly IDictionary<int, IMessageReceiver> receivers;

		private readonly object receiversLock = new object();
	
		public IncomingMessagesRouter()
		{
			receivers = new Dictionary<int, IMessageReceiver>();
		}
	
		public void SetReceiver(IMessageType type, IMessageReceiver receiver)
		{
			lock(receiversLock)
			{
				receivers[type.TypeId] = receiver;
			}
		}
	
		public void ReceiveMessage(IMessageType type, byte[] content)
		{
			IMessageReceiver receiver;
		
			lock (receiversLock)
			{
				receiver = receivers[type.TypeId];
			}
		
			receiver.Receive(content);
		}
	}
}
