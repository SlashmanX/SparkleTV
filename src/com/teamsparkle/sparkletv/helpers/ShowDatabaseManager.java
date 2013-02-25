package com.teamsparkle.sparkletv.helpers;

import java.util.ArrayList;
import java.util.Map;

import org.pojava.datetime.DateTime;

import com.omertron.tvrageapi.model.Episode;
import com.omertron.tvrageapi.model.EpisodeList;
import com.omertron.tvrageapi.model.EpisodeNumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShowDatabaseManager {
        
        private SQLiteDatabase db; // a reference to the database manager class.
        private final String DB_NAME = "sparkletv"; // the name of our database
        private final int DB_VERSION = 3; // the version of the database
 
        // SHOW TABLE
        private final String SHOW_TABLE_NAME					= "shows";
        private final String SHOW_TABLE_ROW_ID					= "showid";
        private final String SHOW_TABLE_ROW_NAME				= "name";
        private final String SHOW_TABLE_ROW_LAST_AIRED_EP		= "lastAiredEpisode";
        private final String SHOW_TABLE_ROW_AIR_DAY				= "airDay";
        private final String SHOW_TABLE_ROW_AIR_TIME			= "airTime";
        private final String SHOW_TABLE_ROW_IS_RUNNING			= "isStillRunning";
        private final String SHOW_TABLE_ROW_GENRE				= "genre";
        private final String SHOW_TABLE_ROW_RUNTIME				= "runtime";
        private final String SHOW_TABLE_ROW_SUMMARY				= "summary";
        private final String SHOW_TABLE_ROW_NUM_SEASONS			= "numSeasons";
        
        
        // EPISODE TABLE
        private final String EPISODE_TABLE_NAME					= "episodes";
        private final String EPISODE_TABLE_ROW_ID				= "episodeid";
        private final String EPISODE_TABLE_ROW_SHOW_ID			= "showid";
        private final String EPISODE_TABLE_ROW_AIR_DATE			= "dateAired";
        private final String EPISODE_TABLE_ROW_SEASON			= "seasonNum";
        private final String EPISODE_TABLE_ROW_EPISODE			= "episodeNum";
        private final String EPISODE_TABLE_ROW_NAME				= "name";
        private final String EPISODE_TABLE_ROW_SUMMARY			= "summary";
        private final String EPISODE_TABLE_ROW_DOWNLOADED		= "hasBeenDownloaded";
        private Context context;
        private ShowSQLiteDBHelper helper;
 
    // TODO: write the constructor and methods for this class
        
        public ShowDatabaseManager(Context context)
        {
                this.context = context;
 
                // create or open the database

                Log.v("DBManager", "Start of constructor");
                this.helper = new ShowSQLiteDBHelper(this.context);
                this.db = helper.getWritableDatabase();
                
        }
        
        public void close()
        {
                this.helper.close();
        }
        
        public void open()
        {
                this.db = this.helper.getWritableDatabase();
        }



        public void addShow(Show show)
        {
                if(showExists(show) == -1)
                {
                        ContentValues values = new ContentValues();

                        values.put(SHOW_TABLE_ROW_ID, show.getId());
                        values.put(SHOW_TABLE_ROW_NAME, show.getName());
                        values.put(SHOW_TABLE_ROW_AIR_DAY, show.getAirDay());
                        values.put(SHOW_TABLE_ROW_AIR_TIME, show.getAirTime());
                        values.put(SHOW_TABLE_ROW_IS_RUNNING, show.isRunning());
                        values.put(SHOW_TABLE_ROW_GENRE, show.getGenre());
                        values.put(SHOW_TABLE_ROW_RUNTIME, show.getRunTime());
                        values.put(SHOW_TABLE_ROW_SUMMARY, show.getSummary());
                        values.put(SHOW_TABLE_ROW_NUM_SEASONS, show.getNumSeasons());
                        
                 
                        // ask the database object to insert the new data 
                        try
                        {
                                db.insert(SHOW_TABLE_NAME, null, values);
                        }
                        catch(Exception e)
                        {
                                Log.e("DB ERROR", e.toString()); // prints the error message to the log
                                e.printStackTrace(); // prints the stack trace to the log
                        }
                }
        }
        
        public void addEpisodeList(EpisodeList epList)
        {
        	for(Map.Entry<EpisodeNumber, Episode> m : epList.getEpisodeList().entrySet()) {
        		Episode ep = m.getValue();
                if(episodeExists(ep) == -1)
                {
                        ContentValues values = new ContentValues();

                        values.put(EPISODE_TABLE_ROW_ID, ep.getId());
                        values.put(EPISODE_TABLE_ROW_NAME, ep.getTitle());
                        values.put(EPISODE_TABLE_ROW_AIR_DATE, ep.getAirDate().toString());
                        values.put(EPISODE_TABLE_ROW_SEASON, ep.getSeason());
                        values.put(EPISODE_TABLE_ROW_EPISODE, ep.getEpisode());
                        values.put(EPISODE_TABLE_ROW_SUMMARY, ep.getSummary());
                        values.put(EPISODE_TABLE_ROW_DOWNLOADED, false);
                        values.put(EPISODE_TABLE_ROW_SHOW_ID, epList.getShowId());
                        
                 
                        // ask the database object to insert the new data 
                        try
                        {
                                db.insert(EPISODE_TABLE_NAME, null, values);
                        }
                        catch(Exception e)
                        {
                                Log.e("DB ERROR", e.toString()); // prints the error message to the log
                                e.printStackTrace(); // prints the stack trace to the log
                        }
                }
        	}
        }
        
        /*public void updateMatch(long rowID, Match match)
        {
                // this is a key value pair holder used by android's SQLite functions
                ContentValues values = new ContentValues();
                values.put(TABLE_ROW_HOME_TEAM, match.getHomeTeam());
                values.put(TABLE_ROW_AWAY_TEAM, match.getAwayTeam());
                values.put(TABLE_ROW_HOME_SCORE, match.getHomeScore());
                values.put(TABLE_ROW_AWAY_SCORE, match.getAwayScore());
                values.put(TABLE_ROW_MINUTE, match.getMinute());
                values.put(TABLE_ROW_TYPE, match.getType());
                values.put(TABLE_ROW_LEAGUE, match.getLeague());
         
                // ask the database object to update the database row of given rowID
                try 
                {
                        db.update(TABLE_NAME, values, TABLE_ROW_ID + "=" + rowID, null);
                }
                catch (Exception e)
                {
                        Log.e("DB Error", e.toString());
                        e.printStackTrace();
                }
        }*/
        
        public Show getShow(long ID)
        {
        	Show temp = new Show();
        	Cursor cursor = null;
         
            try
            {
                cursor = db.query
                (
                    SHOW_TABLE_NAME,
                    new String[] { SHOW_TABLE_ROW_ID, SHOW_TABLE_ROW_NAME, 
                    		SHOW_TABLE_ROW_LAST_AIRED_EP, SHOW_TABLE_ROW_AIR_DAY, 
                    		SHOW_TABLE_ROW_AIR_TIME, SHOW_TABLE_ROW_IS_RUNNING, 
                    		SHOW_TABLE_ROW_GENRE, SHOW_TABLE_ROW_RUNTIME,
                    		SHOW_TABLE_ROW_SUMMARY, SHOW_TABLE_ROW_NUM_SEASONS},
                    SHOW_TABLE_ROW_ID + "=" + ID,
                    null, null, null, null, null
                );
                cursor.moveToNext();
                do
                {
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    temp.setAirDay(cursor.getString(3));
                    temp.setAirTime(cursor.getString(4));
                    temp.setRunning(cursor.getInt(5) != 0);
                    temp.setGenre(cursor.getString(6));
                    temp.setRunTime(cursor.getInt(7));
                    temp.setSummary(cursor.getString(8));
                    temp.setNumSeasons(cursor.getInt(9));
                }
                // move the cursor's pointer up one position.
                while (cursor.moveToNext());
            }
            catch (SQLException e) 
            {
            	Log.e("DB ERROR", e.toString());
               	e.printStackTrace();
            }
            
            cursor.close();
     
            return temp;
        }
        
        public EpisodeList getEpisodeList(int showID) {
        	EpisodeList epList = new EpisodeList();
        	EpisodeNumber epNum = new EpisodeNumber();
        	epList.setShowId(showID);
        	Episode tmp = new Episode();
        	Cursor cursor = null;
        	
        	try
            {
                cursor = db.query
                (
                    EPISODE_TABLE_NAME,
                    new String[] { EPISODE_TABLE_ROW_NAME, 
                    		EPISODE_TABLE_ROW_AIR_DATE, EPISODE_TABLE_ROW_SEASON, 
                    		EPISODE_TABLE_ROW_EPISODE, EPISODE_TABLE_ROW_SUMMARY, 
                    		EPISODE_TABLE_ROW_DOWNLOADED},
                    		EPISODE_TABLE_ROW_SHOW_ID + "=" + showID,
                    null, null, null, null, null
                );
                cursor.moveToNext();
                do
                {
                	tmp = new Episode();
                    tmp.setTitle(cursor.getString(0));
                    tmp.setAirDate(cursor.getString(1));
                    epNum = new EpisodeNumber();
                    epNum.setSeason(cursor.getInt(2));
                    epNum.setEpisode(cursor.getInt(3));
                    tmp.setEpisodeNumber(epNum);
                    tmp.setSummary(cursor.getString(4));
                    tmp.setDownloaded(cursor.getInt(5) == 1);
                    
                    epList.addEpisode(tmp);
                }
                // move the cursor's pointer up one position.
                while (cursor.moveToNext());
            }
            catch (SQLException e) 
            {
            	Log.e("DB ERROR", e.toString());
               	e.printStackTrace();
            }
            
            cursor.close();
        	
        	return epList;
        }
        
        public void clearDatabase()
        {
                db.execSQL("DELETE FROM " + SHOW_TABLE_NAME);
                db.execSQL("DELETE FROM sqlite_sequence where name='"+ SHOW_TABLE_NAME +"'"); 
                db.execSQL("VACUUM");
        }
        
        public int showExists(Show show) {
        	Cursor cursor = db.rawQuery("SELECT * FROM "+ SHOW_TABLE_NAME +" WHERE "+ SHOW_TABLE_ROW_ID +"="+ show.getId(), null);
           
        	boolean exists = (cursor.getCount() > 0);
           
           
        	int pos = -1;
           
        	if(exists)
        	{
        		cursor.moveToNext();
        		pos = cursor.getInt(0);
        	}
           
        	cursor.close();
           
        	return pos;
        }
        
        public int episodeExists(Episode ep) {
        	Cursor cursor = db.rawQuery("SELECT * FROM "+ EPISODE_TABLE_NAME +" WHERE "+ EPISODE_TABLE_ROW_ID +"="+ ep.getId(), null);
           
        	boolean exists = (cursor.getCount() > 0);
           
           
        	int pos = -1;
           
        	if(exists)
        	{
        		cursor.moveToNext();
        		pos = cursor.getInt(0);
        	}
           
        	cursor.close();
           
        	return pos;
        }
        
        public ArrayList<Show> getAllShows()
        {
                // create an ArrayList that will hold all of the data collected from
                // the database.
                ArrayList<Show> shows = new ArrayList<Show>();
         
                // this is a database call that creates a "cursor" object.
                // the cursor object store the information collected from the
                // database and is used to iterate through the data.
                Cursor cursor;
         
                try
                {
                        // ask the database object to create the cursor.
                	// TODO: Change to RAW query to use JOIN
                    cursor = db.rawQuery("SELECT * FROM "+ SHOW_TABLE_NAME, null);
     
                    // move the cursor's pointer to position zero.
                    cursor.moveToFirst();
     
                    // if there is data after the current cursor position, add it
                    // to the ArrayList.
                    if (!cursor.isAfterLast())
                    {
                            do
                            {
                                Show temp = new Show();
 
                                temp.setId(cursor.getInt(0));
                                temp.setName(cursor.getString(1));
                                temp.setAirDay(cursor.getString(3));
                                temp.setAirTime(cursor.getString(4));
                                temp.setRunning(cursor.getInt(5) != 0);
                                temp.setGenre(cursor.getString(6));
                                temp.setRunTime(cursor.getInt(7));
                                temp.setSummary(cursor.getString(8));
                                temp.setNumSeasons(cursor.getInt(9));
                                
                                shows.add(temp);
                            }
                            // move the cursor's pointer up one position.
                            while (cursor.moveToNext());
                    }
                }
                catch (SQLException e)
                {
                        Log.e("DB Error", e.toString());
                        e.printStackTrace();
                }
         
                // return the ArrayList that holds the data collected from
                // the database.
                return shows;
        }
 
        // the beginnings our SQLiteOpenHelper class
        private class ShowSQLiteDBHelper extends SQLiteOpenHelper
        {

            public ShowSQLiteDBHelper(Context context) {
                    
                    super(context, DB_NAME, null, DB_VERSION);

                    Log.v("DBHelper", "End of constructor");
                    // TODO Auto-generated constructor stub
            }
                
                @Override
	        public synchronized void close() {
	            if(db != null){
	                db.close();
	                super.close();
	            }   
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {
	                String newShowTableQueryString =    
	                        "CREATE TABLE " +
	                        SHOW_TABLE_NAME +
	                        " (" +
	                        SHOW_TABLE_ROW_ID + " integer primary key not null," +
	                        SHOW_TABLE_ROW_NAME + " text," +
	                        SHOW_TABLE_ROW_LAST_AIRED_EP + " integer," +
	                        SHOW_TABLE_ROW_AIR_DAY + " text," +
	                        SHOW_TABLE_ROW_AIR_TIME + " text," +
	                        SHOW_TABLE_ROW_IS_RUNNING + " int," +
	                        SHOW_TABLE_ROW_GENRE + " text," +
	                        SHOW_TABLE_ROW_RUNTIME + " integer," +
	                        SHOW_TABLE_ROW_SUMMARY + " text," +
	                        SHOW_TABLE_ROW_NUM_SEASONS + " integer" +
	                        ");";
	                
	                String newEpisodeTableQueryString =    
	                        "CREATE TABLE " +
	                        EPISODE_TABLE_NAME +
	                        " (" +
	                        EPISODE_TABLE_ROW_ID + " integer primary key not null," +
	                        EPISODE_TABLE_ROW_SHOW_ID + " integer," +
	                        EPISODE_TABLE_ROW_NAME + " text," +
	                        EPISODE_TABLE_ROW_AIR_DATE + " text," +
	                        EPISODE_TABLE_ROW_SEASON + " integer," +
	                        EPISODE_TABLE_ROW_EPISODE + " integer," +
	                        EPISODE_TABLE_ROW_SUMMARY + " text," +
	                        EPISODE_TABLE_ROW_DOWNLOADED + " integer," +
	                        "FOREIGN KEY("+ EPISODE_TABLE_ROW_SHOW_ID +") REFERENCES "+ SHOW_TABLE_NAME +"("+SHOW_TABLE_ROW_ID+"));";
	         
	                // execute the query string to the database.
	                db.execSQL(newShowTableQueryString);
	                db.execSQL(newEpisodeTableQueryString);
	                
	        }
	
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	                
	                db.execSQL("DROP TABLE IF EXISTS "+ SHOW_TABLE_NAME +"");
	                db.execSQL("DROP TABLE IF EXISTS "+ EPISODE_TABLE_NAME +"");
	                onCreate(db);
	
	                
	        }
     }

}