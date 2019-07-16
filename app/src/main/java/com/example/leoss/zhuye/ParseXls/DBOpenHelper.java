package com.example.leoss.zhuye.ParseXls;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/4/29.
 */

public class DBOpenHelper extends SQLiteOpenHelper
{

    public DBOpenHelper(Context context)
    {
        super(context, "user1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="create table if not exists u_user4(_id integer primary key autoincrement,name varchar,sex varchar,address varchar,major varchar,phone varchar,qq varchar,weixin varchar)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
