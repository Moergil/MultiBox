using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Threading;
using CSharpNetInterface.Util;

namespace MultiBoxWindowsPhoneApp.Util
{
	class DispatcherMessageQueue : IMessageQueue
	{
		private readonly Dispatcher dispatcher;

		public DispatcherMessageQueue()
		{
			dispatcher = Deployment.Current.Dispatcher;
		}

		public void Post(Action runnable)
		{
			dispatcher.BeginInvoke(runnable);
		}

		public void PostDelayed(Action runnable, long delay)
		{
			Post(() =>
			{
				DispatcherTimer timer = new DispatcherTimer();
				timer.Interval = TimeSpan.FromMilliseconds(delay);
				timer.Tick += (sender, args) =>
				{
					timer.Stop();
					runnable();
				};
				timer.Start();
			});
		}
	}
}
