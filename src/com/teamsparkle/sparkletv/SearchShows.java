package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.model.Episode;
import com.omertron.thetvdbapi.model.Series;
import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.Helper;
import com.teamsparkle.sparkletv.helpers.Show;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

public class SearchShows extends Activity {
	
	ListView list;
	static final String KEY_ITEM = "show"; // parent node
	static final String KEY_ID = "showid";
	static final String KEY_NAME = "name";
	public ShowDatabaseManager db;
	public TheTVDBApi tvdb;
	ProgressDialog searchingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_show);
		db = new ShowDatabaseManager(this);
		tvdb = new TheTVDBApi("35A222B84DD0FA85");
		searchingDialog = ProgressDialog.show(SearchShows.this, "", 
                "Searching. Please wait...", true);
		
		list = (ListView) findViewById(R.id.search_results);
		// Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	    	new SearchShowsTask().execute(query);
	    }
	    
	    list.setOnItemClickListener(new OnItemClickListener() {
	    	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String showid = ((TextView) view.findViewById(R.id.search_show_id)).getText().toString();
                Log.d("Show", showid + "");
                new getShowInfoTask().execute(showid);
            }
        });

	}
	
	class getShowInfoTask extends AsyncTask<String, Void, Void> {

		ProgressDialog addingDialog = ProgressDialog.show(SearchShows.this, "","Adding Episode List to Database. Please wait...", true);
		Show show = new Show();
		List<Episode> eps = new ArrayList<Episode>();
		String showid = "";
	
	    @Override
	    protected Void doInBackground(String... params) {
	    	Log.d("SERIES ID", params[0]);
	    	Series tmp = tvdb.getSeries(params[0], "en");
	    	showid = params[0];
			
            show.setId(Integer.parseInt(tmp.getId()));
            show.setName(tmp.getSeriesName());
            show.setAirDay(tmp.getAirsDayOfWeek());
            show.setAirTime(tmp.getAirsTime());
            show.setGenre(StringUtils.join(tmp.getGenres(), ", "));
            //show.setRunning(tmp.getStatus());
            show.setRunTime(Integer.parseInt(tmp.getRuntime()));
            show.setSummary(tmp.getOverview());
			
			eps = tvdb.getAllEpisodes(params[0], "en");
			
	        return null;
	
	    }
	
	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
            db.addShow(show);
            db.addEpisodeList(showid, eps);
            addingDialog.dismiss();
            finish();
	    }
	
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	}

	class SearchShowsTask extends AsyncTask<String, Void, Void> {
		 // XML node keys
		 ArrayList<HashMap<String, String>> searchResults = new ArrayList<HashMap<String, String>>();
	
	    @Override
	    protected Void doInBackground(String... params) {
	    	List<Series> search = tvdb.searchSeries(params[0], "en");
			for (int i = 0; i < search.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Series s = search.get(i);
				map.put(KEY_ID, s.getId());
				map.put(KEY_NAME, s.getSeriesName());
				map.put("summary", s.getOverview());
				searchResults.add(map);
			}
	        return null;
	
	    }
	
	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
			 
	        ListAdapter adapter = new SimpleAdapter(SearchShows.this, searchResults,
	                R.layout.search_result_list_item,
	                new String[] { KEY_NAME, KEY_ID, "summary" }, new int[] {
	                        R.id.search_show_name, R.id.search_show_id, R.id.search_show_summary});
	        searchingDialog.dismiss();
	        list.setAdapter(adapter);
	    }
	
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	}
}


