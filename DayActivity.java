package com.example.calendartutorial;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class DayActivity extends AppCompatActivity implements editEventFragment.FragmentCloser {

    public static final String TAG = "DayActivity";
    TextView dateText;
    TextView reminderText;
    TextView eventsText;

    Button saveEventsText;
    Button homeButton;

    String userValues;
    String date;

    ImageView backgroundImg;
    ListView eventDataDisplay;
    DatabaseHelper dh;
    EventsDatabaseHelper edh;
    DateObject today_DO;
    EventObject newEvent;
    ArrayAdapter eventsArrayAdapter;

    //testing
//    EditText testText;
//    Button saveTestText;
    List<EventObject> events_list;
    Fragment eventFrag;


// TODO update events display to a scroll to fit multiple events

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

        Log.d(TAG, " DayActivity has been called");

        //Hookup view elements
        dateText = findViewById(R.id.dayLayout_dateText_TV);
        reminderText = findViewById(R.id.dayView_reminder1_TV);
        eventsText = findViewById(R.id.dayView_event1_TV);
        saveEventsText = findViewById(R.id.dayView_addEvent1_Bu);
        homeButton = findViewById(R.id.DayView_button_returnHome);
        backgroundImg = findViewById(R.id.dayView_backImage_IV);
        eventDataDisplay = findViewById(R.id.dayView_eventsDisplay_TV);

        //retrieve data from incoming intents
        Intent catchIncomingIntent = getIntent();
        String raw_date = catchIncomingIntent.getStringExtra("date");
        date = clean(raw_date);

        //initialise databases
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

        // save User Text to database
        saveEventsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    userValues = eventsText.getText().toString();
                } catch (Exception e) {
                    Log.d(TAG, "test: error getting user values from testText object");
                    e.printStackTrace();
                }
                Log.d(TAG, "Test button has been pressed");
                newEvent = new EventObject(date, userValues);
                boolean itsBeenAdded = edh.addEvent(newEvent);
                Log.d(TAG, "Database Insert = " + itsBeenAdded);

                //update UI
                displayEvents(today_DO);
                eventsText.setText("");
            }
        });

        eventDataDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //fragment pop-up
                String name = (String) parent.getItemAtPosition(position);
                EventObject databaseEvent = edh.getEventByNameAndDate(name, calcId(date));
                String databaseId = edh.getDatabaseIdFromEvent(databaseEvent);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                editEventFragment frag_editEvent = editEventFragment.newInstance(date, name, databaseId);
                ft.replace(R.id.dayLayout_placeholder, frag_editEvent);
                ft.commit();
                Log.d(TAG, "eventDataDisplaylistener() complete");
                //set other view elements as hidden
                dateText.setVisibility(View.GONE);
                reminderText.setVisibility(View.GONE);
                eventsText.setVisibility(View.GONE);
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
        Log.d(TAG, "Setting page data; ");
        dateText.setText(date);
        setBackgroundImage(backgroundImg, date);
        displayEvents(today_DO);
    }

    //TODO currently - dh.getEvents() is returning a list of Strings from database; only the column 'DATA' is being read from database by getEvents()
    //TODO change this to returning an Event object - so when selected by user we can open an EventView Activity, where edit etc.,

    // set the Adapter for eventDataDisplay ListView which displays the name of each event to the user in DayActivity
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


    @Override
    public void removeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        eventFrag = (Fragment) getSupportFragmentManager().findFragmentById(R.id.dayLayout_placeholder);
        ft.remove(eventFrag);
        ft.commit();
        setPageData(date);
    }

    public String calcId(String pDate){
        Log.d(TAG, "calcId: parameter date = " + pDate);
        String editedDate = pDate.substring(6, 10) + pDate.substring(3, 5) + pDate.substring(0, 2);

        Log.d(TAG, "calcId: edited date = " + editedDate);
        return editedDate;
    }
}





