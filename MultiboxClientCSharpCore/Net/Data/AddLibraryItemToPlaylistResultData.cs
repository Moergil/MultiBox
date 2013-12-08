using MultiboxClientCSharpCore.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MultiboxClientCSharpCore.Net.Data
{
	public class AddLibraryItemToPlaylistResultData
	{
		public AddLibraryItemToPlaylistResultData(bool success, ILibraryItem libraryItem)
		{
			this.Success = success;
			this.LibraryItem = libraryItem;
		}

		public bool Success
		{
			get;
			private set;
		}

		public ILibraryItem LibraryItem
		{
			get;
			private set;
		}
	}
}
