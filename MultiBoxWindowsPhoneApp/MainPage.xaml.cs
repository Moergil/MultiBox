using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using MultiboxClientCSharpCore.Model;
using MultiBoxWindowsPhoneApp.ViewModels;

namespace MultiBoxWindowsPhoneApp
{
    public partial class MainPage : PhoneApplicationPage
    {
		private const int PlayerPivotPosition = 0;
		private const int LibraryPivotPosition = 1;

		private MainPageViewModel viewModel;

		private readonly IPlayer player;
		private readonly IPlaylist playlist;
		private readonly ILibrary library;

		private readonly Stack<long> libraryHistory;

		private enum PivotPositions
		{
			Player = 0,
			Library = 1
		}

        public MainPage()
        {
            InitializeComponent();

			App app = App.Current as App;
			Server server = app.Server;

			this.player = server.Player;
			this.playlist = server.Playlist;
			this.library = server.Library;

			PlayerViewModel playerViewModel = new PlayerViewModel(player);
			PlaylistViewModel playlistViewModel = new PlaylistViewModel(playlist);
			LibraryViewModel libraryViewModel = new LibraryViewModel(library);

			this.libraryHistory = new Stack<long>();
			libraryHistory.Push(LibraryConstants.RootDirectory);

			this.viewModel = new MainPageViewModel(playerViewModel, playlistViewModel, libraryViewModel);
			DataContext = viewModel;
            this.Loaded += new RoutedEventHandler(OnLoaded);
			this.Unloaded += new RoutedEventHandler(OnUnloaded);
        }

		protected override void OnNavigatedTo(NavigationEventArgs e)
		{
			base.OnNavigatedTo(e);

			string serverName = NavigationContext.QueryString["serverName"];

			viewModel.ServerName = serverName;
		}

        private void OnLoaded(object sender, RoutedEventArgs e)
        {
			player.Init();
			playlist.Init();
			library.Init();
        }

		private void OnUnloaded(object sender, RoutedEventArgs e)
		{
			player.Close();
			playlist.Close();
			library.Close();
		}

		private void UpdatePlayerState(MultimediaItem multimedia, int playbackPosition, bool playing)
		{

		}

		private void LibrarySelectionChanged(object sender, SelectionChangedEventArgs eventArgs)
		{
			if (LibraryListBox.SelectedIndex == -1)
			{
				return;
			}

			LibraryItemViewModel libraryItemViewModel = LibraryListBox.SelectedItem as LibraryItemViewModel;

			ILibraryItem libraryItem = libraryItemViewModel.LibraryItem;

			long itemId = libraryItem.Id;

			if (libraryItem.Type == LibraryItemType.Directory)
			{
				libraryHistory.Push(itemId);
				library.RequestItem(itemId);
			}
			else if (libraryItem.Type == LibraryItemType.Multimedia)
			{
				playlist.AddItem(itemId);
			}
		}

		protected override void OnBackKeyPress(System.ComponentModel.CancelEventArgs e)
		{
			if (NavigateBack())
			{
				e.Cancel = true;
			}
			else
			{
				base.OnBackKeyPress(e);
			}
		}

		private bool NavigateBack()
		{
			if (MainPagePivot.SelectedIndex == LibraryPivotPosition && libraryHistory.Count > 1)
			{
				libraryHistory.Pop();
				long itemId = libraryHistory.Peek();
				library.RequestItem(itemId);
				return true;
			}
			else
			{
				return false;
			}
		}
    }
}