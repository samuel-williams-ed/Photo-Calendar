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

    public static final String TAG = "EventsDatabaseHelper.java";
    public static final String EVENTS_TABLE = "EVENTS_TABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE_ID = "DATE_ID"; //format YYYYMMDD
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

    public boolean addEvent(EventObject event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE_ID, event.getId());
        cv.put(COLUMN_NAME, event.getName());
        cv.put(COLUMN_LOCATION, event.getLocation());
        cv.put(COLUMN_DATA, event.getDetails());

        long success = db.insert(EVENTS_TABLE, null, cv);
        db.close();
        if (success == -1) {
            return false;
        } else
            return true;
    }

    public boolean editEventName(EventObject event, String new_name) {
        long success = 0;
        String id = getDatabaseIdFromEvent(event);
        Log.d(TAG, "editEventName(): Params: event =" + event.toString() + ": new_name =" + new_name + ": id=" + id);
        if (id != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, new_name);
            success = db.update(EVENTS_TABLE, cv, "id=?", new String[]{id});
            db.close();
        }
        if (success > 0) {
            return true;
        } else {
            return false;
        }
    }

    //TODO check this is needed (retrieveFull() in EventsDBH)
    public List<DateObject> retrieveFull(String id) {
        List result = new ArrayList<>();
        String queryString = "Select * FROM " + EVENTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
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

    public EventObject getEventByDatabaseId(String database_id) {
        EventObject result;
        Log.d(TAG, "getEventByDatabaseId: string id = " + database_id);
        String queryString = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_ID + " = " + database_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String col_date = cursor.getString(1);
                String col_name = cursor.getString(2);
                String col_location = cursor.getString(3);
                String col_details = cursor.getString(4);

                Log.d(TAG, "getEventByDatabaseId() is creating a new EventObject...");
                result = new EventObject(col_date, col_name, col_location, col_details);
                return result;

            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getEventByDatabaseId: cursor returned no values!");
        }
        result = new EventObject("01/01/2001", "getEventByDatabaseId() Failed");
        return result;
    }

    //TODO check id and date being passed into getEventByIdAndDate
    public EventObject getEventByNameAndDate(String name, String date) {
        Log.d(TAG, "getEventByNameAndDate: param_name = " + name + ": param_date = " + date);
        EventObject result;
        String queryString = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_NAME + "=" + "\"" + name + "\"" + " AND " + COLUMN_DATE_ID + "=" + "\"" + date + "\"" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String col_date = cursor.getString(1);
                String col_name = cursor.getString(2);
                String col_location = cursor.getString(3);
                String col_details = cursor.getString(4);

                Log.d(TAG, "getEventByNameAndDate(): creating object with params; date =" + col_date + ": name =" + col_name);
                result = new EventObject(col_date, col_name, col_location, col_details);
                return result;

            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getEventByIdAndDate(): cursor returned no values!");
        }
        result = new EventObject("01/01/2001", "Loading Failed");
        return result;
    }

    public void deleteEvent(EventObject event) {
        String id = this.getDatabaseIdFromEvent(event);
        Log.d(TAG, "deleteEvent(): Params: event =" + event.toString() + ": id=" + id);
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + EVENTS_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst(); //needed or event doesn't delete...
        Log.d(TAG, "deleteEvent() successful.");
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

        public List<EventObject> getEventObjects(DateObject date){

            List<EventObject> result_list = new ArrayList<EventObject>();
            String query_id = Integer.toString(date.getId());
            //only selecting data column from row matching id
            String queryString = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_DATE_ID + " = " + query_id;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            Log.d(TAG, "getEventObjects(): query: " + queryString);
            try {
                if (cursor.moveToFirst()) {
                    Log.d(TAG, "getEvents() method has found a value from cursor.moveToFirst()");
                    do {
                        String read_date = Integer.toString(cursor.getInt(1));
                        String read_name = cursor.getString(2);
                        Log.d(TAG, "read_name = " + read_name + ": read_date = " + read_date);

                        EventObject readEvent = new EventObject(read_date, read_name);
                        Log.d(TAG, "EventObject readEvent = " + readEvent.getName() + ": " + readEvent.getId());
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

        public String getDatabaseIdFromEvent(EventObject event){
        String return_id;
        String id = Integer.toString(event.getId());
        String name = event.getName();

            String queryString = "SELECT " + COLUMN_ID + " FROM " + EVENTS_TABLE + " WHERE " + COLUMN_NAME + " = " + "\"" + name + "\"" + " AND " + COLUMN_DATE_ID + " = " + "\"" +  id + "\"" +  ";";
            Log.d(TAG, "getDatabaseIdFromEvent query string = " + queryString);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()){
                do{
                    return_id = cursor.getString(0);
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, " cursor returned no values! return_id = null!!");
                return_id = null;
            }
            db.close();
            Log.d(TAG, "getDatabaseIdFromEvent(): return_id = " + return_id);
            return return_id;
        }
    }


