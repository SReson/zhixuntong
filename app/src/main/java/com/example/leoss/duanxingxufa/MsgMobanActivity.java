package com.example.leoss.duanxingxufa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.leoss.R;
import com.example.leoss.zhuye.File_xianshi.ToFileActivity;


public class MsgMobanActivity extends AppCompatActivity implements View.OnClickListener {
    EditText id_msgmoban_ed_model1s;
    Button id_msgmoban_bt_phone1s;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_moban);
        initView();
        verifyStoragePermissions(this);
    }

    private void initView() {
        id_msgmoban_ed_model1s = (EditText) findViewById(R.id.id_mesmoban_ed_model);
        id_msgmoban_bt_phone1s = (Button) findViewById(R.id.id_msgmoban_bt_phone);
        id_msgmoban_bt_phone1s.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_msgmoban_bt_phone:
                Intent intent = new Intent();
                intent.setClass(this,ToFileActivity.class);
                intent.putExtra("DX",1);
                intent.putExtra("smsModel",id_msgmoban_ed_model1s.getText().toString());
                startActivity(intent);
                break;
        }
    }


    public void verifyStoragePermissions(Activity activity)
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
