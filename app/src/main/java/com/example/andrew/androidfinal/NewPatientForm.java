package com.example.andrew.androidfinal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class NewPatientForm extends Activity {

    Button resetB;
    EditText fn;
    EditText ln;
    EditText address;
    EditText birth;
    EditText phone;
    EditText healthCard;
    EditText visitReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_form);

        Button resetB = (Button) findViewById(R.id.resetButton);
        fn = findViewById(R.id.FirstName);
        ln = findViewById(R.id.LastName);
        address = findViewById(R.id.Address);
        birth = findViewById(R.id.BirthDate);
        phone = findViewById(R.id.PhoneNumber);
        healthCard = findViewById(R.id.HealthCard);
        visitReason = findViewById(R.id.VisitReason);


        resetB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fn.setText("");
                ln.setText("");
                address.setText("");
                birth.setText("");
                phone.setText("");
                healthCard.setText("");
                visitReason.setText("");
            }
        });

//        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // checkedId is the RadioButton selected
//            }
//        });


//        RadioButton rb1 = (RadioButton) findViewById(R.id.radioDoctor);
//        View.OnClickListener listener1 = new View.OnClickListener(){
//            public void onClick(View v) {
//                TextView radioDoc= (TextView) v.findViewById(R.id.aaaa);
//
//                radioDoc.setVisibility(View.VISIBLE);
//            }
//        };
//        rb1.setOnClickListener(listener1);
//
//        RadioButton rb2 = (RadioButton) findViewById(R.id.radioDoctor);
//        View.OnClickListener listener2 = new View.OnClickListener(){
//            public void onClick(View v) {
//                TextView radioDoc= (TextView) v.findViewById(R.id.bbbb);
//
//                radioDoc.setVisibility(View.VISIBLE);
//            }
//        };
//        rb2.setOnClickListener(listener2);
//
//        RadioButton rb3 = (RadioButton) findViewById(R.id.radioDoctor);
//        View.OnClickListener listener3 = new View.OnClickListener(){
//            public void onClick(View v) {
//                TextView radioDoc= (TextView) v.findViewById(R.id.cccc);
//
//                radioDoc.setVisibility(View.VISIBLE);
//            }
//        };
//        rb3.setOnClickListener(listener3);
    }
}
