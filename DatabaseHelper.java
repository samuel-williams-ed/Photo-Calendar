package com.example.calendartutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper.java ";
    public static final String DATES_TABLE = "NEW_DATES_TABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY = "DAY";
    public static final String COLUMN_MONTH = "MONTH";
    public static final String COLUMN_YEAR = "YEAR";
    public static final String COLUMN_BACKGROUND_PICTURE_ID = "BACKGROUND_PICTURE_ID";


    //constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, "my_new_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatorString = "CREATE TABLE " + DATES_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DAY + " INTEGER, " + COLUMN_MONTH + " INTEGER, " + COLUMN_YEAR + " INTEGER, " + COLUMN_BACKGROUND_PICTURE_ID + " INTEGER)";

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

}
