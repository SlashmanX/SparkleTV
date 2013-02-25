package com.teamsparkle.sparkletv;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.omertron.tvrageapi.model.Episode;
import com.omertron.tvrageapi.model.EpisodeList;
import com.omertron.tvrageapi.model.EpisodeNumber;
import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.Helper;
import com.teamsparkle.sparkletv.helpers.Show;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;
import com.teamsparkle.sparkletv.helpers.TorrentSearchResult;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
	public ShowDatabaseManager db;
    public static ScheduleFragment newInstance(int showid) {
    	ScheduleFragment f = new ScheduleFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("showid", showid);
        f.setArguments(args);

        return f;
    }

    public int getShowID() {
        return getArguments().getInt("showid", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	ArrayList<HashMap<String, String>> schedule = new ArrayList<HashMap<String, String>>();
    	db = new ShowDatabaseManager(getActivity().getApplicationContext());
    	
        if (container == null) {
            
            return null;
        }
        View v = inflater.inflate(R.layout.schedule_layout, container, false);
        ListView scheduleList = (ListView) v.findViewById(R.id.schedule_list_view);
        
		EpisodeList epList = db.getEpisodeList(getShowID());
		for(Map.Entry<EpisodeNumber, Episode> m : epList.getEpisodeList().entrySet()) {
    		Episode ep = m.getValue();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("epid", Integer.toString(ep.getId()));
			map.put("episode_name", ep.getTitle());
			map.put("SE", ep.getEpisodeNumber().getSxxEyy());
			map.put("show_name", epList.getShowName());
			schedule.add(map);
		}
		
		ListAdapter listAdapter = new SimpleAdapter(getActivity(), schedule, R.layout.schedule_list_item,
				new String[] { "episode_name", "epid", "SE", "show_name" }, 
				new int[] { R.id.episode_name, R.id.episode_id, R.id.episode_season_episode, R.id.episode_show_name});
        
        scheduleList.setAdapter(listAdapter);
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		String showName = ((TextView) view.findViewById(R.id.episode_show_name)).getText().toString();
        		String SxxEyy = ((TextView) view.findViewById(R.id.episode_season_episode)).getText().toString();
        		String searchString = showName + "."+ SxxEyy;
        		Toast.makeText(getActivity(), searchString.replace(' ', '.'), Toast.LENGTH_LONG).show();
        		new GetTorrentTask().execute(searchString.replace(' ', '.'));
        	}
        });
        return v;
    }
    
    class GetTorrentTask extends AsyncTask<String, Void, Void> {
    	String filename = "download";
		// All static variables
    	public List<TorrentSearchResult> searchResults;

	    @Override
	    protected Void doInBackground(String... params) {
	    	filename = params[0];
	    	searchResults = Helper.getJSONfromURL("http://fenopy.se/module/search/api.php?keyword="+ params[0] +"&sort=peer&format=json&limit=1");
	        return null;
	
	    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
		String torrentLink = "";
		for(int i = 0; i < searchResults.size(); i++)
		{
			torrentLink = searchResults.get(i).torrent;
		}
		
		DownloadManager dm = (DownloadManager) getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(torrentLink));
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/SparkleTV/torrents");
        dir.mkdirs();
        request.setDestinationUri(Uri.fromFile(dir));
        request.setTitle(filename +".torrent");
        long enqueue = dm.enqueue(request);
		Toast.makeText(getActivity(), torrentLink, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
}