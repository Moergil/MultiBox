using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CSharpNetInterface.Message;

namespace CSharpNetInterface.Connection
{
	public interface IMessageInterface : IDisposable
	{
		void SendMessage(IMessage message);
		IMessage WaitForMessage();
	}
}
