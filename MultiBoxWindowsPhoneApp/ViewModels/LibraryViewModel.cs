using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using System.Collections.ObjectModel;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Model.LibraryEventArgs;
using MultiboxClientCSharpCore.Model.LibraryItems;

namespace MultiBoxWindowsPhoneApp.ViewModels
{
	public class LibraryViewModel : INotifyPropertyChanged
	{
		private readonly ILibrary library;

		public LibraryViewModel(ILibrary library)
		{
			this.library = library;

			library.LibraryItemReceived += OnLibraryItemReceived;

			this.Items = new ObservableCollection<LibraryItemViewModel>();
		}

		private void OnLibraryItemReceived(object sender, LibraryItemReceivedEventArgs eventArgs)
		{
			ILibraryItem libraryItem = eventArgs.LibraryItem;

			if (libraryItem.Type != LibraryItemType.Directory)
			{
				return;
			}

			DirectoryItem directory = libraryItem as DirectoryItem;

			Items.Clear();

			foreach (ILibraryItem item in directory.Items)
			{
				Items.Add(new LibraryItemViewModel(item));
			}

			NotifyPropertyChanged("Items");
		}

		public ObservableCollection<LibraryItemViewModel> Items
		{
			get;
			private set;
		}

		public event PropertyChangedEventHandler PropertyChanged = delegate { };

		private void NotifyPropertyChanged(string propertyName)
		{
			PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
		}
	}
}
