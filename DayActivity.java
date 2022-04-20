package com.example.calendartutorial;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class DayActivity extends AppCompatActivity {

    public static final String TAG = "DayActivity: ";

    TextView dateText;
    Button homeButton;
    ImageView backgroundImg;
    //testing
    EditText testText;
    Button saveTestText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

        Log.d(TAG, " has been called");

        //Hookup view elements
        dateText = findViewById(R.id.dayLayout_dateText_TV);
        homeButton = findViewById(R.id.DayView_button_returnHome);
        backgroundImg = findViewById(R.id.dayView_backImage_IV);

        //retrieve data from incoming intents
        Intent catchIncomingIntent = getIntent();
        String date = catchIncomingIntent.getStringExtra("date");

        //set page data
        dateText.setText(date);
        setBackgroundImage(backgroundImg, date);
        loadDateObject(date);


        //return home
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //testing
        testText = findViewById(R.id.editText_test);
        saveTestText = findViewById(R.id.saveTestText);

        saveTestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saveFile(testText, "test.txt");

                DateObject today;

                try {
                    today = new DateObject(date);
                } catch (Exception e) {
                    e.printStackTrace();
                    today = new DateObject("01/11/1993");
                }
                Log.d(TAG, "Test button has been pressed");

                DatabaseHelper dh = new DatabaseHelper(DayActivity.this);
                boolean itsBeenAdded = dh.add(today);
                Log.d(TAG, "Database Insert = " + itsBeenAdded);
            }
        });
    }

    public void saveFile(EditText rawData, String fileName) {
        FileOutputStream my_output_stream = null;
        String my_data = rawData.getText().toString();

        try {
            my_output_stream = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter my_writer = new OutputStreamWriter(my_output_stream);
            my_writer.write(my_data);
            my_writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "saveFile() completed successfully.");
    }

    //TODO build readFile()
    public String readFile(String fileName){

        return "no files yet";
    }

    private void loadDateObject(String date) {
        if (checkDateExists(date)){ return; }
        DateObject today = new DateObject(date);


    }

    //TODO build checkDateExists(), return boolean whether date exists in database
    private boolean checkDateExists(String date) {
        return false;
    }

    // ####### DayActivity Methods ########

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





