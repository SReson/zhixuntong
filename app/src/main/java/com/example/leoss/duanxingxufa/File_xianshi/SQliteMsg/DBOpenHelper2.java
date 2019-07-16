package com.example.leoss.duanxingxufa.File_xianshi.SQliteMsg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper2 extends SQLiteOpenHelper {
    public DBOpenHelper2(Context context) {
        super(context, "user2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists u_msg(_id integer primary key autoincrement,name varchar,phone varchar,content varchar)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
