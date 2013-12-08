using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using MultiboxClientCSharpCore.Model;

namespace MultiBoxWindowsPhoneApp.ViewModels
{
	public class LibraryItemViewModel
	{
		private ILibraryItem libraryItem;

		public LibraryItemViewModel(ILibraryItem libraryItem)
		{
			this.libraryItem = libraryItem;
		}

		public string Name
		{
			get
			{
				string name = libraryItem.Name;

				if (libraryItem.Type == LibraryItemType.Directory)
				{
					name = "[Dir] " + name;
				}

				return name;
			}
		}

		public ILibraryItem LibraryItem
		{
			get { return libraryItem; }
		}
	}
}
