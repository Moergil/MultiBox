package sk.hackcraft.multibox;

import sk.hackcraft.multibox.model.Multimedia;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlayerFragment extends Fragment
{
	private Multimedia multimedia;
	
	private TextView nameView;
	private TextView timeView;
	private ProgressBar progressView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_player, container, false);
		
		nameView = (TextView)layout.findViewById(R.id.player_multimedia_name);
		timeView = (TextView)layout.findViewById(R.id.player_multimedia_time);
		progressView = (ProgressBar)layout.findViewById(R.id.player_multimedia_progress);
		
		return layout;
	}
	
	public void setActualMultimedia(Multimedia multimedia)
	{
		this.multimedia = multimedia;
		
		String name = multimedia.getName();
		nameView.setText(name);
		
		updatePlayingPosition();
	}

	public void updatePlayingPosition()
	{
		int length = multimedia.getLength();
		int playPosition = multimedia.getPlayPosition();
		
		int estimatedTime = length - playPosition;
		int seconds = estimatedTime % 60;
		int minutes = estimatedTime / 60;
		timeView.setText(String.format("%d:%d", minutes, seconds));
		
		progressView.setMax(length);
		progressView.setProgress(playPosition);
	}
}
