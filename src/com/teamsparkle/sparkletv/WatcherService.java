package com.teamsparkle.sparkletv;

import java.util.ArrayList;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.teamsparkle.sparkletv.helpers.RegexPatterns;

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
	private final ArrayList<String> patterns = new RegexPatterns().getPatterns();

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
		Toast.makeText(getApplicationContext(), "Watching: "+ android.os.Environment.getExternalStorageDirectory().toString() + "/" + FOLDER, Toast.LENGTH_SHORT).show();
		FileObserver observer = new FileObserver( "/storage/sdcard1/" + FOLDER) 
		{
	        @Override
	        public void onEvent(int event, String file) 
	        {
	        	boolean matched = false;
	            if(event == FileObserver.MOVED_TO)
	            { 
	                Log.d(TAG, "File moved [" + android.os.Environment.getExternalStorageDirectory().toString() + "/" + FOLDER + "/" + file + "]");
	                Log.d(TAG, "Patterns: "+ patterns.size());
	                Pattern p;
	                Matcher m;
	                for(String s : patterns)
	                {
	                	Log.d(TAG, s);
	                	p = Pattern.compile(s);
	            		m = p.matcher(file);
	            		if(m.find())
	            		{
	            			matched = true;
	            			Log.d(TAG, "Found match");
	            			
	            			if(m.group("seriesname") != null)
	            			{
	            				Log.d(TAG, "Series: "+ m.group("seriesname"));
	            			}
	            			if(m.group("seasonnumber") != null)
	            			{
	            				Log.d(TAG, "Season: "+ m.group("seasonnumber"));
	            			}
	            			if(m.group("episodenumber") != null)
	            			{
	            				Log.d(TAG, "Episode: "+ m.group("episodenumber"));
	            			}
	            			if(m.group("episodenumberstart") != null)
	            			{
	            				Log.d(TAG, "Episode Start: "+ m.group("episodenumberstart"));
	            			}
	            			if(m.group("episodenumberend") != null)
	            			{
	            				Log.d(TAG, "Episode End: "+ m.group("episodenumberend"));
	            			}

		            		break;
	            		}
	                }
	                
	                if(!matched)
	                {
	                	Log.d(TAG, "No match found");
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
