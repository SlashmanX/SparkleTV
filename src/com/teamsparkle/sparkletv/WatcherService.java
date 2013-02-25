package com.teamsparkle.sparkletv;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

public class WatcherService extends Service
{
	private static final String TAG = "WatcherService";
	private static final String FOLDER = "testing"; // folder to watch on sd card

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
		FileObserver observer = new FileObserver(android.os.Environment.getExternalStorageDirectory().toString() + "/" + FOLDER) 
		{
	        @Override
	        public void onEvent(int event, String file) 
	        {
	            if(event == FileObserver.MOVED_TO)
	            { 
	                Log.d(TAG, "File moved [" + android.os.Environment.getExternalStorageDirectory().toString() + "/" + FOLDER + "/" + file + "]");
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
