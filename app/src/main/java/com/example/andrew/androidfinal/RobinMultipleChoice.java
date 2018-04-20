package com.example.andrew.androidfinal;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RobinMultipleChoice extends AppCompatActivity {

    Button add;
    EditText addQuest;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    EditText correctAnswer;
    RobinMultiChoiceDBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_multiple_choice);
        add = findViewById(R.id.choiceSubmitR);
        addQuest = findViewById(R.id.multipleQuestionR);
        answer1 = findViewById(R.id.multipleC1R);
        answer2 = findViewById(R.id.multipleC2R);
        answer3 = findViewById(R.id.multipleC3R);
        answer4 = findViewById(R.id.multipleC4R);
        correctAnswer = findViewById(R.id.answerCaptionR);
        dbHelper = new RobinMultiChoiceDBHelper(this);
        db = dbHelper.getWritableDatabase();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put(RobinMultiChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE, 1);
                cv.put(RobinMultiChoiceContract.QuestionTable.COLUMN_QUESTION, String.valueOf(addQuest.getText()));
                cv.put(RobinMultiChoiceContract.QuestionTable.COLUMN_OPTION1, String.valueOf(answer1.getText()));
                cv.put(RobinMultiChoiceContract.QuestionTable.COLUMN_OPTION2, String.valueOf(answer2.getText()));
                cv.put(RobinMultiChoiceContract.QuestionTable.COLUMN_OPTION3, String.valueOf(answer3.getText()));
                cv.put(RobinMultiChoiceContract.QuestionTable.COLUMN_OPTION4, String.valueOf(answer4.getText()));
                db.insert(RobinMultiChoiceContract.QuestionTable.TABLE_NAME, "nullColumnName", cv);

                Snackbar snackbar = Snackbar.make(add, "Your New Question Added", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}