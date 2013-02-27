package com.teamsparkle.sparkletv;

import java.util.ArrayList;
import java.util.List;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.model.Series;
import com.teamsparkle.sparkletv.helpers.ParsedEpisode;
import com.teamsparkle.sparkletv.helpers.RegexPatterns;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class WatcherService extends Service
{
	private static final String TAG = "WatcherService";
	private static final String FOLDER = "testing"; // folder to watch on sd card
	private RegexPatterns regex = new RegexPatterns();

	String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
	

	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.d(TAG, "Service CREATED");
	}

	@Override
	public void onStart(Intent intent, int startId) 
	{
		Log.d(TAG, "Service STARTED");

		final ShowDatabaseManager db = new ShowDatabaseManager(getApplicationContext());

		final TheTVDBApi tvdb = new TheTVDBApi("35A222B84DD0FA85");
		FileObserver observer = new FileObserver( "/storage/sdcard1/" + FOLDER) 
		{
	        @Override
	        public void onEvent(int event, String file) 
	        {
	            if(event == FileObserver.MOVED_TO)
	            { 
	                Log.d(TAG, "File moved [" + android.os.Environment.getExternalStorageDirectory().toString() + "/" + FOLDER + "/" + file + "]");
	                
	                ParsedEpisode pe = regex.parseEpisode(file);
	                
	                if(pe != null)
	                {
	                	
	                	// TODO: Update database to be able to use fulltext search to check show names
	                	String searchStr = pe.getShowName().replaceAll("[^A-Za-z0-9]", " ");
	                	List<Series> searchResults = tvdb.searchSeries(searchStr, "en");
	                	for(Series s : searchResults){
	                		if(db.showExists(Integer.parseInt(s.getId())))
	                		{
	                			pe.setShowName(s.getSeriesName());
	                			break;
	                		}
	                	}
	                	pe.setEpisodeName(db.getEpisodeName(Integer.parseInt(pe.getSeasonNumber()), Integer.parseInt(pe.getEpisodeNumber())));
		                Log.d("PARSED EPISODE", pe.toString());
	                }
	            
	            }
	        }
	    };
	    observer.startWatching(); // start the observer
	}   

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	@Override
	public void onDestroy() 
	{               
		super.onDestroy();
	}               
}
