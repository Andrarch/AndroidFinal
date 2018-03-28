package com.example.andrew.androidfinal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndrewTranspoDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("StopNumber");

            TextView text=findViewById(R.id.andrewOCDetailStopNum);
            text.setText(value);
        }

    }
}
