package sk.hackcraft.multibox;

import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.HuhPlayer;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.server.MockServerInterface;
import sk.hackcraft.multibox.server.ServerInterface;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PlayerActivity extends Activity
{
	private PlayingInfobarFragment playerFragment;
	private PlaylistFragment playlistFragment;

	private ServerInterface serverInterface;
	
	private HuhPlayer player;
	private Playlist playlist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_player);
		
		FragmentManager fragmentManager = getFragmentManager();
		playerFragment = (PlayingInfobarFragment)fragmentManager.findFragmentById(R.id.fragment_player);
		playlistFragment = (PlaylistFragment)fragmentManager.findFragmentById(R.id.fragment_playlist);
		
		serverInterface = new MockServerInterface();
		
		player = new HuhPlayer(serverInterface);
		player.setEventListener(new HuhPlayer.EventListener()
		{
			@Override
			public void onStateChange(boolean playing)
			{
				playerFragment.requestPlayingStateChange(playing);
			}
			
			@Override
			public void onNothingToPlay()
			{
				playerFragment.showNothing();
			}
			
			@Override
			public void onMultimediaUpdate(int playPosition)
			{
				//playerFragment.synchronizePlayingPosition(multimedia);
			}
			
			@Override
			public void onMultimediaChange(Multimedia newMultimedia, int playPosition)
			{
				playerFragment.showMultimedia(newMultimedia, playPosition);
			}
		});
		
		playlist = new Playlist(serverInterface, new Playlist.PlaylistListener()
		{
			@Override
			public void onPlaylistReceived(List<Multimedia> playlist)
			{
				playlistFragment.setPlaylist(playlist);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.playlist, menu);
		return true;
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		player.activate();
		playlist.activate();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		player.deactivate();
		playlist.deactivate();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_library:
				openLibrary();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void openLibrary()
	{
		Toast.makeText(this, "open library", Toast.LENGTH_LONG).show();
	}
}
