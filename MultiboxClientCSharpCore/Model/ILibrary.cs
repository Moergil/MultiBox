using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiboxClientCSharpCore.Model.LibraryEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	public class LibraryConstants
	{
		public const long RootDirectory = 0;
	}

	public interface ILibrary
	{
		void Init();
		void Close();

		void RequestItem(long id);

		event EventHandler<LibraryItemReceivedEventArgs> LibraryItemReceived;
	}

	namespace LibraryEventArgs
	{
		public class LibraryItemReceivedEventArgs : EventArgs
		{
			public LibraryItemReceivedEventArgs(ILibraryItem item)
			{
				this.LibraryItem = item;
			}

			public ILibraryItem LibraryItem
			{
				get;
				private set;
			}
		}
	}
}
