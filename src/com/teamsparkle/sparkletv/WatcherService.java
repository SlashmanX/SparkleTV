package com.teamsparkle.sparkletv;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.model.Series;
import com.team.sparkle.sparkletv.R;
import com.teamsparkle.sparkletv.helpers.ParsedEpisode;
import com.teamsparkle.sparkletv.helpers.RegexPatterns;
import com.teamsparkle.sparkletv.helpers.ShowDatabaseManager;

@SuppressLint("NewApi")
public class WatcherService extends Service
{
	private static final String TAG = "WatcherService";
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
		Intent sintent = new Intent(this, SparkleTVActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, sintent, 0);
		Notification noti = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle("SparkleTV: Watching folder").setContentIntent(pIntent).build();
		startForeground(1, noti);
		final ShowDatabaseManager db = new ShowDatabaseManager(getApplicationContext());
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


		final TheTVDBApi tvdb = new TheTVDBApi("35A222B84DD0FA85");
		final String folderToWatch = sharedPrefs.getString("folderwatcher_preference", null);
		final String moveTo = sharedPrefs.getString("filemover_preference", null);
		
		// Indent city
		if(folderToWatch != null && moveTo != null)
		{
			Toast.makeText(getApplicationContext(), "Watching: "+ folderToWatch, Toast.LENGTH_SHORT).show();
			FileObserver observer = new FileObserver(folderToWatch) 
			{
		        @Override
		        public void onEvent(int event, String file) 
		        {
		            if(event == FileObserver.MOVED_TO)
		            { 
		            	String ext = file.substring(file.lastIndexOf('.') + 1);
		            	if(ext.equalsIgnoreCase("mkv") || ext.equalsIgnoreCase("avi") || ext.equalsIgnoreCase("mp4"))
		            	{
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
				                File oldFile = new File(folderToWatch +"/"+ file);
				                File newFileLoc = new File(moveTo +"/"+ pe.getShowName() +"/Season "+ Integer.parseInt(pe.getSeasonNumber()) + "/");
				                newFileLoc.mkdirs();
				                File newFileName = new File(newFileLoc.getAbsolutePath()+ "/" + String.format("%02d", Integer.parseInt(pe.getEpisodeNumber())) + " - "+ pe.getEpisodeName()+"."+ ext);
				                oldFile.renameTo(newFileName);
			                }
		            	}
		            
		            }
		        }
		    };
		    observer.startWatching(); // start the observer
		}
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
