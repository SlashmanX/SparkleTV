package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.HashMap;

import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.Show;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;


public class FragmentLayout extends Activity {
	public ShowDatabaseManager db;
	ArrayList<HashMap<String, String>> favShows = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_layout);
        db = new ShowDatabaseManager(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
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
                FavsFragment favs = (FavsFragment) getFragmentManager().findFragmentById(R.id.favs);
                favs.updateFavList();
                break;
        }
        return true;
    }
}
