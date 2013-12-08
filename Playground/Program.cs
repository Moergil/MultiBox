using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.IO.Pipes;


using MultiboxClientCSharpCore;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Net.Transformer.Json;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using MultiboxClientCSharpCore.Net.Transformer;

namespace Playground
{
	class Program
	{
		static void Main(string[] args)
		{
			new Program().Run();
		}

		public void Run()
		{

			//string json = "{\n    \"libraryItem\": {\n        \"id\": 0,\n        \"items\": [\n            {\n                \"id\": 1,\n                \"name\": \"Music\",\n                \"type\": \"DIRECTORY\"\n            },\n            {\n                \"id\": 1833,\n                \"name\": \"Clips\",\n                \"type\": \"DIRECTORY\"\n            }\n        ],\n        \"name\": \"Library\",\n        \"type\": \"DIRECTORY\"\n    }\n}\n";
			//string json = "{\"libraryItem\": {\"id\": 0,\"name\": \"Library\",\"type\": \"DIRECTORY\"}}";
			string json = "{\"test\": 3}";
			new GetLibraryItemDecoder().Transform(json);

			/*
			"{
				\"libraryItem\":
				{
					\"id\": 0,
					\"items\":
					[
						{
							\"id\": 1,
							\"name\": \"Music\",
							\"type\": \"DIRECTORY\"
						},
						{
							\"id\": 1833,
							\"name\": \"Clips\",
							\"type\": \"DIRECTORY\"
						}
					],
					\"name\": \"Library\",
					\"type\": \"DIRECTORY\"
				}
			}"
			 */

		}

		event EventHandler TestEvent = delegate { };
	}
}
