using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.LibraryEventArgs;

namespace MultiBoxCSharpCore
{
	public class LibraryConstants
	{
		public const long RootDirectory = 0;
		public const long BackNavigation = 0;
	}

	public interface ILibrary
	{
		void Init();
		void Close();

		void RequestItem(long id);

		event EventHandler<ItemReceivedEventArgs> ItemReceived;
	}

	namespace LibraryEventArgs
	{
		public class ItemReceivedEventArgs : EventArgs
		{
			public ItemReceivedEventArgs(ILibraryItem item)
			{
				this.Item = item;
			}

			public ILibraryItem Item
			{
				get;
				private set;
			}
		}
	}
}
