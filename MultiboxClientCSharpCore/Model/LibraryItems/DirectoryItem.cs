using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiboxClientCSharpCore.Model.LibraryItems
{
	public class DirectoryItem : GenericLibraryItem
	{
		private IList<ILibraryItem> content;

		public DirectoryItem(long id, string name)
			: base(id, LibraryItemType.Directory, name)
		{
			this.content = new List<ILibraryItem>();
		}

		public void AddItem(ILibraryItem item)
		{
			content.Add(item);
		}

		public IList<ILibraryItem> Items
		{
			get { return new List<ILibraryItem>(content); }
		}
	}
}
