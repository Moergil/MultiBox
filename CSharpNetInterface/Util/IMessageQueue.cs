using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Util
{
	public interface IMessageQueue
	{
		void Post(Action runnable);
		void PostDelayed(Action runnable, long delay);
	}
}
