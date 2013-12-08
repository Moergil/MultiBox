using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using MultiboxClientCSharpCore.Model;
using MultiboxClientCSharpCore.Model.PlayerEventArgs;
using CSharpNetInterface.Util;
using CSharpNetInterface.Util.CountDownTimerEventArgs;
using MultiBoxWindowsPhoneApp.Util;

namespace MultiBoxWindowsPhoneApp.ViewModels
{
	public class PlayerViewModel : INotifyPropertyChanged
	{
		private readonly IPlayer player;

		private CountDownTimer playbackUpdater;

		private long millisLength, millisPlaybackPosition;

		public PlayerViewModel(IPlayer player)
		{
			this.player = player;
			player.PlayerStateChanged += OnPlayerStateUpdate;

			this.millisLength = 0;
			this.millisPlaybackPosition = 0;
		}

		public string MultimediaName
		{
			get
			{
				MultimediaItem multimedia = player.ActiveMultimedia;
				if (multimedia == null)
				{
					return AppResources.NothingToPlayNotify;
				}
				else
				{
					return multimedia.Name;
				}
			}
		}

		public string PlaybackTime
		{
			get
			{
				long estimatedTimeMillis = millisLength - millisPlaybackPosition;

				int estimatedTime = (int)(estimatedTimeMillis / 1000);
				int seconds = (int)(estimatedTime % 60);
				int minutes = (int)(estimatedTime / 60);

				return String.Format("{0}:{1}", minutes.ToString("D2"), seconds.ToString("D2"));
			}
		}

		public int PlaybackPosition
		{
			get
			{
				return (int)millisPlaybackPosition;
			}
		}

		public int MultimediaLength
		{
			get
			{
				return (int)millisLength;
			}
		}

		public event PropertyChangedEventHandler PropertyChanged = delegate { };

		private void OnPlayerStateUpdate(object sender, PlayerStateChangedEventArgs eventArgs)
		{
			NotifyPropertyChanged("MultimediaName");
			NotifyPropertyChanged("MultimediaLength");
			NotifyPropertyChanged("Playing");
			NotifyPropertyChanged("PlaybackPosition");
			NotifyPropertyChanged("PlaybackTime");

			MultimediaItem multimedia = eventArgs.Multimedia;
			if (eventArgs.Multimedia != null)
			{
				this.millisLength = (long)multimedia.Length * 1000;
				long millisEstimatedLength = millisLength - (eventArgs.PlaybackPosition * 1000);

				int millisStep = 1000 / 60;

				if (playbackUpdater != null)
				{
					playbackUpdater.Cancel();
				}

				playbackUpdater = new CountDownTimer(new DispatcherMessageQueue(), millisEstimatedLength, millisStep);
				playbackUpdater.Tick += OnUpdaterTick;
				playbackUpdater.Finish += OnUpdaterFinish;
				playbackUpdater.Start();
			}
		}

		private void OnUpdaterTick(object sender, TickEventArgs eventArgs)
		{
			UpdatePlaybackIndicators(eventArgs.MillisToEnd);
		}

		private void OnUpdaterFinish(object sender, FinishEventArgs eventArgs)
		{
			UpdatePlaybackIndicators(0);
		}

		private void UpdatePlaybackIndicators(long millisToEnd)
		{
			this.millisPlaybackPosition = millisLength - millisToEnd;

			NotifyPropertyChanged("PlaybackPosition");
			NotifyPropertyChanged("PlaybackTime");
		}

		private void NotifyPropertyChanged(string propertyName)
		{
			PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
		}
	}
}
