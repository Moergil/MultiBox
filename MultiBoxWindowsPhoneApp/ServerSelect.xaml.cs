using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;

namespace MultiBoxWindowsPhoneApp
{
    public partial class ServerSelect : PhoneApplicationPage
    {
        public ServerSelect()
        {
            InitializeComponent();
        }

		private void ConnectButtonClicked(object sender, RoutedEventArgs e)
		{
			string serverAddress = ServerAddressTextBox.Text;

			Uri uri = new Uri("/MainPage.xaml", UriKind.Relative);
			NavigationService.Navigate(uri);
		}
    }
}