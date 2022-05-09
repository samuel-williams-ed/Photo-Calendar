package com.example.calendartutorial;

import android.util.Log;

public class EventObject {
    int id; //format: YYYYMMDD
    int day;
    int month;
    int year;
    String name;
    String location;
    String details;
    public static String TAG = "EventObject.java";

    //parameter format date: DD/MM/YYYY
    public EventObject(String date, String name){
        this.id = calcId(date);
        this.day = calcDay(date);
        this.month = calcMonth(date);
        this.year = calcYear(date);
        this.name = name;
        this.location = null;
        this.details = null;
    }

    public EventObject(String date, String name, String location){
        this.id = calcId(date);
        this.day = calcDay(date);
        this.month = calcMonth(date);
        this.year = calcYear(date);
        this.name = name;
        this.location = location;
        this.details = null;
    }

    public EventObject(String date, String name, String location, String details){
        this.id = calcId(date);
        this.day = calcDay(date);
        this.month = calcMonth(date);
        this.year = calcYear(date);
        this.name = name;
        this.location = location;
        this.details = details;
    }


    //setters
    private int calcId(String date){
        //return format: YYYYMMDD

        // if date already formatted
        Log.d(TAG, "calcId; date already formatted, parameter date = " + date);
        if(date.length() == 8){ return Integer.parseInt(date); }

        Log.d(TAG, "calcId, parameter date = " + date);
        String editedDate = date.substring(6, 10) + date.substring(3, 5) + date.substring(0, 2);

        Log.d(TAG, "calcId, edited date = " + editedDate);
        return Integer.parseInt(editedDate);
    }
    // required string format for 'date' = dd/mm/yyyy
    private int calcDay(String date){
        String onlyDay = date.substring(0, 2);
        return Integer.parseInt(onlyDay);
    };
    private int calcMonth(String date){
        String onlyMonth = date.substring(3, 5);
        return Integer.parseInt(onlyMonth);
    }
    private int calcYear(String date){
        String onlyYear = date.substring(6);
        return Integer.parseInt(onlyYear);
    }

    //getters
    public int getId(){
        return this.id;
    }
    public int getDay(){
        return this.day;
    }
    public int getMonth(){
        return this.month;
    }
    public int getYear(){
        return this.year;
    }
    public String getName() { return name; }
    public String getLocation(){ return location; }
    public String getDetails() { return details; }
}
