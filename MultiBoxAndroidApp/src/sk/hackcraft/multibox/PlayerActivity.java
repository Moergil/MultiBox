package sk.hackcraft.multibox;

import sk.hackcraft.multibox.PlayerInfoFragment.PlayerProvider;
import sk.hackcraft.multibox.PlaylistFragment.PlaylistProvider;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PlayerActivity extends Activity implements PlayerProvider, PlaylistProvider
{
	private PlayerInfoFragment playerFragment;
	private PlaylistFragment playlistFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_player);
		
		FragmentManager fragmentManager = getFragmentManager();
		playerFragment = (PlayerInfoFragment)fragmentManager.findFragmentById(R.id.fragment_player);
		playlistFragment = (PlaylistFragment)fragmentManager.findFragmentById(R.id.fragment_playlist);
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
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
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
		Intent openLibraryIntent = new Intent(this, LibraryActivity.class);
		startActivity(openLibraryIntent);
	}

	@Override
	public Player providePlayer()
	{
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		
		return application.createPlayer();
	}
	
	@Override
	public Playlist providePlaylist()
	{
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		
		return application.createPlaylist();
	}
}
