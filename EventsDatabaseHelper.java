package com.example.calendartutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EventsDatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "EventsDatabaseHelper.java ";
    public static final String EVENTS_TABLE = "EVENTS_TABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE_ID = "DATE_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_DATA = "DATA";

    //constructor
    public EventsDatabaseHelper(@Nullable Context context) {
            super(context, "events_database", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatorString = "CREATE TABLE " + EVENTS_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE_ID + " INTEGER, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_DATA + " TEXT);";

        Log.d(TAG, " eVENTSDatabaseHelper initialised - query String: " + creatorString);
        db.execSQL(creatorString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public boolean addEvent(EventObject event){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_DATE_ID, event.getId());
            cv.put(COLUMN_NAME, event.getName());
            cv.put(COLUMN_LOCATION, event.getLocation());
            cv.put(COLUMN_DATA, event.getDetails());

            long success = db.insert(EVENTS_TABLE, null, cv);
            db.close();
            if(success == -1 ){
                return false;
            } else
                return true;
        }

        //TODO check this is needed (retrieveFull() in EventsDBH)
        public List<DateObject> retrieveFull(String id){
            List result = new ArrayList<>();
            String queryString = "Select * FROM " + EVENTS_TABLE;
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


        public List<String> getEventData(DateObject date){
            List<String> result_list = new ArrayList<>();

            String query_id = Integer.toString(date.getId());
            //only selecting data column from row matching id
            String queryString = "SELECT " + COLUMN_DATA + " FROM " + EVENTS_TABLE + " WHERE " + COLUMN_DATE_ID + " = " + query_id;
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
            String queryString = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_DATE_ID + " = " + query_id;
            Log.d(TAG, "query string = " + queryString);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            try {
                if (cursor.moveToFirst()) {
                    Log.d(TAG, "getEvents() method has found a value from cursor.moveToFirst()");
                    do {

                        String read_date = Integer.toString(cursor.getInt(1));
                        Log.d(TAG, "read_date = " + read_date);
                        String read_name = cursor.getString(2);
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


