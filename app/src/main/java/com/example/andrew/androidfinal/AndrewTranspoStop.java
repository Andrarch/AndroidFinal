package com.example.andrew.androidfinal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;

import android.view.View;


public class AndrewTranspoStop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo_stop);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AndrewHelp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fab.getContext());
                builder.setMessage("By: Andrew Archibald \n\nActivity version 0.8 \n\nPress image arrow on the left for detailed information");
                builder.setPositiveButton("Ok",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new AndrewTranspoStopFragment();
        Bundle data = new Bundle();
        data.putString("StopNumber", getIntent().getStringExtra("StopNumber"));

        fragment.setArguments(data);

        fragmentTransaction.add(R.id.andrewTranspoFragmentContainer, fragment);
        fragmentTransaction.commit();

    }

    }
