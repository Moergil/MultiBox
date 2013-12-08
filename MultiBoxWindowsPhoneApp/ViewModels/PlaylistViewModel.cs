using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using System.Collections.ObjectModel;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Model.PlaylistEventArgs;

namespace MultiBoxWindowsPhoneApp.ViewModels
{
	public class PlaylistViewModel : INotifyPropertyChanged
	{
		private IPlaylist playlist;

		public PlaylistViewModel(IPlaylist playlist)
        {
			playlist.PlaylistChanged += OnPlaylistChanged;
            this.Items = new ObservableCollection<MultimediaViewModel>();
        }

		public event PropertyChangedEventHandler PropertyChanged = delegate { };

        public ObservableCollection<MultimediaViewModel> Items
		{
			get;
			private set;
		}

		public void OnPlaylistChanged(object sender, PlaylistChangedEventArgs eventArgs)
		{
			IList<MultimediaItem> playlist = eventArgs.Playlist;

			Items.Clear();

			foreach (MultimediaItem multimedia in playlist)
			{
				MultimediaViewModel viewModel = new MultimediaViewModel(multimedia);
				Items.Add(viewModel);
			}

			if (Items.Count > 0)
			{
				Items.RemoveAt(0);
			}

			NotifyPropertyChanged("Items");
		}

		private void NotifyPropertyChanged(String propertyName)
		{
			PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
		}
	}
}
