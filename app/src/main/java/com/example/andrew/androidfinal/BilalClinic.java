package com.example.andrew.androidfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * BilalClinic class to show current patients or add new ones
 */
public class BilalClinic extends Activity {

    Button b;
//    Button resetB;

    /**
     * adds functionality to buttons to access other activities
     * @param savedInstanceState loads after reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilal_clinic);

        b = findViewById(R.id.newPatientButton);
        b.setOnClickListener((t) -> {

            Toast.makeText(BilalClinic.this, "Transferring to form", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BilalClinic.this, NewPatientForm.class);

            startActivity(intent);

        });




    }
}
