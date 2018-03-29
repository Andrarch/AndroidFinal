package com.example.andrew.androidfinal;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.design.widget.Snackbar;


public class RobinQuiz extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_quiz);
        button = findViewById(R.id.sendButton);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

        snackbar.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RobinQuiz.this, "I was clicked", Toast.LENGTH_LONG).show();

            }
        });
    }


}
