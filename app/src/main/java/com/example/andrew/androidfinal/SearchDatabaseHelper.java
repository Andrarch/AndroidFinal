package com.example.andrew.androidfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew on 3/27/2018.
 */

public class SearchDatabaseHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="OCSearches.db";
    static int VERSION_NUMBER=1;
    static String KEY_SEARCH="Search";
    static String KEY_ID="id";
    static private String TABLE_NAME="PrevSearch";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + KEY_ID
            + " integer primary key autoincrement, " + KEY_SEARCH
            + " VARCHAR2(20) );";


    public SearchDatabaseHelper(Context ctx) {

        super(ctx,DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        Log.i("SearchDatabaseHelper", "Calling onCreate");


    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("SearchDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVersion + " newVersion= " + newVersion);
        onCreate(db);

    }
    public static String getTableName(){
        return TABLE_NAME;
    }
}
