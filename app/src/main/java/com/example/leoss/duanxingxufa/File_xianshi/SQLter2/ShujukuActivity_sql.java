package com.example.leoss.duanxingxufa.File_xianshi.SQLter2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.leoss.R;


public class ShujukuActivity_sql extends AppCompatActivity implements View.OnClickListener {
    EditText id_shujuku_ed_zhanghao1s,id_shujuku_ed_mima1s;
    Button id_shujuku_bt_tianjia1s,id_shujuku_bt_xianshi1s;
    ListView id_shujuku_lv1s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shujuku);

        initView();

    }

    private void initView() {
        id_shujuku_ed_zhanghao1s = (EditText)findViewById(R.id.id_shujuku_ed_zhanghao);
        id_shujuku_ed_mima1s=(EditText)findViewById(R.id.id_shujuku_ed_mima);

        id_shujuku_lv1s = (ListView)findViewById(R.id.id_shujuku_lv);

        id_shujuku_bt_tianjia1s = (Button)findViewById(R.id.id_shujuku_bt_tianjia);
        id_shujuku_bt_xianshi1s = (Button)findViewById(R.id.id_shujuku_bt_xianshi);

        id_shujuku_bt_xianshi1s.setOnClickListener(this);
        id_shujuku_bt_tianjia1s.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == id_shujuku_bt_tianjia1s) {
            try {
                String zhanghao = id_shujuku_ed_zhanghao1s.getText().toString();
                String mima = id_shujuku_ed_mima1s.getText().toString();

                User_sql user = new User_sql(zhanghao, mima, "teacher","1","11","22");
                Caozuo_sql caozuo = new Caozuo_sql(this);
                caozuo.add(user);
                Toast.makeText(this, "数据成功添加！", Toast.LENGTH_SHORT).show();

                Cursor cursor = caozuo.xianshi_all();
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout_shujuku, cursor,
                        new String[]{"_id", "name", "phone", "num1"}, new int[]{
                        R.id.id_layout_shujuku_tv_id,R.id.id_layout_shujuku_tv_name,
                        R.id.id_layout_shujuku_tv_phone,R.id.id_layout_shujuku_tv_num1
                });
                id_shujuku_lv1s.setAdapter(adapter);

            } catch (Exception e) {
                Toast.makeText(this, "哈哈你出错了@", Toast.LENGTH_SHORT).show();
            }
        }
        if(v==id_shujuku_bt_xianshi1s){
            Intent intent = new Intent();
            intent.setClass(this,SQLiteActivity_sql.class);
            startActivity(intent);
        }
    }

}
