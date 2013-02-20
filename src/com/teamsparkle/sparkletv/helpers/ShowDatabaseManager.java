package com.teamsparkle.sparkletv.helpers;

import java.util.ArrayList;

import org.pojava.datetime.DateTime;

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
        private final int DB_VERSION = 1; // the version of the database
 
        // the names for our database columns
        private final String TABLE_NAME                 = "shows";
        private final String TABLE_ROW_ID               = "id";
        private final String TABLE_ROW_SHOW_NAME        = "showName";
        private final String TABLE_ROW_LAST_EP_DATE     = "lastEpisodeDate";
        private final String TABLE_ROW_NEXT_EP_DATE     = "nextEpisodeDate";
        private final String TABLE_ROW_LAST_EP_NAME     = "lastEpisodeName";
        private final String TABLE_ROW_NEXT_EP_NAME     = "nextEpisodeName";
        private final String TABLE_ROW_CURRENT_SEASON   = "currentSeasonNo";
        private final String TABLE_ROW_CURRENT_EPISODE  = "currentEpisodeNo";
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
                        // this is a key value pair holder used by android's SQLite functions
                        ContentValues values = new ContentValues();
                 
                        // this is how you add a value to a ContentValues object
                        // we are passing in a key string and a value string each time
                        values.put(TABLE_ROW_ID, show.getId());
                        values.put(TABLE_ROW_SHOW_NAME, show.getName());
                        /*
                        values.put(TABLE_ROW_LAST_EP_DATE, show.getLastEpisodeDate().toString("yyyy-MM-dd"));
                        values.put(TABLE_ROW_NEXT_EP_DATE, show.getNextEpisodeDate().toString("yyyy-MM-dd"));
                        values.put(TABLE_ROW_LAST_EP_NAME, show.getLastEpisodeName());
                        values.put(TABLE_ROW_NEXT_EP_NAME, show.getNextEpisodeName());
                        values.put(TABLE_ROW_CURRENT_SEASON, show.getCurrentSeason());
                        values.put(TABLE_ROW_CURRENT_EPISODE, show.getCurrentEpisode());
                        */
                 
                        // ask the database object to insert the new data 
                        try
                        {
                                db.insert(TABLE_NAME, null, values);
                        }
                        catch(Exception e)
                        {
                                Log.e("DB ERROR", e.toString()); // prints the error message to the log
                                e.printStackTrace(); // prints the stack trace to the log
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
                    // this method call is spread out over seven lines as a personal preference
                        cursor = db.query
                        (
                                        TABLE_NAME,
                                        new String[] { TABLE_ROW_ID, TABLE_ROW_SHOW_NAME, TABLE_ROW_LAST_EP_DATE, TABLE_ROW_NEXT_EP_DATE, TABLE_ROW_LAST_EP_NAME, TABLE_ROW_NEXT_EP_NAME, TABLE_ROW_CURRENT_SEASON, TABLE_ROW_CURRENT_EPISODE },
                                        TABLE_ROW_ID + "=" + ID,
                                        null, null, null, null, null
                        );
                        cursor.moveToNext();
                        do
                        {
                                temp.setId(cursor.getInt(0));
                                temp.setName(cursor.getString(1));
                                temp.setLastEpisodeDate(new DateTime(cursor.getString(2)));
                                temp.setNextEpisodeDate(new DateTime(cursor.getString(3)));
                                temp.setLastEpisodeName(cursor.getString(4));
                                temp.setNextEpisodeName(cursor.getString(5));
                                temp.setCurrentSeason(cursor.getInt(6));
                                temp.setCurrentEpisode(cursor.getInt(7));
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
        
        public void clearDatabase()
        {
                db.execSQL("DELETE FROM " + TABLE_NAME);
                db.execSQL("DELETE FROM sqlite_sequence where name='"+ TABLE_NAME +"'"); 
                db.execSQL("VACUUM");
        }
        
        public int showExists(Show show) {
                   Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+ TABLE_ROW_ID +"="+ show.getId(), null);
                   
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
                        cursor = db.query(
                                        TABLE_NAME,
                                        new String[] { TABLE_ROW_ID, TABLE_ROW_SHOW_NAME, TABLE_ROW_LAST_EP_DATE, TABLE_ROW_NEXT_EP_DATE, TABLE_ROW_LAST_EP_NAME, TABLE_ROW_NEXT_EP_NAME, TABLE_ROW_CURRENT_SEASON, TABLE_ROW_CURRENT_EPISODE },
                                        null, null, null, null, null
                        );
         
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
                                        temp.setLastEpisodeDate(new DateTime(cursor.getString(2)));
                                        temp.setNextEpisodeDate(new DateTime(cursor.getString(3)));
                                        temp.setLastEpisodeName(cursor.getString(4));
                                        temp.setNextEpisodeName(cursor.getString(5));
                                        temp.setCurrentSeason(cursor.getInt(6));
                                        temp.setCurrentEpisode(cursor.getInt(7));
                                        
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
                        String newTableQueryString =    
                                "CREATE TABLE " +
                                TABLE_NAME +
                                " (" +
                                TABLE_ROW_ID + " integer primary key autoincrement not null," +
                                TABLE_ROW_SHOW_NAME + " text," +
                                TABLE_ROW_LAST_EP_DATE + " text," +
                                TABLE_ROW_NEXT_EP_DATE + " text," +
                                TABLE_ROW_LAST_EP_NAME + " text," +
                                TABLE_ROW_NEXT_EP_NAME + " text," +
                                TABLE_ROW_CURRENT_SEASON + " integer," +
                                TABLE_ROW_CURRENT_EPISODE + " integer" +
                                ");";
                 
                        // execute the query string to the database.
                        db.execSQL(newTableQueryString);
                        
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        
                        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME +"");

                        
                }
        }

}