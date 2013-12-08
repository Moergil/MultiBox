using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using System.IO.IsolatedStorage;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using MultiboxClientCSharpCore.Model;

namespace MultiBoxWindowsPhoneApp
{
    public partial class ServerSelect : PhoneApplicationPage
    {
        public ServerSelect()
        {
            InitializeComponent();

			string serverAddress = LoadServerAddress();

			if (serverAddress != null)
			{
				ServerAddressTextBox.Text = serverAddress;
			}
        }

		private void ConnectButtonClicked(object sender, RoutedEventArgs e)
		{
			string serverAddress = ServerAddressTextBox.Text;
			ConnectToServer(serverAddress);
		}

		protected override void OnNavigatedTo(NavigationEventArgs e)
		{
			base.OnNavigatedTo(e);

			IDictionary<string, string> pairs = NavigationContext.QueryString;

			if (pairs.ContainsKey("disconnect"))
			{
				MessageBox.Show(AppResources.DisconnectMessage, "", MessageBoxButton.OK);
			}
		}

		private void ConnectToServer(string serverAddress)
		{
			App app = App.Current as App;
			app.CreateServerConnection(serverAddress);

			Server server = app.Server;
			server.RequestInfo(OnServerInfoReceived);

			SaveServerAddress(serverAddress);
		}

		private void SaveServerAddress(string address)
		{
			IsolatedStorageSettings settings = IsolatedStorageSettings.ApplicationSettings;
			if (!settings.Contains("lastServerAddress"))
			{
				settings.Add("lastServerAddress", address);
			}
			else
			{
				settings["lastServerAddress"] = address;
			}

			settings.Save();
		}

		private string LoadServerAddress()
		{
			IsolatedStorageSettings settings = IsolatedStorageSettings.ApplicationSettings;
			if (!settings.Contains("lastServerAddress"))
			{
				return null;
			}
			else
			{
				return settings["lastServerAddress"] as string;
			}
		}

		private void OnServerInfoReceived(string serverName)
		{
			Uri uri = new Uri("/MainPage.xaml?serverName=" + serverName, UriKind.Relative);
			NavigationService.Navigate(uri);
		}
    }
}