package com.example.andrew.androidfinal;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RobinNumeric extends AppCompatActivity {
    Button buttonNumericAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_numeric);
        buttonNumericAdd=(findViewById(R.id.numericSubmitR));

        buttonNumericAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make( buttonNumericAdd, "Your New Numeric Question is submitted.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}
