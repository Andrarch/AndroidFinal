package com.example.andrew.androidfinal;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.support.design.widget.Snackbar;


public class AndrewTranspo extends Activity {
    ListView javaListView;
    Button javaInfoButton;
    ArrayList<String> javaMessages = new ArrayList<String>();
    EditText javaText;
    SearchDatabaseHelper databaseHelp;
    SearchAdapter transpoAdapter;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelp = new SearchDatabaseHelper(this);
        database = databaseHelp.getWritableDatabase();
        cursor = database.rawQuery("SELECT " + SearchDatabaseHelper.KEY_SEARCH + " FROM " + SearchDatabaseHelper.getTableName(), new String[]{});
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(SearchDatabaseHelper.KEY_SEARCH);
        while (!cursor.isAfterLast()) {
            Log.i("Andrew_OCTranspo", "SQL Message:" + cursor.getString(cursor.getColumnIndex(SearchDatabaseHelper.KEY_SEARCH)));
            javaMessages.add(cursor.getString(column));
            cursor.moveToNext();
        }

        Log.i("Andrew_OCTranspo", "Cursor’s  column count =" + cursor.getColumnCount());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo);
        javaListView = (ListView) findViewById(R.id.listViewOCPrevSearch);
        javaInfoButton = (Button) findViewById(R.id.buttonOCStopSearch);
        transpoAdapter = new SearchAdapter(this);
        javaListView.setAdapter(transpoAdapter);
        javaText = (EditText) findViewById(R.id.editTextOCStopSearch);
        ContentValues cValues = new ContentValues();
        javaInfoButton.setOnClickListener(temp -> {
            String tempString = javaText.getText().toString();
            javaMessages.add(tempString);
            transpoAdapter.notifyDataSetChanged();

            cValues.put(SearchDatabaseHelper.KEY_SEARCH, tempString);
            database.insert(SearchDatabaseHelper.getTableName(), SearchDatabaseHelper.KEY_SEARCH, cValues);
            Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoStop.class);
            String PassString = javaText.getText().toString();
            intent.putExtra("StopNumber", PassString);
            startActivity(intent);
            javaText.setText("");
        });
        Toast toast = Toast.makeText(this, "Andrew's OCTranspo Toast", Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    protected void onDestroy() {
        databaseHelp.close();
        database.close();
        cursor.close();
        super.onDestroy();
    }

    public class SearchAdapter extends ArrayAdapter<String> {
        public SearchAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {

            return (javaMessages.size());
        }

        public String getItem(int position) {
            return (javaMessages.get(position));
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
            go.setOnClickListener((t)->{
                Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoStop.class);
                intent.putExtra("StopNumber", stopIDText.getText());
                startActivity(intent);
            });
            del.setOnClickListener((t)->{
                deleteEntry(stopIDText.getText().toString());
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.andrewStopIDTextView),
                        R.string.AndrewStopSearchSnack, Snackbar.LENGTH_LONG);
                mySnackbar.setAction(R.string.AndrewStopSearchSnackUndelete, (a) -> {
                    javaMessages.add(stopIDText.getText().toString());
                    transpoAdapter.notifyDataSetChanged();

                });
                mySnackbar.show();
            });


            return result;

        }

        public long getID(int position) {
            return (position);
        }

    }
    public void deleteEntry(String delete){
        database.delete(SearchDatabaseHelper.getTableName(), SearchDatabaseHelper.KEY_SEARCH+"=?", new String[] {delete} );
        javaMessages.remove(delete);
        transpoAdapter.notifyDataSetChanged();
        /*javaMessages=new ArrayList<String>();

        cursor=database.rawQuery("SELECT "+ChatDatabaseHelper.KEY_MESSAGE+", "+ChatDatabaseHelper.KEY_ID+ " FROM "+ChatDatabaseHelper.getTableName(),new String[]{});
        cursor.moveToFirst();
        int column=cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        while(!cursor.isAfterLast() ){
            Log.i("ChatWindow", "SQL Message:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            javaMessages.add(cursor.getString(column));
            cursor.moveToNext();
        }
        messageAdapter.notifyDataSetChanged();*/
    }

}
