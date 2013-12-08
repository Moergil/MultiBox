using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CSharpNetInterface.Util;
using MultiboxClientCSharpCore.Net;
using MultiboxClientCSharpCore.Model.PlayerEventArgs;
using MultiboxClientCSharpCore.Net.ServerInterfaceEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	class ServerPlayerShadow : IPlayer
	{
		private readonly IServerInterface serverInterface;
		private readonly IMessageQueue messageQueue;

		private bool playing;
		private MultimediaItem activeMultimedia;
		private int playbackPosition;

		private long lastUpdateTimestamp;

		public event EventHandler<PlayerEventArgs.PlayerStateChangedEventArgs> PlayerStateChanged = delegate { };

		private readonly PeriodicWorkDispatcher stateChecker;
	
		public ServerPlayerShadow(IServerInterface serverInterface, IMessageQueue messageQueue)
		{
			this.serverInterface = serverInterface;
			this.messageQueue = messageQueue;
		
			this.playing = false;
			this.activeMultimedia = null;
			this.playbackPosition = 0;

			this.stateChecker = new PeriodicWorkDispatcher(messageQueue, 5000, UpdatePlayer);
		}

		public void Init()
		{
			serverInterface.PlayerUpdateReceived += OnPlayerUpdateReceived;

			UpdatePlayer();

			stateChecker.Start();
		}

		public void Close()
		{
			serverInterface.PlayerUpdateReceived -= OnPlayerUpdateReceived;

			stateChecker.Stop();
		}

		private void UpdatePlayer()
		{
			serverInterface.RequestPlayerUpdate();
		}

		public bool Playing
		{
			get { return playing; }
		}

		public int PlaybackPosition
		{
			get { return playbackPosition; }
		}

		public MultimediaItem ActiveMultimedia
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
			MultimediaItem multimedia = eventArgs.Multimedia;
			int playbackPosition = eventArgs.PlaybackPosition;
			bool playing = eventArgs.Playing;

			ProcessPlayerUpdate(multimedia, playbackPosition, playing);
		}

		private void ProcessPlayerUpdate(MultimediaItem multimedia, int playbackPosition, bool playing)
		{
			lastUpdateTimestamp = Environment.TickCount;

			this.activeMultimedia = multimedia;
			this.playing = playing;
			this.playbackPosition = playbackPosition;

			PlayerStateChangedEventArgs playerStateEventArgs = new PlayerStateChangedEventArgs(multimedia, playbackPosition, playing);
			messageQueue.Post(() => PlayerStateChanged(this, playerStateEventArgs));
		}
	}
}
