package com.example.calendartutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper.java ";
    public static final String DATES_TABLE = "NEW_DATES_TABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULL_ID = "FULL_ID";
    public static final String COLUMN_DAY = "DAY";
    public static final String COLUMN_MONTH = "MONTH";
    public static final String COLUMN_YEAR = "YEAR";
    public static final String COLUMN_BACKGROUND_PICTURE_ID = "BACKGROUND_PICTURE_ID";
    public static final String COLUMN_DATA = "DATA";


    //constructor
    //name = "my_new_database"
    public DatabaseHelper(@Nullable Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatorString = "CREATE TABLE " + DATES_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_ID + " INTEGER, " +
                COLUMN_DAY + " INTEGER, " +
                COLUMN_MONTH + " INTEGER, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_BACKGROUND_PICTURE_ID + " INTEGER, " +
                COLUMN_DATA + " TEXT);";

        Log.d(TAG, " DatabaseHelper initialised - query String: " + creatorString);
        db.execSQL(creatorString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add(DateObject date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //shouldn't need as autoincrement
//        cv.put(COLUMN_ID, date.getId());
        cv.put(COLUMN_DAY, date.getDay());
        cv.put(COLUMN_MONTH, date.getMonth());
        cv.put(COLUMN_YEAR, date.getYear());
        cv.put(COLUMN_BACKGROUND_PICTURE_ID, date.getBackgroundPictureID());

        long success = db.insert(DATES_TABLE, null, cv);
        db.close();
        if(success == -1 ){
            return false;
        } else
            return true;
    }

    //TODO apply method retrieveFull() to display database data somewhere on screen
    public List<DateObject> retrieveFull(String id){
        List result = new ArrayList<>();
        String queryString = "Select * FROM " + DATES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                String col_id = cursor.getString(0);
                String col_day = cursor.getString(1);
                String col_month = cursor.getString(2);
                String col_year = cursor.getString(3);

                DateObject dateToAdd = new DateObject(col_id);
                result.add(dateToAdd);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, " cursor returned no values!");
        }



        return result;
    }

    public Boolean saveEvent(EventObject eventObject, String data){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FULL_ID, eventObject.getId());
        cv.put(COLUMN_DATA, data);

        Log.d(TAG, "Adding to Database: FULL_ID: " + eventObject.getId() + ", DATA: " + data);

        long success = db.insert(DATES_TABLE, null, cv);

        db.close();

        if(success == -1){
            return false;
        }
        return true;
    }


    public List<String> getEventData(DateObject date){
        List<String> result_list = new ArrayList<>();

        String query_id = Integer.toString(date.getId());
        //only selecting data column from row matching id
        String queryString = "SELECT " + COLUMN_DATA + " FROM " + DATES_TABLE + " WHERE " + COLUMN_FULL_ID + " = " + query_id;
        Log.d(TAG, "query string = " + queryString);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        try {
            if (cursor.moveToFirst()) {
                Log.d(TAG, "getEvents() method has found a value from cursor.moveToFirst()");
                do {

                    String pulled_data = cursor.getString(0);
                    result_list.add(pulled_data);

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "getEvents() method could not cursor.moveToFirst()");
            }
        } catch (Exception e){
            Log.d(TAG, "getEvents() has failed! ");
        }

        cursor.close();
        db.close();

        return result_list;
    }

    //TODO getEventObjects() - currently we can only save and retrieve date and 'data' columns, need a separate DatabaseHelper class to create eventObject database. Then store name, location, details.
    public List<EventObject> getEventObjects(DateObject date){
        List<EventObject> result_list = new ArrayList<EventObject>();

        String query_id = Integer.toString(date.getId());
        //only selecting data column from row matching id
        String queryString = "SELECT * FROM " + DATES_TABLE + " WHERE " + COLUMN_FULL_ID + " = " + query_id;
        Log.d(TAG, "query string = " + queryString);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        try {
            if (cursor.moveToFirst()) {
                Log.d(TAG, "getEvents() method has found a value from cursor.moveToFirst()");
                do {

                    String read_date = Integer.toString(cursor.getInt(1));
                    Log.d(TAG, "read_date = " + read_date);
                    String read_name = cursor.getString(6);
                    Log.d(TAG, "read_name = " + read_name);

                    EventObject readEvent = new EventObject(read_date, read_name);
                    Log.d(TAG, "EventObject readEvent = " + readEvent.getId() + ": " + readEvent.getName());
                    result_list.add(readEvent);

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "getEventObjects() method could not cursor.moveToFirst()");
            }
        } catch (Exception e){
            Log.d(TAG, "getEventObjects() has failed! ");
        }

        cursor.close();
        db.close();

        return result_list;
    }
}
