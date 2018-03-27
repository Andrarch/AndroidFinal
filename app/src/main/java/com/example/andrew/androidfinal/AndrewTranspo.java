package com.example.andrew.androidfinal;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

public class AndrewTranspo extends Activity {
    ListView javaListView;
    Button javaInfoButton;
    ArrayList<String> javaMessages=new ArrayList<String>();
    EditText javaText;
    ChatDatabaseHelper databaseHelp;

    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelp=new ChatDatabaseHelper(this);
        database= databaseHelp.getWritableDatabase();
        cursor=database.rawQuery("SELECT Message FROM "+ChatDatabaseHelper.getTableName(),new String[]{});
        cursor.moveToFirst();
        int column=cursor.getColumnIndex(ChatDatabaseHelper.KEY_SEARCH);
        while(!cursor.isAfterLast() ){
            Log.i("Andrew_OCTranspo", "SQL Message:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_SEARCH) ) );
            javaMessages.add(cursor.getString(column));
            cursor.moveToNext();
        }

        Log.i("Andrew_OCTranspo", "Cursor’s  column count =" + cursor.getColumnCount() );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo);
        javaListView=(ListView)findViewById(R.id.listViewOCPrevSearch);
        javaInfoButton =(Button)findViewById(R.id.buttonOCStopSearch);
        SearchAdapter messageAdapter =new SearchAdapter( this );
        javaListView.setAdapter (messageAdapter);
        javaText=(EditText) findViewById(R.id.editTextOCStopSearch);
        ContentValues cValues=new ContentValues();
        javaInfoButton.setOnClickListener(temp->{
            String tempString=javaText.getText().toString();
            javaMessages.add(tempString);
            messageAdapter.notifyDataSetChanged();
            javaText.setText("");
            cValues.put(ChatDatabaseHelper.KEY_SEARCH,tempString);
            database.insert(ChatDatabaseHelper.getTableName(),ChatDatabaseHelper.KEY_SEARCH,cValues);
        });


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
        public int getCount(){

            return (javaMessages.size());
        }
        public String getItem(int position){
            return(javaMessages.get(position));
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = AndrewTranspo.this.getLayoutInflater();
            View result = null ;
            result = inflater.inflate(R.layout.octranspo_stopid, null);
            TextView message = (TextView)result.findViewById(R.id.textView);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
        public long getID(int position){
            return(position);
        }

    }
    public static class ChatDatabaseHelper extends SQLiteOpenHelper {
        static String DATABASE_NAME="OCSearches.db";
        static int VERSION_NUMBER=0;
        static String KEY_SEARCH="Search";
        static String KEY_ID="id";
        static private String TABLE_NAME="PrevSearch";
        String ACTIVITY_NAME="ChatWindow";
        private static final String DATABASE_CREATE = "create table "
                + TABLE_NAME + "( " + KEY_ID
                + " integer primary key autoincrement, " + KEY_SEARCH
                + " VARCHAR2(20) );";


        public ChatDatabaseHelper(Context ctx) {

            super(ctx,DATABASE_NAME, null, VERSION_NUMBER);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DATABASE_CREATE);
            Log.i("ChatDatabaseHelper", "Calling onCreate");


        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVersion + " newVersion= " + newVersion);
            onCreate(db);

        }
        public static String getTableName(){
            return TABLE_NAME;
        }
    }
}
