package sk.hackcraft.multibox.android.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.android.client.LibraryFragment.LibraryProvider;
import sk.hackcraft.multibox.android.client.PlayerFragment.PlayerProvider;
import sk.hackcraft.multibox.android.client.PlayerFragment.PlaylistProvider;
import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
import sk.hackcraft.multibox.model.Server;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements PlayerProvider, PlaylistProvider, LibraryProvider, BackPressedEvent
{
	private static final String SAVED_STATE_KEY_ACTIVE_TAB = "activeTab";
	private static final String SAVED_STATE_KEY_SERVER_NAME = "serverName";
	
	private Server server;
	
	private ViewPager viewPager;
	private TabsAdapter tabsAdapter;
	
	private ActionBar actionBar;
	
	private List<BackPressedListener> backPressedListeners;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		server = application.getServer();
		
		backPressedListeners = new LinkedList<BackPressedListener>();
		
		actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager)findViewById(R.id.pager);
		
		tabsAdapter = new TabsAdapter(this, viewPager);

		Tab tab;
		
		tab = actionBar.newTab();
		tab.setText(R.string.playlist);
		tabsAdapter.addTab(tab,PlayerFragment.class);
		
		tab = actionBar.newTab();
		tab.setText(R.string.library);
		tabsAdapter.addTab(tab, LibraryFragment.class);

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrollStateChanged(int state)
			{
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
			}

			@Override
			public void onPageSelected(int position)
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		viewPager.setAdapter(tabsAdapter);
		
		if (savedInstanceState != null)
		{
			int activeTab = savedInstanceState.getInt(SAVED_STATE_KEY_ACTIVE_TAB);
			actionBar.setSelectedNavigationItem(activeTab);
			
			String serverName = savedInstanceState.getString(SAVED_STATE_KEY_SERVER_NAME);
			actionBar.setTitle(serverName);
		}
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
		
		outState.putInt(SAVED_STATE_KEY_ACTIVE_TAB, actionBar.getSelectedNavigationIndex());
		outState.putString(SAVED_STATE_KEY_SERVER_NAME, (String)actionBar.getTitle());
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		server.requestInfo(new Server.ServerInfoListener()
		{
			@Override
			public void onServerNameReceived(String name)
			{
				actionBar.setTitle(name);
			}
		});
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed()
	{
		for (BackPressedListener listener : backPressedListeners)
		{
			if (listener.onBackPressed())
			{
				return;
			}
		}

		super.onBackPressed();
		ActivityTransitionAnimator.runFinishActivityAnimation(this);
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

	@Override
	public Player providePlayer()
	{
		return server.getPlayer();
	}
	
	@Override
	public Playlist providePlaylist()
	{
		return server.getPlaylist();
	}
	
	@Override
	public Library provideLibrary()
	{
		return server.getLibrary();
	}
	
	private class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener
	{
		private final List<Class<? extends Fragment>> fragmentClasses;
		
		public TabsAdapter(Activity activity, ViewPager pager)
		{
			super(activity.getFragmentManager());
			
			fragmentClasses = new ArrayList<Class<? extends Fragment>>();
		}
		
		public void addTab(Tab tab, Class<? extends Fragment> fragmentClass)
		{
			fragmentClasses.add(fragmentClass);
			
			tab.setTag(fragmentClass);
			tab.setTabListener(this);
			
			actionBar.addTab(tab);
			
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position)
		{
			Class<? extends Fragment> fragmentClass = fragmentClasses.get(position);
			
			return Fragment.instantiate(MainActivity.this, fragmentClass.getName());
		}

		@Override
		public int getCount()
		{
			return actionBar.getTabCount();
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft)
		{
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft)
		{
			Class<? extends Fragment> fragmentClass = (Class<? extends Fragment>)tab.getTag();
			
			for (int i = 0; i < fragmentClasses.size(); i++)
			{
				if (fragmentClasses.get(i) == fragmentClass)
				{
					viewPager.setCurrentItem(i);
					break;
				}
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft)
		{
		}
	}
}
