package com.lanshiliang.imessage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lanshiliang on 2016/1/12.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_THREAD = "CREATE TABLE threads (" +
            "   _id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   date INTEGER DEFAULT 0,message_count INTEGER DEFAULT 0," +
            "   recipient_ids TEXT,snippet TEXT," +
            "   snippet_cs INTEGER DEFAULT 0," +
            "   read INTEGER DEFAULT 1," +
            "   type INTEGER DEFAULT 0," +
            "   error INTEGER DEFAULT 0," +
            "   has_attachment INTEGER DEFAULT 0" +
            ")";

    public static  final String CREATE_MSG= "CREATE TABLE sms (_id INTEGER PRIMARY KEY,"+
            "   thread_id INTEGER,"+
            "   address TEXT,"+
            "   person INTEGER,"+
            "   date INTEGER,"+
            "   date_sent INTEGER DEFAULT 0,"+
            "   protocol INTEGER,"+
            "   read INTEGER DEFAULT 0,"+
            "   status INTEGER DEFAULT -1,"+
            "   type INTEGER,"+
            "   reply_path_present INTEGER,"+
            "   subject TEXT,"+
            "   body TEXT,"+
            "   service_center TEXT,"+
            "   locked INTEGER DEFAULT 0,"+
            "   error_code INTEGER DEFAULT 0,"+
            "   seen INTEGER DEFAULT 0"+
            ")";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_THREAD);
        db.execSQL(CREATE_MSG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
