package com.example.andrew.androidfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew on 3/27/2018.
 */

public class SearchDatabaseHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME_OCTRANSPO ="OCTranspoData.db";
    static int VERSION_NUMBER=1;
    static String KEY_SEARCH_OCTRANSPO ="Search";
    static String KEY_ID_OCTRANSPO ="id";
    static private String TABLE_NAME_OCTRANSPO ="PrevSearch";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME_OCTRANSPO + "( " + KEY_ID_OCTRANSPO
            + " integer primary key autoincrement, " + KEY_SEARCH_OCTRANSPO
            + " VARCHAR2(20) );";


    public SearchDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME_OCTRANSPO, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        Log.i("SearchDatabaseHelper", "Calling onCreate");


    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_OCTRANSPO);
        Log.i("SearchDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVersion + " newVersion= " + newVersion);
        onCreate(db);

    }
    public static String getTableNameOctranspo(){
        return TABLE_NAME_OCTRANSPO;
    }
}
