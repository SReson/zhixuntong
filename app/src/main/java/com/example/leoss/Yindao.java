package com.example.leoss;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Yindao extends AppCompatActivity implements View.OnClickListener {
    TextView id_yindao_tv_tianshu1s;
    Button id_yindao_bt_xiangqing1s;
    Button id_yindao_bt_tiaoguo1s;
    int tianshu;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    private ContentResolver mContentResolver = null;
    //第一个界面----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yindao);
//        verifyStoragePermissions(this);

        initView();
        try {
            json_parse();
        }catch (Exception e){
            Toast.makeText(this, "解析出错", Toast.LENGTH_SHORT).show();
        }
        //注意sdk28会出错

        daojishi();

    }

    private void daojishi() {
        /**
         * 启动界面
         *
         */

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(Yindao.this,ShouyeActivity.class));
                finish();
            }
        };timer.schedule(timerTask,1000);
    }

    private void json_parse() {
        try{
            RequestQueue mQueue = Volley.newRequestQueue(Yindao.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://47.100.203.0/android/json_object2.json",null,new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    parseJsonWithGson(response.toString());//Gson解析Json对象
                }
            }, null);
            mQueue.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(this, "解析失败2", Toast.LENGTH_SHORT).show();
        }

    }

    private void parseJsonWithGson(String jsonString) {
        try{
            Gson gson = new Gson();
            final JsonData message = gson.fromJson(jsonString,JsonData.class);
            id_yindao_tv_tianshu1s.post(new Runnable() {
                @Override
                public void run() {
                    id_yindao_tv_tianshu1s.setText(message.getStr1());
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "解析失败3", Toast.LENGTH_SHORT).show();
        }

    }



    private void initView() {
        id_yindao_bt_xiangqing1s = (Button) findViewById(R.id.id_yindao_bt_xiangqing);
        id_yindao_tv_tianshu1s = (TextView) findViewById(R.id.id_yindao_tv_tianshu);
        id_yindao_bt_tiaoguo1s = (Button) findViewById(R.id.id_yindao_bt_tiaoguo);

        id_yindao_bt_xiangqing1s.setOnClickListener(this);
        id_yindao_bt_tiaoguo1s.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        tianshu = Integer.valueOf(id_yindao_tv_tianshu1s.getText().toString());
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.id_yindao_bt_tiaoguo:
                if(tianshu>=1) {
                    intent.setClass(this, ShouyeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "请查看详情！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.id_yindao_bt_xiangqing:
                if(tianshu>=1) {
                    intent.setClass(this, ShouyeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "请查看详情！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public static void verifyStoragePermissions(Activity activity)
    {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);

        }
    }
}
