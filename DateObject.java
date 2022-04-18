package com.example.calendartutorial;

import java.util.ArrayList;

public class DateObject {
    int day;
    int month;
    int year;
    ArrayList<String> events;
    ArrayList<String> reminders;
    String backgroundDrawableName;
    int backgroundPictureID;

    public DateObject(String date){
        this.day = getDay(date);
        this.month = getMonth(date);
        this.year = getYear(date);

        this.backgroundDrawableName = getBackgroundDrawableName();
        this.backgroundPictureID = R.drawable.corgi1;
        //^testing v
        //this.backgroundPictureID = getBackgroundID(backgroundDrawableName);
    }

    //TODO get background Image ID() - currently hard-coded
    private int getBackgroundID(String Name) {
        return R.drawable.corgi1;
    }

    private String getBackgroundDrawableName() {
        int imageNo = (int)Math.floor(Math.random()*3);
        return "corgi" + imageNo;
    };


    public static void main(String[] args) {

    }

    // dd/mm/yyyy
    private int getDay(String date){
        String onlyDay = date.substring(0, 2);
        return Integer.parseInt(onlyDay);
    };
    private int getMonth(String date){
        String onlyMonth = date.substring(3, 5);
        return Integer.parseInt(onlyMonth);
    }
    private int getYear(String date){
        String onlyYear = date.substring(6);
        return Integer.parseInt(onlyYear);
    }

}
