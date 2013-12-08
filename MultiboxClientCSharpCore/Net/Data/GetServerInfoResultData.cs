using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MultiboxClientCSharpCore.Net.Data
{
	public class GetServerInfoResultData
	{
		private readonly string serverName;
	
		public GetServerInfoResultData(string serverName)
		{
			this.serverName = serverName;
		}
	
		public string ServerName
		{
			get { return serverName; }
		}
	}
}
