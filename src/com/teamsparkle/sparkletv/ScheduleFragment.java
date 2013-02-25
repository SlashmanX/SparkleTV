package com.teamsparkle.sparkletv;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.omertron.tvrageapi.model.Episode;
import com.omertron.tvrageapi.model.EpisodeList;
import com.omertron.tvrageapi.model.EpisodeNumber;
import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.Show;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
			map.put("name", ep.getTitle());
			schedule.add(map);
		}
		
		ListAdapter listAdapter = new SimpleAdapter(getActivity(), schedule, R.layout.schedule_list_item,
				new String[] { "name", "epid" }, 
				new int[] { R.id.episode_name, R.id.episode_id});
        
        scheduleList.setAdapter(listAdapter);
        return v;
    }
}