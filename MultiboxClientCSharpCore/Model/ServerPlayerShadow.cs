using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.PlayerEventArgs;
using MultiBoxCSharpCore.ServerInterfaceEventArgs;

namespace MultiBoxCSharpCore
{
	class ServerPlayerShadow : IPlayer
	{
		private readonly IServerInterface serverInterface;
		private readonly IMessageQueue messageQueue;

		private bool playing;
		private Multimedia activeMultimedia;
		private int playbackPosition;

		public event EventHandler<PlayerEventArgs.PlayingStateChangedEventArgs> PlayingStateChanged;
		public event EventHandler<PlayerEventArgs.PlaybackPositionChangedEventArgs> PlaybackPositionChanged;
		public event EventHandler<PlayerEventArgs.MultimediaChangedEventArgs> MultimediaChanged;
	
		public ServerPlayerShadow(IServerInterface serverInterface, IMessageQueue messageQueue)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;
		
			this.playing = false;
			this.activeMultimedia = null;
			this.playbackPosition = 0;
		}

		public void Init()
		{
			serverInterface.PlayerUpdateReceived += OnPlayerUpdateReceived;
			serverInterface.RequestPlayerUpdate();
		}

		public void Close()
		{
			serverInterface.PlayerUpdateReceived -= OnPlayerUpdateReceived;
		}

		public bool Playing
		{
			get { return playing; }
		}

		public int PlaybackPosition
		{
			get { return playbackPosition; }
		}

		public Multimedia ActiveMultimedia
		{
			get { return activeMultimedia; }
		}

		public void RequestPlayingStateChange(bool playing)
		{
			throw new NotImplementedException();
		}

		public void RequestActiveMultimediaSkip()
		{
			throw new NotImplementedException();
		}

		private void OnPlayerUpdateReceived(object sender, PlayerUpdateReceivedEventArgs eventArgs)
		{
			Multimedia multimedia = eventArgs.Multimedia;
			int playbackPosition = eventArgs.PlaybackPosition;
			bool playing = eventArgs.Playing;

			ProcessPlayerUpdate(multimedia, playbackPosition, playing);
		}

		private void ProcessPlayerUpdate(Multimedia multimedia, int playbackPosition, bool playing)
		{
			if (multimedia == null)
			{
				if (activeMultimedia != null)
				{
					this.activeMultimedia = null;
					
					MultimediaChangedEventArgs eventArgs = new MultimediaChangedEventArgs(multimedia);
					messageQueue.Post(() => MultimediaChanged(this, eventArgs));
				}
			}
			else
			{
				bool newMultimedia;
				if (activeMultimedia == null || !activeMultimedia.Equals(multimedia))
				{
					newMultimedia = true;
					this.activeMultimedia = multimedia;
				}
				else
				{
					newMultimedia = false;
				}
	
				this.playbackPosition = playbackPosition;

				if (newMultimedia)
				{
					MultimediaChangedEventArgs eventArgs = new MultimediaChangedEventArgs(multimedia);
					messageQueue.Post(() => MultimediaChanged(this, eventArgs));
				}

				PlaybackPositionChangedEventArgs playbackEventArgs = new PlaybackPositionChangedEventArgs(playbackPosition);
				messageQueue.Post(() => PlaybackPositionChanged(this, playbackEventArgs));
			}
			
			bool stateChanged = this.playing != playing;
			
			this.playing = playing;
			
			if (stateChanged)
			{
				PlayingStateChangedEventArgs eventArgs = new PlayingStateChangedEventArgs(playing);
				messageQueue.Post(() => PlayingStateChanged(this, eventArgs));
			}
		}
	}
}
