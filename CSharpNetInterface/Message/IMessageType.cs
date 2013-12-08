using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Message
{
	public interface IMessageType
	{
		int TypeId { get; }
	}

	public interface MessageTypeParser<T>
	{
		T Parse(int typeId);
	}
}
