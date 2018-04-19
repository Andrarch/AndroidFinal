package com.example.andrew.androidfinal;
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


import java.util.ArrayList;


public class RobinQuiz extends Activity {
    ListView listViewObject;

    ArrayList<String> quizLines = new ArrayList<String>();

    SearchDatabaseHelper searchDatabase;
    EditText javaEditText;
    Button multipleR, trueFalseR, numericR, infoButtonR;
    SQLiteDatabase databaseDb;
    Cursor cursor;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_quiz);
        infoButtonR = findViewById(R.id.backR);
        multipleR = findViewById(R.id.multipleR);
        trueFalseR = findViewById(R.id.trueFalseR);
        numericR = findViewById(R.id.numericR);
        infoButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(multipleR, "google v zebra", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        multipleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, RobinMultipleChoice.class);
                startActivity(intent);

            }
        });
        numericR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, RobinNumeric.class);
                startActivity(intent);
                // startActivityForResult(intent,50);
            }
        });
        trueFalseR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, trueFalseRobin.class);
                startActivity(intent);
                // startActivityForResult(intent,50);
            }
        });

        //ctx = this;

        // TextView message = (TextView) result.findViewById(R.id.textView);

//        Snackbar snackbar = Snackbar.make(findViewById(R.id.textView), "Quiz data", Snackbar.LENGTH_LONG);

  //      snackbar.show();
//        snackbar.setAction(R.string.AndrewStopSearchSnackGo, (t) -> {
//            Intent intent = new Intent(RobinQuiz.this, RobinQuiz.class);
//            intent.putExtra("StopNumber", message.getText());
//            startActivity(intent);
//
//        });

//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
//        alertBuilder.setMessage("Stop Number: "+message.getText())
//                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(RobinQuiz.this, RobinQuiz.class);
//                        intent.putExtra("StopNumber", message.getText());
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton(R.string.Delete, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast toast = Toast.makeText(RobinQuiz.this, "Placeholder for Delete", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                });
//
//
//        AlertDialog alert = alertBuilder.create();
//        alert.show();

//    });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(RobinQuiz.this, "I was clicked", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }


    }
}