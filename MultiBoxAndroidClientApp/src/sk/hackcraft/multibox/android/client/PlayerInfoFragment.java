package sk.hackcraft.multibox.android.client;

import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.Player;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlayerInfoFragment extends Fragment
{
	private Player player;
	private PlayerListener playerListener;
	
	private Multimedia activeMultimedia;
	
	private TextView nameView;
	private TextView timeView;
	private ProgressBar progressView;
	
	private CountDownTimer playbackPositionUpdater;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		PlayerProvider playerProvider = (PlayerProvider)getActivity();
		player = playerProvider.providePlayer();
		
		playerListener = new PlayerListener();
		player.registerPlayerEventListener(playerListener);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_player_info, container, false);
		
		nameView = (TextView)layout.findViewById(R.id.player_multimedia_name);
		timeView = (TextView)layout.findViewById(R.id.player_multimedia_time);
		progressView = (ProgressBar)layout.findViewById(R.id.player_multimedia_progress);
		
		return layout;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		player.init();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		player.close();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		player.unregisterPlayerEventListener(playerListener);
	}
	
	private void startPlaybackPositionUpdater(int secondsToEnd)
	{
		long millisToEnd = secondsToEnd * 1000;
		
		playbackPositionUpdater = new CountDownTimer(millisToEnd, 100)
		{
			
			@Override
			public void onTick(long millisUntilFinished)
			{
				int secondsUntilFinished = (int)TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
				
				int actualSeconds = activeMultimedia.getLength() - secondsUntilFinished;
				
				updatePlaybackPosition(actualSeconds);
			}
			
			@Override
			public void onFinish()
			{
				updatePlaybackPosition(activeMultimedia.getLength());
			}
		};
		
		playbackPositionUpdater.start();
	}
	
	private void stopPlaybackPositionUpdater()
	{
		playbackPositionUpdater.cancel();
		playbackPositionUpdater = null;
	}
	
	public void showMultimedia(Multimedia multimedia)
	{
		this.activeMultimedia = multimedia;
		
		String name = multimedia.getName();
		nameView.setText(name);
		
		int length = multimedia.getLength();
		progressView.setMax(length);
		updatePlaybackPosition(0);
	}
	
	public void showNothing()
	{
		nameView.setText("Nothing to play...");
		timeView.setText("");
		progressView.setProgress(0);
	}

	public void updatePlaybackPosition(int newPlayPosition)
	{
		int length = activeMultimedia.getLength();
		
		int estimatedTime = length - newPlayPosition;
		int seconds = (int)(estimatedTime % 60);
		int minutes = (int)(estimatedTime / 60);
		timeView.setText(String.format("%d:%d", minutes, seconds));

		progressView.setProgress(newPlayPosition);
	}
	
	private class PlayerListener implements Player.PlayerEventListener
	{
		@Override
		public void onPlayingStateChanged(boolean playing)
		{
			if (!playing && playbackPositionUpdater != null)
			{
				stopPlaybackPositionUpdater();
			}
		}

		@Override
		public void onPlaybackPositionChanged(int newPosition)
		{
			updatePlaybackPosition(newPosition);
			
			if (player.isPlaying())
			{
				if (playbackPositionUpdater != null)
				{
					stopPlaybackPositionUpdater();
				}
				
				int secondsToEnd = activeMultimedia.getLength() - newPosition;
				startPlaybackPositionUpdater(secondsToEnd);
			}
		}

		@Override
		public void onMultimediaChanged(Multimedia newMultimedia)
		{
			if (playbackPositionUpdater != null)
			{
				stopPlaybackPositionUpdater();
			}
			
			if (newMultimedia == null)
			{
				showNothing();
			}
			else
			{
				showMultimedia(newMultimedia);
			}
		}
	}
	
	public interface PlayerProvider
	{
		public Player providePlayer();
	}
}
