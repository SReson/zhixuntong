package com.example.leoss.duanxingxufa.File_xianshi.SQLter2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2019/4/29.
 */

public class Caozuo_sql {
    DBOpenHelper_sql helper;

    public Caozuo_sql(Context context) {
        helper = new DBOpenHelper_sql(context);
    }

    public void add(User_sql user){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="insert into u_user3(name,phone,num1,num2,num3,num4) values(?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]
                {
                user.getName(),user.getPhone(),user.getNum1(),user.getNum2(),user.getNum3(),user.getNum4()
        });
        db.close();
    }


    public Cursor xianshi_all()
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor=db.query("u_user3",null,null,null,null,null,"_id desc",null);
    return cursor;
    }
}
