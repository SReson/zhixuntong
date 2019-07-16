package com.example.leoss.duanxingxufa;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leoss.R;
import com.example.leoss.duanxingxufa.File_xianshi.SQLter2.Caozuo_sql;

import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class FileDownloadActivity extends AppCompatActivity implements View.OnClickListener
{

    Button id_xls_bt_download1s;
    ListView id_xls_lv1s;
    Button id_xls_btOpen1s;
    EditText id_xls_ed_phone1s;
    String smsModel;
    ArrayList<String> phone_num = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);

        initView();
        show_data();

    }

    private void initView()
    {


        /*打开文件*/
        id_xls_btOpen1s = (Button) findViewById(R.id.id_xls_bt_open);
        id_xls_ed_phone1s = (EditText)findViewById(R.id.id_xls_tv_phone);
        id_xls_lv1s = (ListView)findViewById(R.id.id_xls_lv);

        id_xls_btOpen1s.setOnClickListener(this);
        id_xls_bt_download1s = (Button)findViewById(R.id.id_xls_bt_download);
        id_xls_bt_download1s.setOnClickListener(this);

        Intent intent = getIntent();
        smsModel=intent.getStringExtra("smsModel");

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.id_xls_bt_download:
                show_data();
               // Intent intent=new Intent();
                //intent.setClass(this,File_item_activit.class);
               // startActivity(intent);
                break;



           //跳转SmsActity发送界面
            case R.id.id_xls_bt_open:
                Intent intent4=new Intent();
                intent4.setClass(this,SmsActity.class);
                intent4.putExtra("phone",id_xls_ed_phone1s.getText().toString());
                startActivity(intent4);
                break;
        }

    }


    private void show_data()
    {
        try {

            Caozuo_sql caozuo = new Caozuo_sql(this);
            Cursor cursor = caozuo.xianshi_all();
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout_shujuku, cursor,
                    new String[]{"_id", "name", "phone", "num1"}, new int[]
                    {
                    R.id.id_layout_shujuku_tv_id, R.id.id_layout_shujuku_tv_name,
                    R.id.id_layout_shujuku_tv_phone, R.id.id_layout_shujuku_tv_num1
                    });


            id_xls_lv1s.setAdapter(adapter);
            id_xls_lv1s.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {

                    TextView id_layout_name = view.findViewById(R.id.id_layout_shujuku_tv_name);
                    TextView id_layout_phone = view.findViewById(R.id.id_layout_shujuku_tv_phone);
                    Toast.makeText(FileDownloadActivity.this, id_layout_phone.getText(), Toast.LENGTH_SHORT).show();
                    id_layout_phone.setBackgroundColor(Color.RED);

                    String phone = id_layout_phone.getText().toString();
                    String temp = id_xls_ed_phone1s.getText().toString();


                    for (int num = 0; num <= phone_num.size(); num++)
                    {
                        Toast.makeText(FileDownloadActivity.this, phone_num.toString(), Toast.LENGTH_SHORT).show();
                        if ((!phone_num.contains(String.valueOf(i))) && (!TextUtils.isEmpty(temp)))
                        {
                            id_xls_ed_phone1s.setText(temp + ',' + phone);
                            phone_num.add(String.valueOf(i));
                        } else if ((!phone_num.contains(String.valueOf(i))) && (TextUtils.isEmpty(temp)))
                        {
                            id_xls_ed_phone1s.setText(phone);
                            phone_num.add(String.valueOf(i));
                        }
                    }



                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "哈哈你出错了@", Toast.LENGTH_SHORT).show();
        }


    }
}
