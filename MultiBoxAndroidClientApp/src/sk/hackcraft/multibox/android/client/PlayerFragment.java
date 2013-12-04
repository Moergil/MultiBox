package sk.hackcraft.multibox.android.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.model.Server;
import sk.hackcraft.multibox.model.libraryitems.MultimediaItem;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

	private MultimediaItem multimedia;
	
	private CountDownTimer playbackPositionUpdater;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		
		Activity activity = getActivity();
		MultiBoxApplication application = (MultiBoxApplication)activity.getApplication();
		
		Server server = application.getServer();
		player = server.getPlayer();
		playlist = server.getPlaylist();
		
		playerListener = new PlayerListener();
		player.registerPlayerEventListener(playerListener);
		
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
		
		Activity activity = getActivity();

		playlistAdapter = new PlaylistAdapter(activity);
		
		playlistView.setAdapter(playlistAdapter);
		playlistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(getActivity(), "clicked " + position, Toast.LENGTH_SHORT).show();
			}
		});
		
		if (player.isPlaying())
		{
			MultimediaItem multimedia = player.getActiveMultimedia();
			showMultimedia(multimedia);
			
			int playbackPosition = player.getPlaybackPosition();
			synchronizePlaybackPositionInfo(playbackPosition);
		}
		else
		{
			showNothing();
		}
		
		setPlaylist(playlist.getItems());
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		player.init();
		playlist.init();
		
		if (player.isPlaying())
		{
			MultimediaItem multimedia = player.getActiveMultimedia();
			int length = multimedia.getLength();
			int playbackPosition = player.getPlaybackPosition();
			
			startPlaybackPositionUpdater(length, playbackPosition);
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		player.close();
		playlist.close();
		
		if (player.isPlaying())
		{
			stopPlaybackPositionUpdater();
		}
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		player.unregisterListener(playerListener);
		playlist.unregisterListener(playlistListener);
	}
	
	private void startPlaybackPositionUpdater(final int length, int playbackPosition)
	{
		long millisToEnd = TimeUnit.SECONDS.toMillis(length - playbackPosition);
		
		Display display = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int refreshRate = (int)display.getRefreshRate();
		
		playbackPositionUpdater = new CountDownTimer(millisToEnd, TimeUnit.SECONDS.toMillis(1) / refreshRate)
		{
			@Override
			public void onTick(long millisUntilFinished)
			{
				long actualMilliseconds = TimeUnit.SECONDS.toMillis(length) - millisUntilFinished;
				setPlaybackPositionInfo(length, actualMilliseconds);
			}
	
			@Override
			public void onFinish()
			{
				int length = player.getActiveMultimedia().getLength();
				setPlaybackPositionInfo(length, TimeUnit.SECONDS.toMillis(length));
			}
		};
		
		playbackPositionUpdater.start();
	}
	
	private void stopPlaybackPositionUpdater()
	{
		if (playbackPositionUpdater == null)
		{
			return;
		}
		
		playbackPositionUpdater.cancel();
		playbackPositionUpdater = null;
	}
	
	public void showMultimedia(MultimediaItem multimedia)
	{
		this.multimedia = multimedia;
		
		String name = multimedia.getName();
		nameView.setText(name);
		
		int length = multimedia.getLength();
		progressView.setMax((int)TimeUnit.SECONDS.toMillis(length));
		synchronizePlaybackPositionInfo(0);
	}
	
	public void showNothing()
	{
		nameView.setText("Nothing to play...");
		timeView.setText("");
		progressView.setProgress(0);
	}
	
	public void setPlaybackPositionInfo(int length, long playbackPositionMillis)
	{
		int estimatedTime = length - (int)TimeUnit.MILLISECONDS.toSeconds(playbackPositionMillis);
		int seconds = (int)(estimatedTime % 60);
		int minutes = (int)(estimatedTime / 60);
		timeView.setText(String.format("%d:%d", minutes, seconds));

		progressView.setProgress((int)playbackPositionMillis);
	}

	public void synchronizePlaybackPositionInfo(int playbackPosition)
	{
		MultimediaItem multimedia = player.getActiveMultimedia();
		
		int length = multimedia.getLength();
		setPlaybackPositionInfo(length, playbackPosition);
	}
	
	public void setPlaylist(List<MultimediaItem> playlist)
	{
		playlistAdapter.clear();
		
		List<MultimediaItem> playlistCopy = new ArrayList<MultimediaItem>(playlist);
		if (!playlistCopy.isEmpty())
		{
			playlistCopy.remove(0);
			playlistAdapter.addAll(playlistCopy);
		}
		
		playlistAdapter.notifyDataSetChanged();
	}
	
	private class PlayerListener implements Player.PlayerEventListener
	{
		/*@Override
		public void onPlayingStateChanged(boolean playing)
		{
			if (playing)
			{
				MultimediaItem multimedia = player.getActiveMultimedia();
				int length = multimedia.getLength();
				
				int playbackPosition = player.getPlaybackPosition();
				
				startPlaybackPositionUpdater(length, length - playbackPosition);
			}
			else
			{
				stopPlaybackPositionUpdater();
			}
		}

		@Override
		public void onPlaybackPositionChanged(int newPosition)
		{
			synchronizePlaybackPositionInfo(newPosition);
		}

		@Override
		public void onMultimediaChanged(MultimediaItem newMultimedia)
		{
			if (newMultimedia == null)
			{
				showNothing();
			}
			else
			{
				showMultimedia(newMultimedia);
			}
		}*/

		@Override
		public void set(MultimediaItem multimedia)
		{
			if (multimedia != null)
			{
				showMultimedia(multimedia);
			}
			else
			{
				showNothing();
			}
		}
		
		@Override
		public void play()
		{
			play(0);
		}

		@Override
		public void play(int playbackPosition)
		{
			int length = multimedia.getLength();
			
			startPlaybackPositionUpdater(length, playbackPosition);
		}

		@Override
		public void pause()
		{
			stopPlaybackPositionUpdater();
		}
		
		@Override
		public void synchronizePlaybackPosition(int playbackPosition)
		{
			pause();
			play(playbackPosition);
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
}
