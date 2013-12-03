package sk.hackcraft.multibox.android.client;

import java.util.ArrayList;
import java.util.List;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.android.client.LibraryFragment.LibraryEventListener;
import sk.hackcraft.multibox.android.client.LibraryFragment.LibraryProvider;
import sk.hackcraft.multibox.android.client.PlayerFragment.PlayerProvider;
import sk.hackcraft.multibox.android.client.PlayerFragment.PlaylistProvider;
import sk.hackcraft.multibox.model.Library;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.Playlist;
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

public class MainActivity extends Activity implements PlayerProvider, PlaylistProvider, LibraryProvider, LibraryEventListener
{
	private static final String SAVED_STATE_KEY_ACTIVE_TAB = "activeTab";
	
	private ViewPager viewPager;
	private TabsAdapter tabsAdapter;
	
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public Player providePlayer()
	{
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		
		return application.getPlayer();
	}
	
	@Override
	public Playlist providePlaylist()
	{
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		
		return application.getPlaylist();
	}
	
	@Override
	public Library provideLibrary()
	{
		MultiBoxApplication application = (MultiBoxApplication)getApplication();
		
		return application.getLibrary();
	}
	
	@Override
	public void onItemChanged(long id, String name)
	{
		//actionBar.setTitle(name);
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
