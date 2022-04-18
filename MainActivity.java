package com.example.calendartutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TODO hook up view elements for clicks
        CalendarView calendarView = findViewById(R.id.calendarView_calenderWidget);

        //TODO click listeners
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //add 0 to months less than 10
                String month;
                if(i1 < 9){
                    month = "0" + (i1+1);
                } else {
                    month = Integer.toString((i1+1));
                }

                String date = i2 + "/" + month + "/" + i;
                Log.d(TAG, "onSelectedDayChange: " + date);

                Intent intent = new Intent(MainActivity.this, DayActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);

            }
        });
    }
}