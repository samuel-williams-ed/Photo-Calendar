package com.example.calendartutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

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

}
