package com.example.andrew.androidfinal;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RobinListView extends Activity {
    protected static final String ACTIVITY_NAME = "Questions_List_View";
    ListView listView;
    ArrayAdapter questionListAdapter;
    RobinMultipleChoiceQuestion question;
    ArrayList<RobinMultipleChoiceQuestion> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_list_view);
        listView = findViewById(R.id.listViewR);
        question = new RobinMultipleChoiceQuestion();
        RobinMultiChoiceDBHelper dbHelper = new RobinMultiChoiceDBHelper(this);
        questions = dbHelper.getAllQuestions();
        questionListAdapter = new ArrayAdapter(RobinListView.this, android.R.layout.simple_list_item_multiple_choice, getQuestionTexts());
                listView.setAdapter(questionListAdapter);
    }
    public int getCount() {
        return questions.size();
    }
    public String[] getQuestionTexts() {
        String[] qText = new String[getCount()];
        for (int i = 0; i < getCount(); i++) {
            qText[i] = questions.get(i).getQuestion();
            Log.i("Array got ", qText[i]);
        }
    return qText;
}
}