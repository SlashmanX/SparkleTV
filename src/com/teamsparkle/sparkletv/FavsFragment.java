package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.Show;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

public class FavsFragment extends ListFragment {
    boolean isDualPane;
    int mCurCheckPosition = 0;
    public ShowDatabaseManager db;
	ArrayList<HashMap<String, String>> favShows = new ArrayList<HashMap<String, String>>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new ShowDatabaseManager(getActivity());
		updateFavList();


        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View scheduleFrame = getActivity().findViewById(R.id.schedule);
        isDualPane = scheduleFrame != null && scheduleFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (isDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            
            //showDetails(mCurCheckPosition);
        }
    }
    
    @Override public void onResume() {
    	super.onResume();
    	updateFavList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	int showid = Integer.parseInt(((TextView) v.findViewById(R.id.show_id)).getText().toString());
        showDetails(position, showid);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index, int id) {
        mCurCheckPosition = index;

        if (isDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            ScheduleFragment schedule = (ScheduleFragment)
                    getActivity().getFragmentManager().findFragmentById(R.id.schedule);
            if (schedule == null || schedule.getShowID() != id) {
                // Make new fragment to show this selection.
            	schedule = ScheduleFragment.newInstance(id);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.replace(R.id.schedule, schedule);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), ScheduleActivity.class);
            intent.putExtra("showid", id);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }
    
	public void updateFavList() {
		favShows = new ArrayList<HashMap<String, String>>();
		ArrayList<Show> tmp = db.getAllShows();
		for(int i = 0; i < tmp.size(); i++)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("showid", Integer.toString(tmp.get(i).getId()));
			map.put("name", tmp.get(i).getName());
			map.put("summary", tmp.get(i).getSummary());
			favShows.add(map);
		}
		
		ListAdapter listAdapter = new SimpleAdapter(getActivity(), favShows, R.layout.fav_show_list_item,
				new String[] { "name", "showid", "summary" }, 
				new int[] { R.id.fav_show_name, R.id.show_id, R.id.fav_show_summary});
        
        setListAdapter(listAdapter);
	}
}
