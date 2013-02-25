package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
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

import com.omertron.tvrageapi.model.EpisodeList;
import com.omertron.tvrageapi.model.ShowInfo;
import com.omertron.tvrageapi.tools.TVRageParser;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_show);
		db = new ShowDatabaseManager(this);
		
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
                String name = ((TextView) view.findViewById(R.id.show_name)).getText().toString();
                String showid = ((TextView) view.findViewById(R.id.show_id)).getText().toString();
                new getShowInfoTask().execute(showid);
            }
        });

	}
	
	class getShowInfoTask extends AsyncTask<String, Void, Void> {
		Show show = new Show();
		EpisodeList epList = new EpisodeList();
		static final String URL = "http://services.tvrage.com/feeds/full_show_info.php?sid=";
	
	    @Override
	    protected Void doInBackground(String... params) {
	    	Helper parser = new Helper();
			String xml = parser.getXmlFromUrl(URL + params[0]);
			Document doc = parser.getDomElement(xml);
			
			List<ShowInfo> showList = TVRageParser.getSearchShow(doc);

			ShowInfo tmp = showList.get(0);
			
            show.setId(tmp.getShowID());
            show.setName(tmp.getShowName());
            show.setAirDay(tmp.getAirDay());
            show.setAirTime(tmp.getAirTime());
            show.setGenre(StringUtils.join(tmp.getGenres(), ", "));
            show.setNumSeasons(tmp.getTotalSeasons());
            show.setRunning(tmp.getEnded().length() > 0);
            show.setRunTime(tmp.getRuntime());
            show.setSummary(tmp.getSummary());
			
			epList = TVRageParser.getEpisodeList(doc);
			
			Log.d("Latest Episode", epList.getLatestEpisode().getEpisodeNumber().getSxxEyy());
			
	        return null;
	
	    }
	
	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
            db.addShow(show);
            db.addEpisodeList(epList);
            Toast.makeText(getApplicationContext(), "Show added!", Toast.LENGTH_SHORT).show();
            finish();
	    }
	
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	}

	class SearchShowsTask extends AsyncTask<String, Void, Void> {
			// All static variables
		 static final String URL = "http://services.tvrage.com/feeds/search.php?show=";
		 // XML node keys
		 ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
	
	    @Override
	    protected Void doInBackground(String... params) {
	    	Helper parser = new Helper();
	    	Log.e("SEARCH", params[0]);
	    	String searchStr = params[0].replace(' ', '.');
			String xml = parser.getXmlFromUrl(URL + searchStr);
			Document doc = parser.getDomElement(xml);
			 
			NodeList nl = doc.getElementsByTagName(KEY_ITEM);
			for (int i = 0; i < nl.getLength(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				map.put(KEY_ID, parser.getValue(e, KEY_ID));
				map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
				menuItems.add(map);
			}
	        return null;
	
	    }
	
	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
			 
	        ListAdapter adapter = new SimpleAdapter(SearchShows.this, menuItems,
	                R.layout.search_result_list_item,
	                new String[] { KEY_NAME, KEY_ID }, new int[] {
	                        R.id.show_name, R.id.show_id});
	        list.setAdapter(adapter);
	    }
	
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	}
}


