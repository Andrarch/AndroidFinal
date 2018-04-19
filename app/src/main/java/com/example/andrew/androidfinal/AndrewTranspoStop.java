package com.example.andrew.androidfinal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;

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
                builder.setMessage("By: Andrew Archibald \nActivity version 0.8 \nPress image > on the left for detailed information");



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
