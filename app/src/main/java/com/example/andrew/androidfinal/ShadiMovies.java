package com.example.andrew.androidfinal;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class ShadiMovies extends Activity {
    ProgressBar progressBar;
    ListView listView;
    EditText editText;
    Button button;
    ArrayList<String> arrayListMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadi_movies);
        final ContentValues cv=new ContentValues();
        arrayListMovies = new ArrayList<>();
        progressBar = findViewById(R.id.progressBarId);
        button=findViewById(R.id.buttonAddId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Message is empty", Toast.LENGTH_LONG).show();
                } else {
                    String str = editText.getText().toString();
                    arrayListMovies.add(str);
                    //cv.put(cdh.KEY_MESSAGE, str);
                    //db.insert(cdh.TABLE_NAME, "null",cv);
                    //listViewChat.setAdapter(messageAdapter);
                   // messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                    editText.setText("");
                }


            }



        });
        editText=findViewById(R.id.editTextMovieId);
        listView=findViewById(R.id.listViewId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Snackbar snackbar = Snackbar
                        .make(button, "www.journaldev.com", Snackbar.LENGTH_LONG);
                snackbar.show();


                Intent intent = new Intent(ShadiMovies.this, ListItemActivity.class);
                startActivity(intent);
            }

        });
        button=findViewById(R.id.buttonAddId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShadiMovies.this, "Button was clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
