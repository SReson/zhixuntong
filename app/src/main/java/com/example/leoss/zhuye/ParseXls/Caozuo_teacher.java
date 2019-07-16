package com.example.leoss.zhuye.ParseXls;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2019/4/29.
 */

public class Caozuo_teacher
{
    DBOpenHelper helper;

    public Caozuo_teacher(Context context)
    {
        helper = new DBOpenHelper(context);
    }

    public void add(Teacher_info user)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="insert into u_user4(name,sex,address,major,phone,qq,weixin) values(?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{
                user.getName(),user.getSex(),user.getAddress(),user.getMajor(),user.getPhone(),user.getQq(),user.getWeixin()
//            user.getShoujihao(),user.getDuanxin(),user.getCanshu1(),user.getCanshu2(),user.getCanshu3(),user.getCanshu4(),user.getYifansong(),user.getXuanze()
        });
        db.close();
    }

    /**
     * 查找用户信息
     */
    public Teacher_info find(int userid)
    {
        SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
        String sql="select name,sex,address,major,phone,qq,weixin from u_user4 where _id=?";
        Cursor cursor=db.rawQuery(sql, new String[]{
                String.valueOf(userid)
        });


        if(cursor.moveToNext())
        {
            return new Teacher_info(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("sex")),
                    cursor.getString(cursor.getColumnIndex("address")),
                    cursor.getString(cursor.getColumnIndex("major")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getString(cursor.getColumnIndex("qq")),
                    cursor.getString(cursor.getColumnIndex("weixin"))
            );
        }
        return null;
    }


//    /**
//     * 查找用户信息
//     */
//    public User find_zhanghao(String zhanghao){
//        SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
//        String sql="select zhanghao,mima,leibie from u_user3 where zhanghao=?";
//        Cursor cursor=db.rawQuery(sql, new String[]{
//                zhanghao
//        });
//        if(cursor.moveToNext()){
//            return new User(
//                    cursor.getString(cursor.getColumnIndex("zhanghao")),
//                    cursor.getString(cursor.getColumnIndex("mima")),
//                    cursor.getString(cursor.getColumnIndex("leibie"))
//            );
//        }
//        return null;
//    }


    public Cursor xianshi_all()
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor=db.query("u_user4",null,null,null,null,null,"_id asc",null);
        return cursor;
    }

    public Cursor filter_show(String s)
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor=db.query("u_user4",null,s,null,null,null,"_id asc",null);
        return cursor;
    }

}
