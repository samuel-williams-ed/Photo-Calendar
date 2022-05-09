package com.example.calendartutorial;

import android.util.Log;

import java.util.ArrayList;

public class DateObject {
    int id; //format: YYYYMMDD
    int day;
    int month;
    int year;
    ArrayList<String> events;
    ArrayList<String> reminders;
    String backgroundDrawableName;
    int backgroundPictureID;

    public static String TAG = "DateObject.java";

    //constructor
    public DateObject(String date){
        this.id = calcId(clean(date));
        this.day = calcDay(clean(date));
        this.month = calcMonth(clean(date));
        this.year = calcYear(clean(date));
        this.events = null;
        this.reminders = null;
        this.backgroundDrawableName = calcBackgroundDrawableName();
        this.backgroundPictureID = R.drawable.corgi1;
        //^testing v
        //this.backgroundPictureID = getBackgroundID(backgroundDrawableName);
    }

    private String clean(String date){

        if(date.length() == 9) {
            return "0" + date;
        }
        return date;
    }

    private int calcId(String date){
        //return format: YYYYMMDD
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

    //getters:
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
    public ArrayList<String> getEvents(){
        return this.events;
    }
    public ArrayList<String> getReminders(){
        return this.reminders;
    }
    public String getBackgroundDrawableName(){
        return this.backgroundDrawableName;
    }
    public int getBackgroundPictureID(){
        return this.backgroundPictureID;
    }

    //methods:
    //TODO get background Image ID() - currently hard-coded
    private int getBackgroundID(String Name) {
        return R.drawable.corgi1;
    }

    private String calcBackgroundDrawableName() {
        int imageNo = (int)Math.floor(Math.random()*3);
        return "corgi" + imageNo;
    };




    public static void main(String[] args) {

    }



}
