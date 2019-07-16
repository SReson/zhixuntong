package com.example.leoss.duanxingxufa.File_xianshi.SQLter2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/4/29.
 */

public class DBOpenHelper_sql extends SQLiteOpenHelper
{

    public DBOpenHelper_sql(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="create table if not exists u_user3(_id integer primary key autoincrement,name varchar,phone varchar,num1 varchar,num2 varchar,num3 varchar,num4 varchar)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
