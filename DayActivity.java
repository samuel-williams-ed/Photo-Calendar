package com.example.calendartutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class DayActivity extends AppCompatActivity {

    public static final String TAG = "DayActivity: ";

    TextView dateText;
    Button homeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

        Log.d(TAG, " has been called");

        //Hookup view elements
        dateText = findViewById(R.id.dayView_dateText_TV);
        homeButton = findViewById(R.id.DayView_button_returnHome);

        //retrieve data from incoming intents
        Intent catchIncomingIntent = getIntent();
        String date = catchIncomingIntent.getStringExtra("date");

        //set page data
        dateText.setText(date);


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}





