using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MultiboxClientCSharpCore.Net.Data
{
	public class AddLibraryItemToPlaylistData
	{
		public AddLibraryItemToPlaylistData(long itemId)
		{
			this.ItemId = itemId;
		}

		public long ItemId
		{
			get;
			private set;
		}
	}
}
