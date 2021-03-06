package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;


public class SparkleTVActivity extends Activity {
	public ShowDatabaseManager db;
	public SearchView searchView;
	ArrayList<HashMap<String, String>> favShows = new ArrayList<HashMap<String, String>>();
	Intent watcherIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_layout);
        db = new ShowDatabaseManager(this);
        
        watcherIntent = new Intent(this, WatcherService.class);
        
        startService(watcherIntent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(searchView != null)
		{
			searchView.setIconified(true);
			searchView.setIconifiedByDefault(true);
			searchView.clearFocus();
			if(searchView.hasFocus())
			{
				NavUtils.navigateUpFromSameTask(this);
			}
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sparkle_tv_menu, menu);
		
		
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    if(null!=searchManager ) {   
	         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    searchView.setIconifiedByDefault(true);
	    searchView.setSubmitButtonEnabled(true);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                db.clearDatabase();
                FavsFragment favs = (FavsFragment) getFragmentManager().findFragmentById(R.id.favs);
                favs.updateFavList();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.menu_settings:   
            	Intent intent = new Intent();
            	intent.setClass(SparkleTVActivity.this, SetPreferenceActivity.class);
            	startActivityForResult(intent, 0); 
            	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                
                OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                		if(key.equals("folderwatcher_preference") || key.equals("filemover_preference")){
	                		stopService(watcherIntent);
	                		startService(watcherIntent);
                		}
                		return;
                	}
                };
                
                prefs.registerOnSharedPreferenceChangeListener(listener);
            	break;
        }
        return true;
    }
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
}
