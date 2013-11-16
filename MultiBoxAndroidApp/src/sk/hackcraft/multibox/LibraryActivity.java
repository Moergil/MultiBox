package sk.hackcraft.multibox;

import java.util.List;

import sk.hackcraft.multibox.LibraryDirectoryFragment.LibraryEventListener;
import sk.hackcraft.multibox.LibraryDirectoryFragment.LibraryProvider;
import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.LibraryItem;
import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.Playlist;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

public class LibraryActivity extends Activity implements LibraryEventListener, LibraryProvider
{
	private LibraryDirectoryFragment libraryDirectoryFragment;
	
	private Playlist playlist;
	private PlaylistListener playlistListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_library);
		
		FragmentManager fragmentManager = getFragmentManager();
		libraryDirectoryFragment = (LibraryDirectoryFragment)fragmentManager.findFragmentById(R.id.fragment_library_directory);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		playlist = application.getPlaylist();
		
		playlistListener = new PlaylistListener();
		playlist.registerListener(playlistListener);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		playlist.init();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		playlist.close();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		playlist.unregisterListener(playlistListener);
	}
	
	@Override
	public void onDirectoryChanged(long id, String name)
	{
		setTitle(name);
	}

	@Override
	public void onItemOpened(LibraryItem item)
	{
		long id = item.getId();
		
		switch (item.getType())
		{
			case MULTIMEDIA:
				addToPlaylist(id);
				break;
			default:
		}
	}
	
	@Override
	public Library provideLibrary()
	{
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		return application.getLibrary();
	}
	
	@Override
	public void onBackPressed()
	{
		boolean result = libraryDirectoryFragment.navigateBack();
		
		if (!result)
		{
			super.onBackPressed();
		}
	}
	
	private void addToPlaylist(long itemId)
	{
		playlist.addItem(itemId);
	}
	
	private class PlaylistListener implements Playlist.PlaylistEventListener
	{
		@Override
		public void onPlaylistChanged(List<Multimedia> newPlaylist)
		{
		}

		@Override
		public void onItemAdded(boolean success, Multimedia multimedia)
		{
			Toast.makeText(LibraryActivity.this, "Item " + multimedia.getName() + " added: " + success, Toast.LENGTH_SHORT).show();
		}
	}
}
