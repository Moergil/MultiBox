package sk.hackcraft.multibox;

import java.util.List;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.HuhPlayer;
import sk.hackcraft.multibox.server.ServerInterface;
import sk.hackcraft.multibox.server.ServerInterfaceService;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlayingInfobarFragment extends Fragment
{
	private Multimedia multimedia;
	
	private TextView nameView;
	private TextView timeView;
	private ProgressBar progressView;
	
	private CountDownTimer playbackTimer;
	
	private boolean playing;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_playing_infobar, container, false);
		
		nameView = (TextView)layout.findViewById(R.id.player_multimedia_name);
		timeView = (TextView)layout.findViewById(R.id.player_multimedia_time);
		progressView = (ProgressBar)layout.findViewById(R.id.player_multimedia_progress);
		
		return layout;
	}

	@Override
	public void onPause()
	{
		super.onPause();
		
		if (isPlaying())
		{
			stopTimer();
		}
	}
	
	public void setPlaying(boolean playing)
	{
		this.playing = playing;
	}
	
	public boolean isPlaying()
	{
		return playing;
	}
	
	private void startTimer(int actualPlayPosition)
	{
		final int length = multimedia.getLength();
		final long millisLength = TimeUnit.SECONDS.toMillis(length);
		
		long millisActualPlayPosition = TimeUnit.SECONDS.toMillis(actualPlayPosition);
		long playDuration = millisLength - millisActualPlayPosition;
		
		playbackTimer = new CountDownTimer(playDuration, 100)
		{
			@Override
			public void onTick(long millisUntilFinished)
			{
				int secondsUntilFinished = (int)TimeUnit.MILLISECONDS.toSeconds(millisLength - millisUntilFinished);
				updatePlayingPosition(secondsUntilFinished);
			}
			
			@Override
			public void onFinish()
			{
				updatePlayingPosition(length);
				multimedia = null;
			}
		};
		
		playbackTimer.start();
	}
	
	private void stopTimer()
	{
		playbackTimer.cancel();
	}
	
	public void showMultimedia(Multimedia multimedia, int playPosition)
	{
		if (isPlaying())
		{
			stopTimer();
		}
		
		this.multimedia = multimedia;
		
		String name = multimedia.getName();
		nameView.setText(name);
		
		int length = multimedia.getLength();
		progressView.setMax(length);

		if (isPlaying())
		{
			startTimer(playPosition);
		}
	}
	
	public void showNothing()
	{
		if (isPlaying())
		{
			stopTimer();
		}
		
		nameView.setText("Nothing to play...");
		timeView.setText("");
		progressView.setProgress(0);
	}

	public void updatePlayingPosition(int newPlayPosition)
	{
		int length = multimedia.getLength();
		
		int estimatedTime = length - newPlayPosition;
		int seconds = (int)(estimatedTime % 60);
		int minutes = (int)(estimatedTime / 60);
		timeView.setText(String.format("%d:%d", minutes, seconds));

		progressView.setProgress(newPlayPosition);
	}
}
