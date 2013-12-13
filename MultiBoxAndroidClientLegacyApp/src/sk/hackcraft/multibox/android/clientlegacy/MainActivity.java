package sk.hackcraft.multibox.android.clientlegacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sk.hackcraft.multibox.android.clientlegacy.util.ActivityTransitionAnimator;
import sk.hackcraft.multibox.model.Server;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements BackPressedEvent
{
	private static final String SAVED_STATE_KEY_SERVER_NAME = "serverName";
	
	private static final int PLAYER_VIEWPAGER_INDEX = 0;
	private static final int LIBRARY_VIEWPAGER_INDEX = 1;
	
	public static final String EXTRA_KEY_SERVER_NAME = "serverName";
	
	private Server server;
	
	private ViewPager viewPager;
	private MenuAdapter menuAdapter;
	
	private List<BackPressedListener> backPressedListeners;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String serverName = intent.getStringExtra(EXTRA_KEY_SERVER_NAME);

		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		server = application.getServer();
		
		backPressedListeners = new LinkedList<BackPressedListener>();
		
		setTitle(serverName);
		
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager)findViewById(R.id.pager);
		
		menuAdapter = new MenuAdapter(this, viewPager);	
		
		menuAdapter.addPage(PLAYER_VIEWPAGER_INDEX, PlayerFragment.class);
		menuAdapter.addPage(LIBRARY_VIEWPAGER_INDEX, LibraryFragment.class);
		
		viewPager.setAdapter(menuAdapter);
		
		if (savedInstanceState != null)
		{
			restoreState(savedInstanceState);
		}
		
		Button playerButton = (Button)findViewById(R.id.playlist_tab_button);
		playerButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				viewPager.setCurrentItem(PLAYER_VIEWPAGER_INDEX);
			}
		});
		
		Button libraryButton = (Button)findViewById(R.id.library_tab_button);
		libraryButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				viewPager.setCurrentItem(LIBRARY_VIEWPAGER_INDEX);
			}
		});
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		if (isFinishing())
		{
			ActivityTransitionAnimator.runFinishActivityAnimation(this);
		}
	}
	
	private void restoreState(Bundle savedInstanceState)
	{
		String serverName = savedInstanceState.getString(SAVED_STATE_KEY_SERVER_NAME);
		setTitle(serverName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.playlist, menu);
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		
		outState.putString(SAVED_STATE_KEY_SERVER_NAME, (String)getTitle());
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
		
		server.requestInfo(new Server.ServerInfoListener()
		{
			@Override
			public void onServerNameReceived(String name)
			{
				setTitle(name);
			}
		});
	}
	
	@Override
	public void onBackPressed()
	{
		Fragment fragment = getCurrentVisibleViewPagerFragment(Fragment.class);
		
		boolean backPressConsumed = false;
		
		if (fragment instanceof BackPressedListener)
		{
			BackPressedListener listener = (BackPressedListener)fragment;
			
			backPressConsumed = listener.onBackPressed();
		}
		
		if (!backPressConsumed)
		{
			super.onBackPressed();
		}
	}
	
	@Override
	public void registerBackPressedListener(BackPressedListener listener)
	{
		backPressedListeners.add(listener);
	}
	
	@Override
	public void unregisterBackPressedListener(BackPressedListener listener)
	{
		backPressedListeners.remove(listener);
	}
	
	private <F extends Fragment> F getCurrentVisibleViewPagerFragment(Class<F> fragmentClass)
	{
		int position = viewPager.getCurrentItem();
		return getViewPagerFragment(position, fragmentClass);
	}
	
	private <F extends Fragment> F getViewPagerFragment(int position, Class<F> fragmentClass)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		String tag = createViewPagerFragmentTag(position);
		Fragment fragment = fragmentManager.findFragmentByTag(tag);
		
		return fragmentClass.cast(fragment);
	}
	
	private String createViewPagerFragmentTag(int position)
	{
		return "android:switcher:" + R.id.pager + ":" + position;
	}
	
	private class MenuAdapter extends FragmentPagerAdapter
	{
		private final Map<Integer, Class<? extends Fragment>> fragmentClasses;
		
		public MenuAdapter(FragmentActivity activity, ViewPager pager)
		{
			super(activity.getSupportFragmentManager());
			
			fragmentClasses = new HashMap<Integer, Class<? extends Fragment>>();
		}
		
		public void addPage(int index, Class<? extends Fragment> fragmentClass)
		{
			fragmentClasses.put(index, fragmentClass);
			
			notifyDataSetChanged();
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position)
		{
			Class<? extends Fragment> fragmentClass = fragmentClasses.get(position);
			
			return Fragment.instantiate(MainActivity.this, fragmentClass.getName());
		}

		@Override
		public int getCount()
		{
			return fragmentClasses.size();
		}
	}
}
