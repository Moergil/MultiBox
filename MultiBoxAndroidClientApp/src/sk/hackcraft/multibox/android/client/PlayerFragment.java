package sk.hackcraft.multibox.android.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerFragment extends Fragment
{
	private Player player;
	private PlayerListener playerListener;
	
	private Playlist playlist;
	private PlaylistListener playlistListener;

	private TextView nameView;
	private TextView timeView;
	private ProgressBar progressView;
	
	private PlaylistAdapter playlistAdapter;	
	private ListView playlistView;
	
	private CountDownTimer playbackPositionUpdater;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		PlayerProvider playerProvider = (PlayerProvider)getActivity();
		player = playerProvider.providePlayer();
		
		playerListener = new PlayerListener();
		player.registerPlayerEventListener(playerListener);
		
		playlistAdapter = new PlaylistAdapter(getActivity());

		PlaylistProvider playlistProvider = (PlaylistProvider)getActivity();
		playlist = playlistProvider.providePlaylist();
		
		playlistListener = new PlaylistListener();
		playlist.registerListener(playlistListener);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_player, container, false);
		
		nameView = (TextView)layout.findViewById(R.id.player_multimedia_name);
		timeView = (TextView)layout.findViewById(R.id.player_multimedia_time);
		progressView = (ProgressBar)layout.findViewById(R.id.player_multimedia_progress);
		
		playlistView = (ListView)layout.findViewById(R.id.playlist);
		
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		playlistView.setAdapter(playlistAdapter);
		playlistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(getActivity(), "clicked " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		player.init();
		playlist.init();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		player.close();
		playlist.close();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		player.unregisterListener(playerListener);
		playlist.unregisterListener(playlistListener);
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
				
				int actualSeconds = player.getActiveMultimedia().getLength() - secondsUntilFinished;
				
				updatePlaybackPosition(actualSeconds);
			}
			
			@Override
			public void onFinish()
			{
				updatePlaybackPosition(player.getActiveMultimedia().getLength());
			}
		};
		
		playbackPositionUpdater.start();
	}
	
	private void stopPlaybackPositionUpdater()
	{
		playbackPositionUpdater.cancel();
		playbackPositionUpdater = null;
	}
	
	public void showMultimedia(MultimediaItem multimedia)
	{
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
		int length = player.getActiveMultimedia().getLength();
		
		int estimatedTime = length - newPlayPosition;
		int seconds = (int)(estimatedTime % 60);
		int minutes = (int)(estimatedTime / 60);
		timeView.setText(String.format("%d:%d", minutes, seconds));

		progressView.setProgress(newPlayPosition);
	}
	
	public void setPlaylist(List<MultimediaItem> playlist)
	{
		playlistAdapter.clear();
		
		if (!playlist.isEmpty())
		{
			playlist.remove(0);
			playlistAdapter.addAll(playlist);
		}
		
		playlistAdapter.notifyDataSetChanged();
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
				
				int secondsToEnd = player.getActiveMultimedia().getLength() - newPosition;
				startPlaybackPositionUpdater(secondsToEnd);
			}
		}

		@Override
		public void onMultimediaChanged(MultimediaItem newMultimedia)
		{
			if (playbackPositionUpdater != null)
			{
				stopPlaybackPositionUpdater();
			}
			
			if (!player.hasActiveMultimedia())
			{
				showNothing();
			}
			else
			{
				showMultimedia(newMultimedia);
			}
		}
	}
	
	private class PlaylistAdapter extends ArrayAdapter<MultimediaItem>
	{
		public PlaylistAdapter(Context context)
		{
			super(context, R.layout.item_playlist);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			MultimediaItem multimedia = getItem(position);
			
			Context context = getContext();
			LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View multimediaItemView = viewInflater.inflate(R.layout.item_playlist, null);

			TextView multimediaNameView = (TextView)multimediaItemView.findViewById(R.id.multimedia_item_name);
			
			String name = multimedia.getName();
			multimediaNameView.setText(name);
			
			return multimediaItemView;
		}
	}
	
	private class PlaylistListener implements Playlist.PlaylistEventListener
	{
		@Override
		public void onPlaylistChanged(List<MultimediaItem> newPlaylist)
		{
			setPlaylist(newPlaylist);
		}

		@Override
		public void onItemAdded(boolean success, MultimediaItem multimedia)
		{
		}
	}
	
	public interface PlaylistProvider
	{
		public Playlist providePlaylist();
	}
	
	public interface PlayerProvider
	{
		public Player providePlayer();
	}
}
