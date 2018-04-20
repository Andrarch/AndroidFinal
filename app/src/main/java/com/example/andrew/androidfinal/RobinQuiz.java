package com.example.andrew.androidfinal;

/**
 * This class is responsible for first load of quiz layout
 * Written by Robin Shrestha
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.app.Dialog;
import android.content.Context;

import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.util.ArrayList;



public class RobinQuiz extends Activity {
    ListView listViewObject;

    ArrayList<String> quizLines = new ArrayList<String>();

    SearchDatabaseHelper searchDatabase;
    EditText javaEditText;
    Button multipleR, trueFalseR, numericR, infoButtonR, showList;
    SQLiteDatabase databaseDb;
    Cursor cursor;

    Button button;
    protected static final String ACTIVITY_NAME = "QuizActivity";



    TextView result;
    TextView questList;
    Context ctx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_quiz);
        infoButtonR = findViewById(R.id.otherR);
        multipleR = findViewById(R.id.multipleR);
        trueFalseR = findViewById(R.id.trueFalseR);
        numericR = findViewById(R.id.numericR);
        Toolbar tbar = (Toolbar) findViewById(R.id.quiz_toolbar);
        showList = findViewById(R.id.questionList);
        setActionBar(tbar);

        result = findViewById(R.id.resultsR);
        questList = findViewById(R.id.questionList);

        ctx = this;

        infoButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(multipleR, "Robin Shrestha, Student Number: 040880427", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        multipleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, RobinMultipleChoice.class);
                startActivity(intent);
                Toast toast=Toast.makeText(RobinQuiz.this,"Multiple Quiz Selected",Toast.LENGTH_LONG);
                toast.show();

            }
        });
        numericR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, RobinNumeric.class);
                startActivity(intent);
                Toast toast=Toast.makeText(RobinQuiz.this,"Numeric Quiz Selected",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        trueFalseR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, trueFalseRobin.class);
                startActivity(intent);
                Toast toast=Toast.makeText(RobinQuiz.this,"True/False Selected",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.activity_robin_list_view);

                final Intent intent = new Intent(RobinQuiz.this, RobinListView.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked True False");
                Toast toast=Toast.makeText(RobinQuiz.this,"Question List Selected",Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}


