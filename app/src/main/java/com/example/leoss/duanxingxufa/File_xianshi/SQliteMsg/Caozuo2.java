package com.example.leoss.duanxingxufa.File_xianshi.SQliteMsg;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Caozuo2 {
        DBOpenHelper2 helper;

        public Caozuo2(Context context) {
            helper = new DBOpenHelper2(context);
        }

        public void tianjia(Msg msg){
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql="insert into u_msg(name,phone,content) values(?,?,?)";
            db.execSQL(sql,new Object[]{
                    msg.getName(),msg.getPhone(),msg.getContent()
            });
            db.close();
        }

    public Cursor xianshi(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor=db.query("u_msg",null,null,null,null,null,"_id desc",null);
        return cursor;
    }

    }
