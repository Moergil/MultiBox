using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Util;

namespace MultiBoxWindowsPhoneApp.Util
{
	class SystemLog : ILog
	{
		public void print(string message)
		{
			System.Diagnostics.Debug.WriteLine(message);
		}

		public void printf(string format, params object[] values)
		{
			string message = String.Format(format, values);
			System.Diagnostics.Debug.WriteLine(message);
		}
	}
}
