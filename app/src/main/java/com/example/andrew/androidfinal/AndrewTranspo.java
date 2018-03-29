package com.example.andrew.androidfinal;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        SearchAdapter messageAdapter = new SearchAdapter(this);
        javaListView.setAdapter(messageAdapter);
        javaText = (EditText) findViewById(R.id.editTextOCStopSearch);
        ContentValues cValues = new ContentValues();
        javaInfoButton.setOnClickListener(temp -> {
            String tempString = javaText.getText().toString();
            javaMessages.add(tempString);
            messageAdapter.notifyDataSetChanged();
            javaText.setText("");
            cValues.put(SearchDatabaseHelper.KEY_SEARCH, tempString);
            database.insert(SearchDatabaseHelper.getTableName(), SearchDatabaseHelper.KEY_SEARCH, cValues);
            Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoDetail.class);
            String PassString = javaText.getText().toString();
            intent.putExtra("StopNumber", PassString);
            startActivity(intent);
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
            TextView message = (TextView) result.findViewById(R.id.textView);
            message.setText(getItem(position)); // get the string at position
            message.setOnClickListener(temp -> {
                javaText.setText(message.getText());

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.textView),
                        R.string.AndrewStopSearchSnack, Snackbar.LENGTH_SHORT);
                mySnackbar.setAction(R.string.AndrewStopSearchSnackGo, (t) -> {
                    Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoDetail.class);
                    intent.putExtra("StopNumber", message.getText());
                    startActivity(intent);

                });
                mySnackbar.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Stop Number: "+message.getText())
                        .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(AndrewTranspo.this, AndrewTranspoDetail.class);
                                intent.putExtra("StopNumber", message.getText());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.Delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast toast = Toast.makeText(AndrewTranspo.this, "Placeholder for Delete", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });


                AlertDialog alert = builder.create();
                alert.show();

            });

            return result;

        }

        public long getID(int position) {
            return (position);
        }

    }
}
