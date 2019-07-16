package com.example.leoss.duanxingxufa.File_xianshi.SQLter2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.leoss.R;


public class SQLiteActivity_sql extends AppCompatActivity {
    ListView id_sqlite_lv1s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        initView();
        datashow();
    }

    private void datashow() {
        try{
            Caozuo_sql caozuo=new Caozuo_sql(this);
            Cursor cursor = caozuo.xianshi_all();
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.layout_shujuku,cursor,
                    new String[]{"_id", "name", "phone", "num1"},new int[]{
                    R.id.id_layout_shujuku_tv_id,R.id.id_layout_shujuku_tv_name,
                    R.id.id_layout_shujuku_tv_phone,R.id.id_layout_shujuku_tv_num1
            });
            id_sqlite_lv1s.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this, "哈哈你出错了@", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        id_sqlite_lv1s = (ListView)findViewById(R.id.id_sqlite_lv);
    }


}
