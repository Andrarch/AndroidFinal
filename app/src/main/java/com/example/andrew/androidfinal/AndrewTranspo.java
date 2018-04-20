package com.example.andrew.androidfinal;

import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.support.design.widget.Snackbar;

/**
 * Start Activity for Andrew's OCtranspo app
 */
public class AndrewTranspo extends AppCompatActivity {
    ListView javaListView;
    Button javaInfoButton;
    ArrayList<String> javaStop = new ArrayList<String>();
    EditText javaText;
    SearchDatabaseHelper databaseHelp; // uses SearchDatabaseHelper to save/load stops
    SearchAdapter transpoAdapter; //adapter that updates the ListView
    SQLiteDatabase database; // Connects to the database that contains the saved stops
    Cursor cursor; //scans through the database data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo);
        // This section connects to the database and loads a query into the cursor
        databaseHelp = new SearchDatabaseHelper(this);
        database = databaseHelp.getWritableDatabase();
        cursor = database.rawQuery("SELECT " + SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO + " FROM " + SearchDatabaseHelper.getTableNameOctranspo(), new String[]{});
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO);
        //The data has been loaded into the cursor
        //Look through the cursor and add data from the database to the javaStop array - to show the data in the listview
        while (!cursor.isAfterLast()) {
            Log.i("Andrew_OCTranspo", "SQL Message:" + cursor.getString(cursor.getColumnIndex(SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO)));
            javaStop.add(cursor.getString(column));
            cursor.moveToNext();
        }

        Log.i("Andrew_OCTranspo", "Cursor’s  column count =" + cursor.getColumnCount());
        //Connect to the floating action button - based onCST2335 – Toolbars & Dialogs Lab 8

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AndrewHelpMain);
        fab.setOnClickListener((t)-> {
            //When you click the floating action button it adds a custom help dialog - CST2335 – Graphical Interface Programming Lab 3

            AlertDialog.Builder builder = new AlertDialog.Builder(fab.getContext());
                builder.setMessage("By: Andrew Archibald \n\nActivity version 0.8 \n\nPress image arrow on the left for detailed  stop information \n\nPress X image on right to delete the stop\n\nUse the input box to enter a stop number\n\nUse get info button to search information input into text box");
                builder.setPositiveButton("Ok",null);
                AlertDialog alert = builder.create();
                alert.show();

        });


        javaListView = (ListView) findViewById(R.id.listViewOCPrevSearch);
        javaInfoButton = (Button) findViewById(R.id.buttonOCStopSearch);
        transpoAdapter = new SearchAdapter(this);
        javaListView.setAdapter(transpoAdapter);
        javaText = (EditText) findViewById(R.id.editTextOCStopSearch);
        ContentValues cValues = new ContentValues();
        javaInfoButton.setOnClickListener(temp -> {
            //Saves the text to the database, then loads the AndrewTranspoStop activit with the Stop number added

            String tempString = javaText.getText().toString();
            javaStop.add(tempString);
            transpoAdapter.notifyDataSetChanged();
            //Saves the text string to database
            cValues.put(SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO, tempString);
            database.insert(SearchDatabaseHelper.getTableNameOctranspo(), SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO, cValues);
            //Passes the input text to new activity when starting the activity
            Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoStop.class);
            String PassString = javaText.getText().toString();
            intent.putExtra("StopNumber", PassString);
            startActivity(intent);
            javaText.setText("");
        });
        Toast toast = Toast.makeText(this, "Andrew's OCTranspo Toast", Toast.LENGTH_LONG);
        toast.show();

    }

    /**
     * Closes the open database objects when the activity ends
     */
    @Override
    protected void onDestroy() {
        databaseHelp.close();
        database.close();
        cursor.close();
        super.onDestroy();
    }

    /**
     * This adapter updates/generates the AndrewTranspo list view elements
     * This was Largely based on CST2335 – Graphical Interface Programming Lab 4
     */
    public class SearchAdapter extends ArrayAdapter<String> {
        public SearchAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() { // size of array containing stop numbers

            return (javaStop.size());
        }

        public String getItem(int position) {
            return (javaStop.get(position));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = AndrewTranspo.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.octranspo_stopid, null);
            TextView stopIDText = (TextView) result.findViewById(R.id.andrewStopIDTextView);
            ImageView go=result.findViewById(R.id.andrewOCStopIDGoImage);
            ImageView del=result.findViewById(R.id.andrewOCStopIDDelImage);
            go.setVisibility(View.VISIBLE);
            stopIDText.setText(getItem(position)); // get the string at position
            go.setOnClickListener((t)->{ //start the Andrew Transpo Stop activity, and pass it the stop number
                Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoStop.class);
                intent.putExtra("StopNumber", stopIDText.getText());
                startActivity(intent);
            });
            del.setOnClickListener((t)->{
                //Deletes the database entry with the same name
                deleteEntry(stopIDText.getText().toString());
                //Adds a snackbar when you delete, so you can undelete the stop
                //https://developer.android.com/training/snackbar/action.html
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.andrewStopIDTextView),
                        R.string.AndrewStopSearchSnack, Snackbar.LENGTH_LONG);
                mySnackbar.setAction(R.string.AndrewStopSearchSnackUndelete, (a) -> {
                    String tempString=stopIDText.getText().toString();
                    javaStop.add(tempString);
                    transpoAdapter.notifyDataSetChanged();
                    ContentValues cValues = new ContentValues();
                    cValues.put(SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO, tempString);
                    database.insert(SearchDatabaseHelper.getTableNameOctranspo(), SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO, cValues);
                });
                mySnackbar.show();
            });


            return result;

        }

        public long getID(int position) {
            return (position);
        }

    }

    /**
     * Function to delete a string from the Database
     * @param delete - the string you want to delete
     */
    public void deleteEntry(String delete){
        database.delete(SearchDatabaseHelper.getTableNameOctranspo(), SearchDatabaseHelper.KEY_SEARCH_OCTRANSPO +"=?", new String[] {delete} );
        javaStop.remove(delete);
        transpoAdapter.notifyDataSetChanged();

    }

}
