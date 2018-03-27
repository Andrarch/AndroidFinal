package com.example.andrew.androidfinal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAndrewMain=findViewById(R.id.MainAndrewButton);
        btnAndrewMain.setOnClickListener((click)->{
            Intent intent = new Intent(MainActivity.this, AndrewTranspo.class);

            startActivity(intent);
        });


    }
}
