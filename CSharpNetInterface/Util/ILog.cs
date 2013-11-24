using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace CSharpNetInterface.Util
{
	interface ILog
	{
		void print(string message);
		void printf(string format, params object[] values);
	}
}
