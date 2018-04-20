package com.example.andrew.androidfinal;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);




        Button btnAndrewMain=findViewById(R.id.MainAndrewButton);
        btnAndrewMain.setOnClickListener((click)->{
            Intent intent = new Intent(MainActivity.this, AndrewTranspo.class);

            startActivity(intent);
        });
        Button btnShadiMain=findViewById(R.id.MainShadiButton);
        btnShadiMain.setOnClickListener((click)->{
            Intent intent = new Intent(MainActivity.this, ShadiMovies.class);

            startActivity(intent);
        });
        Button btnBilalMain=findViewById(R.id.MainBilalButton);
        btnBilalMain.setOnClickListener((click)->{
            Intent intent = new Intent(MainActivity.this, BilalClinic.class);

            startActivity(intent);
        });
        Button btnRobinMain=findViewById(R.id.MainRobinButton);
        btnRobinMain.setOnClickListener((click)->{
            Intent intent = new Intent(MainActivity.this, RobinQuiz.class);

            startActivity(intent);
        });

    }
    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.maintoolbar, m );
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        int id=mi.getItemId();
        Intent intent;
        switch (id){
            case R.id.action_andrew:
                Log.d("ToolBar", "option 1");
                intent= new Intent(MainActivity.this, AndrewTranspo.class);

                startActivity(intent);
                break;


            case R.id.action_shadi:
                Log.d("ToolBar", "option 2");
                intent = new Intent(MainActivity.this, ShadiMovies.class);

                startActivity(intent);

                break;
            case R.id.action_bilal:
                Log.d("ToolBar", "option 3");
                intent = new Intent(MainActivity.this, BilalClinic.class);

                startActivity(intent);

                break;
            case R.id.action_robin:
                Log.d("ToolBar", "option 4");
                intent = new Intent(MainActivity.this, RobinQuiz.class);

                startActivity(intent);
                break;
        }

        return true;
    }
}
