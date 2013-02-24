package com.teamsparkle.sparkletv;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ScheduleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            /*
             * You can call finish() here if you don't want to support
             * landscape orientation in mobile devices
             */
            //finish();
        	ScheduleFragment schedule = new ScheduleFragment();
        	schedule.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, schedule).commit();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            ScheduleFragment schedule = new ScheduleFragment();
            schedule.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, schedule).commit();
        }
    }
    
}