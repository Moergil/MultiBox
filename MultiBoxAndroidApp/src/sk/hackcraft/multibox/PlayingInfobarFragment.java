package sk.hackcraft.multibox;

import java.util.List;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.HuhPlayer;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.net.ServerInterface;
import android.app.Activity;
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
	private Player player;
	private PlayerListener playerListener;
	
	private Multimedia activeMultimedia;
	
	private TextView nameView;
	private TextView timeView;
	private ProgressBar progressView;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		MultiBoxApplication application = (MultiBoxApplication)activity.getApplication();
		
		playerListener = new PlayerListener();
		
		player = application.createPlayer();
		player.registerPlayerEventListener(playerListener);
	}
	
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
	public void onDetach()
	{
		super.onDetach();
		
		player.unregisterPlayerEventListener(playerListener);
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
		}

		@Override
		public void onPlaybackPositionChanged(int newPosition)
		{
			updatePlaybackPosition(newPosition);
		}

		@Override
		public void onMultimediaChanged(Multimedia newMultimedia)
		{
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
}
