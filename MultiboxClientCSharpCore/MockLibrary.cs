using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Model.LibraryEventArgs;

namespace MultiboxClientCSharpCore
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
			LibraryItemReceivedEventArgs arguments = new LibraryItemReceivedEventArgs(item);
			LibraryItemReceived(this, arguments);
		}

		public event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived;
	}
}
