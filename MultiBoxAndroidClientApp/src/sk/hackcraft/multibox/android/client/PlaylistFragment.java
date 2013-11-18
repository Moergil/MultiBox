package sk.hackcraft.multibox.android.client;

import java.util.List;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.Playlist;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlaylistFragment extends Fragment
{	
	private PlaylistAdapter playlistAdapter;
	
	private ListView playlistView;
	
	private Playlist playlist;
	private PlaylistListener playlistListener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		playlistAdapter = new PlaylistAdapter(getActivity());

		PlaylistProvider playlistProvider = (PlaylistProvider)getActivity();
		playlist = playlistProvider.providePlaylist();
		
		playlistListener = new PlaylistListener();
		playlist.registerListener(playlistListener);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_playlist, container, false);
		
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
		
		playlist.init();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		playlist.close();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		playlist.unregisterListener(playlistListener);
	}
	
	public void setPlaylist(List<Multimedia> playlist)
	{
		playlistAdapter.clear();
		
		if (!playlist.isEmpty())
		{
			playlist.remove(0);
			playlistAdapter.addAll(playlist);
		}
		
		playlistAdapter.notifyDataSetChanged();
	}
	
	private class PlaylistAdapter extends ArrayAdapter<Multimedia>
	{
		public PlaylistAdapter(Context context)
		{
			super(context, R.layout.item_playlist);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			Multimedia multimedia = getItem(position);
			
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
		public void onPlaylistChanged(List<Multimedia> newPlaylist)
		{
			setPlaylist(newPlaylist);
		}

		@Override
		public void onItemAdded(boolean success, Multimedia multimedia)
		{
		}
	}
	
	public interface PlaylistProvider
	{
		public Playlist providePlaylist();
	}
}
