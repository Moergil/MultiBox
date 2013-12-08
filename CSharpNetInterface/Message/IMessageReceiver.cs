using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Message
{
	public interface IMessageReceiver
	{
		void Receive(byte[] content);
	}
}
