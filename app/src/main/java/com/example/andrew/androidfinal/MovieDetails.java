package com.example.andrew.androidfinal;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MovieDetails extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getBundleExtra("MovieInfo");

        //start a FragmentTransaction to add a fragment to the FrameLayout
        Movie1Fragment myFragment = new Movie1Fragment();
        myFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.movie_Framelayout, myFragment).commit();
    }

}
