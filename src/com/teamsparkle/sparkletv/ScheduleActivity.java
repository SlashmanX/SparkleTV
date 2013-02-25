package com.teamsparkle.sparkletv;

import com.team.sparkle.sparkletv.R;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

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
    
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
            	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
        }
        return true;
    }
	
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
}