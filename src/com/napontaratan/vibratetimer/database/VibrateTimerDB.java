package com.napontaratan.vibratetimer.database;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import com.napontaratan.vibratetimer.model.VibrateTimer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Visual representation of the database
 * ---------------------------------------
 * | ID (INTEGER) |	 VibrateTimer (BLOB) |
 * |--------------|----------------------|
 * |	...		  |	     	...			 |
 * |--------------|----------------------|
 * 
 * Database tutorial:
 * http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */

public class VibrateTimerDB extends SQLiteOpenHelper {

	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "VibrateTimerDB";
 
    public VibrateTimerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ALARM_TABLE = "CREATE TABLE alarms ( " +
                "id INTEGER PRIMARY KEY, " + 
                "alarm BLOB )";
		
		db.execSQL(CREATE_ALARM_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS alarms");
        this.onCreate(db);
	}
	
	/* ============================================================================== */
	
    private static final String TABLE_NAME = "alarms";
    private static final String KEY_ID = "id";
    private static final String KEY_ALARM = "alarm";
    
    /**
     * Add a VibrateTimer to the database based on the ID
     * @author Napon, Paul, Amelia
     */
    public void addToDB(VibrateTimer vt) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
    	try {
    		values.put(KEY_ID, vt.getId());
    		values.put(KEY_ALARM, VibrateTimer.serialize(vt));
    		Log.d("db.addToDB()","adding an alarm to DB: " + vt);
    	} catch (Exception e) {
    		System.out.println("IOException caught in addToDB()");
    	}

    	db.insert(TABLE_NAME, null, values);
    	db.close(); 
    }
    
    /**
     * Retrieve all VibrateTimer objects in the database
     * @return List<VibrateTimer>
     * @author Napon, Paul, Amelia
     */
    public List<VibrateTimer> getAllVibrateTimers() {
    	
        List<VibrateTimer> result = new LinkedList<VibrateTimer>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VibrateTimer vt = null;
        
        if (cursor.moveToFirst()) {
            do {
                try {
           			vt = (VibrateTimer) VibrateTimer.deserialize(cursor.getBlob(1));
           		} catch (ClassNotFoundException e) {
           			Log.d("Exception", "ClassNotFoundException caught in getAllAlarmsFromDB()");
           		} catch (IOException e) {
           			Log.d("Exception", "IOException caught in getAllAlarmsFromDB()");
           		}
                
                result.add(vt);
            } while (cursor.moveToNext());
        }
        Log.d("getAllBooks()", "number of alarms: " + result.size());
        return result;
    }
    
    /**
     * Clears the database
     * @author Napon, Paul, Amelia
     */
    public void deleteAllFromDB(){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    /**
     * Remove a VibrateTimer entry from the database that matches the given id
     * @param VibrateTimer vt
     * @author Napon, Paul, Amelia
     */
	public void remove(VibrateTimer vt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID+" =?", new String[] { String.valueOf(vt.getId()) });
		Log.d("db.remove()", "deleting the vibrateTimer: " + vt.toString());
		db.close();
	}
}
