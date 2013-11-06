package sk.hackcraft.multibox;

import java.util.List;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.HuhPlayer;
import sk.hackcraft.multibox.server.ServerInterface;
import sk.hackcraft.multibox.server.ServerInterfaceService;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlaylistFragment extends Fragment
{	
	private PlaylistAdapter playlistAdapter;
	
	private ListView playlistListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_playlist, container, false);
		
		playlistListView = (ListView)layout.findViewById(R.id.playlist);
		
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		playlistAdapter = new PlaylistAdapter(getActivity());
		playlistListView.setAdapter(playlistAdapter);
		playlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
	}
	
	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	public void setPlaying(boolean playing)
	{

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
}
