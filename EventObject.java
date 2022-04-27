package com.example.calendartutorial;

public class EventObject {
    int id; //format: YYYYMMDD
    int day;
    int month;
    int year;

    public EventObject(String date){
        this.id = calcId(date);
        this.day = calcDay(date);
        this.month = calcMonth(date);
        this.year = calcYear(date);
    }


    //setters
    private int calcId(String date){
        String editedDate = date.substring(6, 10) + date.substring(3,5) + date.substring(0, 2);
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

}
