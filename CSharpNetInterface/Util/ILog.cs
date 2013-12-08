using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace CSharpNetInterface.Util
{
	public interface ILog
	{
		void print(string message);
		void printf(string format, params object[] values);
	}
}
