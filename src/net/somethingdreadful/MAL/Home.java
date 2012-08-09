package net.somethingdreadful.MAL;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class Home extends FragmentActivity implements ActionBar.TabListener, AnimuFragment.IAnimeFragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    HomeSectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Context context;
    PrefManager mPrefManager;
    public MALManager mManager;
    private boolean init = false;
    AnimuFragment af;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        
        mPrefManager = new PrefManager(context);
        init = mPrefManager.getInit();

		if (init == true) {
			setContentView(R.layout.activity_home);
			// Creates the adapter to return the Animu and Mango fragments
			mSectionsPagerAdapter = new HomeSectionsPagerAdapter(
					getSupportFragmentManager());
			
			mManager = new MALManager(context);

			// Set up the action bar.
			final ActionBar actionBar = getActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setPageMargin(32);

			// When swiping between different sections, select the corresponding
			// tab.
			// We can also use ActionBar.Tab#select() to do this if we have a
			// reference to the
			// Tab.
			mViewPager
					.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
						@Override
						public void onPageSelected(int position) {
							actionBar.setSelectedNavigationItem(position);
						}
					});

			// Add tabs for the animu and mango lists
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
				// Create a tab with text corresponding to the page title
				// defined by the adapter.
				// Also specify this Activity object, which implements the
				// TabListener interface, as the
				// listener for when this tab is selected.
				actionBar.addTab(actionBar.newTab()
						.setText(mSectionsPagerAdapter.getPageTitle(i))
						.setTabListener(this));
			
			}
			
			
			
		}
		else
		{
			Intent firstRunInit = new Intent(this, FirstTimeInit.class);
        	firstRunInit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(firstRunInit);
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.menu_settings:
    		startActivity(new Intent (this, Settings.class));
    		break;
    	
    	case R.id.listType_all:
    		if (af != null)
    		{
    			af.getAnimeRecords(0);
    		}	
    		break;
    	case R.id.listType_watching:
    		if (af != null)
    		{
    			af.getAnimeRecords(1);
    		}
    		break;
    	case R.id.listType_completed:
    		if (af != null)
    		{
    			af.getAnimeRecords(2);
    		}
    		break;
    	case R.id.listType_onhold:
    		if (af != null)
    		{
    			af.getAnimeRecords(3);
    		}
    		break;
    	case R.id.listType_dropped:
    		if (af != null)
    		{
    			af.getAnimeRecords(4);
    		}
    		break;
    	case R.id.listType_plantowatch:
    		if (af != null)
    		{
    			af.getAnimeRecords(5);
    		}
    		break;
    		
    	}
    	
    	return super.onOptionsItemSelected(item);
    }

    


    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }


    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

	public void fragmentReady() {
		// TODO Auto-generated method stub
		af = (AnimuFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);
	}


}
