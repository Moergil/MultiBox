using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Message
{
	public interface IMessage
	{
		IMessageType Type { get; }
		byte[] Content { get; }
	}
}
