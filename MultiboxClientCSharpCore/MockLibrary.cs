using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiBoxCSharpCore
{
	class MockLibrary : ILibrary
	{
		public void Init()
		{
			throw new NotImplementedException();
		}

		public void Close()
		{
			throw new NotImplementedException();
		}

		public void RequestItem(long id)
		{
			OnItemReceived(new GenericLibraryItem(id, LibraryItemType.Directory, "Dir"));
		}

		private void OnItemReceived(ILibraryItem item)
		{
			LibraryEventArgs.ItemReceivedEventArgs arguments = new LibraryEventArgs.ItemReceivedEventArgs(item);
			ItemReceived(this, arguments);
		}

		public event EventHandler<LibraryEventArgs.ItemReceivedEventArgs> ItemReceived;
	}
}
