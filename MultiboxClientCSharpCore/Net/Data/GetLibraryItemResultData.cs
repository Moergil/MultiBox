using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MultiboxClientCSharpCore.Model;

namespace MultiboxClientCSharpCore.Net.Data
{
	public class GetLibraryItemResultData
	{
		public GetLibraryItemResultData(ILibraryItem libraryItem)
		{
			this.LibraryItem = libraryItem;
		}

		public ILibraryItem LibraryItem
		{
			get;
			private set;
		}
	}
}
