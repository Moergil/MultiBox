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
using System.Windows.Navigation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using CSharpNetInterface.Util;
using MultiBoxWindowsPhoneApp.Util;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Net;
using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;
using MultiBoxWindowsPhoneApp.Net;

namespace MultiBoxWindowsPhoneApp
{
    public partial class App : Application
    {
        public PhoneApplicationFrame RootFrame { get; private set; }

        public App()
        {
            UnhandledException += Application_UnhandledException;

            InitializeComponent();
            InitializePhoneApplication();

			InitializeMultibox();

            if (System.Diagnostics.Debugger.IsAttached)
            {
                Application.Current.Host.Settings.EnableFrameRateCounter = true;

                PhoneApplicationService.Current.UserIdleDetectionMode = IdleDetectionMode.Disabled;
            }
        }

        private void Application_Launching(object sender, LaunchingEventArgs e)
        {
        }

        private void Application_Activated(object sender, ActivatedEventArgs e)
        {
        }

        private void Application_Deactivated(object sender, DeactivatedEventArgs e)
        {
        }

        private void Application_Closing(object sender, ClosingEventArgs e)
        {
        }

        private void RootFrame_NavigationFailed(object sender, NavigationFailedEventArgs e)
        {
            if (System.Diagnostics.Debugger.IsAttached)
            {
                System.Diagnostics.Debugger.Break();
            }
        }

        private void Application_UnhandledException(object sender, ApplicationUnhandledExceptionEventArgs e)
        {
            if (System.Diagnostics.Debugger.IsAttached)
            {
                System.Diagnostics.Debugger.Break();
            }
        }

		#region MultiBox global logic

		private IMessageQueue messageQueue;
		private ILog log;

		private IServerInterface serverInterface;
		private Server server;

		private void InitializeMultibox()
		{
			messageQueue = new DispatcherMessageQueue();
			log = new SystemLog();
		}

		public void CreateServerConnection(string serverAddress)
		{
			AutoManagingAsynchronousSocketInterface messageInterface = new AutoManagingAsynchronousSocketInterface(serverAddress, NetworkStandards.SOCKET_PORT, messageQueue, log);
			serverInterface = new NetworkServerInterface(messageInterface, messageQueue);
			//serverInterface = new MockServerInterface(messageQueue);
			serverInterface.Disconnect += OnDisconnect;

			server = new Server(serverInterface, messageQueue, log);
		}

		private void OnDisconnect(object sender, DisconnectEventArgs eventArgs)
		{
			serverInterface = null;
			NavigateToServerSelectPageAfterDisconnect();
		}

		private void NavigateToServerSelectPageAfterDisconnect()
		{
			Uri uri = new Uri("/ServerSelect.xaml?disconnect=true", UriKind.Relative);
			(Application.Current.RootVisual as PhoneApplicationFrame).Navigate(uri);
		}

		public void DestroyServerConnection()
		{
			serverInterface.Close();
			serverInterface = null;

			server = null;
		}

		public bool HasActiveConnection()
		{
			return serverInterface != null;
		}

		public ILog Log
		{
			get { return log; }
		}

		public Server Server
		{
			get { return server; }
		}

		#endregion

		#region Phone application initialization

		// Avoid double-initialization
        private bool phoneApplicationInitialized = false;

        // Do not add any additional code to this method
        private void InitializePhoneApplication()
        {
            if (phoneApplicationInitialized)
                return;

            // Create the frame but don't set it as RootVisual yet; this allows the splash
            // screen to remain active until the application is ready to render.
            RootFrame = new PhoneApplicationFrame();
            RootFrame.Navigated += CompleteInitializePhoneApplication;

            // Handle navigation failures
            RootFrame.NavigationFailed += RootFrame_NavigationFailed;

            // Ensure we don't initialize again
            phoneApplicationInitialized = true;
        }

        // Do not add any additional code to this method
        private void CompleteInitializePhoneApplication(object sender, NavigationEventArgs e)
        {
            // Set the root visual to allow the application to render
            if (RootVisual != RootFrame)
                RootVisual = RootFrame;

            // Remove this handler since it is no longer needed
            RootFrame.Navigated -= CompleteInitializePhoneApplication;
        }

        #endregion
    }
}