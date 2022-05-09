package com.example.calendartutorial;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DayActivity extends AppCompatActivity {

    public static final String TAG = "DayActivity";
    TextView dateText;
    Button homeButton;
    ImageView backgroundImg;
    String userValues;
    ListView eventDataDisplay;
    DatabaseHelper dh;
    EventsDatabaseHelper edh;
    DateObject today_DO;
    EventObject newEvent;
    ArrayAdapter eventsArrayAdapter;

    //testing
    EditText testText;
    Button saveTestText;
    List<EventObject> events_list;

// TODO update events display to a scroll to fit multiple events

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

        Log.d(TAG, " DayActivity has been called");

        //Hookup view elements
        dateText = findViewById(R.id.dayLayout_dateText_TV);
        homeButton = findViewById(R.id.DayView_button_returnHome);
        backgroundImg = findViewById(R.id.dayView_backImage_IV);
        eventDataDisplay = findViewById(R.id.test_displayData_LV);

        //retrieve data from incoming intents
        Intent catchIncomingIntent = getIntent();
        String raw_date = catchIncomingIntent.getStringExtra("date");
        String date = clean(raw_date);

        //initialise database
        dh = new DatabaseHelper(DayActivity.this, "dates_database");
        edh = new EventsDatabaseHelper(DayActivity.this);
        try {
            today_DO = new DateObject(date);
        } catch (Exception e) {
            e.printStackTrace();
            today_DO = new DateObject("01/11/1993");
        }

        //set page data
        setPageData(date);


        //return home click listener
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // save Text to database
        saveTestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    userValues = testText.getText().toString();
                } catch (Exception e) {
                    Log.d(TAG, "test: error getting user values from testText object");
                    e.printStackTrace();
                }
                Log.d(TAG, "Test button has been pressed");

                newEvent = new EventObject(date, userValues);

                boolean itsBeenAdded = edh.addEvent(newEvent);
                Log.d(TAG, "Database Insert = " + itsBeenAdded);

                //update UI
                //TODO check displayEvents() is drawing from edh
                displayEvents(today_DO);
                testText.setText("");
            }
        });
    }


    // ####### DayActivity Methods ########

    private String clean(String raw_date) {
        if(raw_date.length() == 9) {
            return "0" + raw_date;
        }
        return raw_date;
    }

    private void setPageData(String date){
        //set page data
        dateText.setText(date);
        setBackgroundImage(backgroundImg, date);
        displayEvents(today_DO);

        //testing
        testText = findViewById(R.id.editText_test);
        saveTestText = findViewById(R.id.saveTestText);
    }

    //TODO currently - dh.getEvents() is returning a list of Strings from database; only the column 'DATA' is being read from database by getEvents()
    //TODO change this to returning an Event object - so when selected by user we can open an EventView Activity, where edit etc.,
    private void displayEvents(DateObject eventDate){

        events_list = edh.getEventObjects(eventDate);

        List<String> events_data_list = new ArrayList<>();

        for(EventObject event : events_list){
            events_data_list.add(event.getName());
        }

        eventsArrayAdapter = new ArrayAdapter(DayActivity.this, android.R.layout.simple_list_item_1, events_data_list);
        eventDataDisplay.setAdapter(eventsArrayAdapter);
    }


    private void setBackgroundImage(ImageView imageHolder, String date){

        int imageID = getImageID(date);

        Drawable image = getDrawable(R.drawable.corgi1);
        //^ testing v
        //Drawable image = getDrawable(imageID);
        imageHolder.setImageDrawable(image);

    }

    //TODO build getImageID()
    private int getImageID(String date) {
        
        
        return 1;
    }


}





