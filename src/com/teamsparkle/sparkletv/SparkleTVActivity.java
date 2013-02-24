package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.Show;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

public class SparkleTVActivity extends Activity {

	public ShowDatabaseManager db;
	ArrayList<HashMap<String, String>> favShows = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		db = new ShowDatabaseManager(this);
		updateFavList();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateFavList();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sparkle_tv_menu, menu);
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    if(null!=searchManager ) {   
	         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    searchView.setSubmitButtonEnabled(true);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                db.clearDatabase();
                updateFavList();
                break;
        }
        return true;
    }
	
	public void updateFavList() {
		favShows = new ArrayList<HashMap<String, String>>();
		ArrayList<Show> tmp = db.getAllShows();
		for(int i = 0; i < tmp.size(); i++)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("showid", Integer.toString(tmp.get(i).getId()));
			map.put("name", tmp.get(i).getName());
			map.put("currentSeason", "" + tmp.get(i).getCurrentSeason());
			map.put("currentEpisode", "" + tmp.get(i).getCurrentEpisode());
			favShows.add(map);
		}
		
		ListAdapter listAdapter = new SimpleAdapter(this, favShows, R.layout.fav_show_list_item,
				new String[] { "name", "currentSeason", "currentEpisode" }, 
				new int[] { R.id.fav_show_name, R.id.season_num, R.id.episode_num});
        
        ListView list = (ListView) findViewById(R.id.fav_shows_list);
        
        list.setAdapter(listAdapter);
	}
		
}
