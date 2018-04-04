package com.example.andrew.androidfinal;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BilalClinic extends Activity {

    Button b;
    Button resetB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilal_clinic);

        b=findViewById(R.id.fillFormButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BilalClinic.this, "Transferring to form", Toast.LENGTH_SHORT).show();
            }
        });

        resetB=findViewById(R.id.resetButton);
        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BilalClinic.this, "Transferring to form", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
