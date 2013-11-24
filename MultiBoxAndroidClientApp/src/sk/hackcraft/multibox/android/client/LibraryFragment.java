package sk.hackcraft.multibox.android.client;

import java.util.List;
import java.util.Stack;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.LibraryItem;
import sk.hackcraft.multibox.model.LibraryItemType;
import sk.hackcraft.multibox.model.libraryitems.DirectoryItem;
import sk.hackcraft.multibox.model.libraryitems.BackNavigationLibraryItem;
import android.app.Activity;
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

public class LibraryFragment extends Fragment
{
	private static final String SAVED_STATE_KEY_HISTORY = "history";
	
	private Library library;
	private LibraryListener libraryListener;
	
	private ListView contentView;
	
	private DirectoryContentAdapter contentAdapter;
	
	private LibraryEventListener eventListener;

	private Stack<Long> history;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Activity activity = getActivity();
		
		contentAdapter = new DirectoryContentAdapter(activity);
		
		eventListener = (LibraryEventListener)activity;
		
		LibraryProvider libraryProvider = (LibraryProvider)activity;
		library = libraryProvider.provideLibrary();
		
		libraryListener = new LibraryListener();
		library.registerLibraryEventListener(libraryListener);
		
		history = new Stack<Long>();

		if (savedInstanceState != null)
		{
			long historyEntries[] = savedInstanceState.getLongArray(SAVED_STATE_KEY_HISTORY);
			for (Long id : historyEntries)
			{
				history.push(id);
			}			
		}
		else
		{
			history.push(Library.ROOT_DIRECTORY);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_library, container, false);
		
		contentView = (ListView)layout.findViewById(R.id.directory_content);
		
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		contentView.setAdapter(contentAdapter);
		contentView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				LibraryItem item = contentAdapter.getItem(position);

				if (item.getType() == LibraryItemType.DIRECTORY)
				{
					long itemId = item.getId();

					requestDirectory(itemId);
					
					history.push(itemId);
				}
				else if (item.getType() == LibraryItemType.BACK_NAVIGATION)
				{
					navigateBack();
				}
			}
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		library.init();
		
		if (!history.isEmpty())
		{
			long id = history.peek();
			library.requestItem(id);
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		library.close();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		library.unregisterLibraryEventListener(libraryListener);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		
		long historyEntries[] = new long[history.size()];
		for (int i = history.size() - 1; i >= 0; i--)
		{
			historyEntries[i] = history.get(i);
		}
		
		outState.putLongArray(SAVED_STATE_KEY_HISTORY, historyEntries);
	}
	
	public void navigateBack()
	{
		history.pop();
		
		if (!history.isEmpty())
		{
			long id = history.peek();
			requestDirectory(id);
		}
	}
	
	public void requestDirectory(long id)
	{
		library.requestItem(id);
	}

	private void setDirectory(DirectoryItem directory)
	{
		contentAdapter.clear();
		
		if (history.size() > 1)
		{
			contentAdapter.add(new BackNavigationLibraryItem());
		}
		
		List<LibraryItem> items = directory.getItems();
		contentAdapter.addAll(items);
		
		contentAdapter.notifyDataSetChanged();
	}
	
	private class DirectoryContentAdapter extends ArrayAdapter<LibraryItem>
	{
		public DirectoryContentAdapter(Context context)
		{
			super(context, R.layout.item_library);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LibraryItem libraryItem = getItem(position);
			
			Context context = getContext();
			LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View multimediaItemView = viewInflater.inflate(R.layout.item_library, null);

			TextView multimediaNameView = (TextView)multimediaItemView.findViewById(R.id.library_item_name);
			
			// TODO
			String name;
			if (libraryItem.getType() == LibraryItemType.DIRECTORY)
			{
				name = "[Dir] " + libraryItem.getName();
			}
			else
			{
				name = libraryItem.getName();
			}

			multimediaNameView.setText(name);
			
			return multimediaItemView;
		}
	}
	
	private class LibraryListener implements Library.LibraryEventListener
	{
		@Override
		public void onItemReceived(LibraryItem item)
		{
			if (item.getType() == LibraryItemType.DIRECTORY)
			{
				DirectoryItem directory = (DirectoryItem)item;
				setDirectory(directory);
				
				eventListener.onItemChanged(directory.getId(), directory.getName());
			}
			else
			{
				Toast.makeText(getActivity(), "TODO item detail", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public interface LibraryEventListener
	{
		public void onItemChanged(long id, String name);
	}
	
	public interface LibraryProvider
	{
		public Library provideLibrary();
	}
}
