package com.teamsparkle.sparkletv;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SearchView;

import com.team.sparkle.sparkletv.R;

public class SparkleTVActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.activity_sparkle_tv, menu);
			// Get the SearchView and set the searchable configuration
		    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		    if(null!=searchManager ) {   
		         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		    }
		    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
		    searchView.setSubmitButtonEnabled(true);
		    return true;
		}
		
}
