package com.example.leoss.duanxingxufa;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leoss.R;
import com.example.leoss.duanxingxufa.File_xianshi.SQliteMsg.Caozuo2;

import java.util.ArrayList;
import java.util.Set;



/**
 * 短信群发 并将数据保存到数据库
 * */
public class SmsActity extends Activity {
    String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    public static final Uri SMS_URI = Uri.parse("content://sms/");

    private ImageButton id_duanxin_ibt_jiahao1s;
    private ListView id_layout_number_lv1s;
    private String body;//短信内容
    private String[] address ; //
    private ListView id_message_lv1s;

    private TextView id_layout_msg_tv_content1s;
    private TextView id_layout_msg_tv_phone1s;
    ArrayList<String> phone_num = new ArrayList<String>();;

    private String data[] = {"18570705202","18511895950","18570705202","18511895950","18570705202","18570705202","18511895950","18574006202"};//假数据
    long id;
    View dialogView;
    Set<String> addr;

    String list_content;
    String list_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_send);

        initView();
        message_show();


    }

    private void message_show() {



        Caozuo2 caozuo=new Caozuo2(this);
        Cursor cursor = caozuo.xianshi();

        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,R.layout.layout_msg,cursor,
                new String[]{"_id","name","phone","content"},new int[]{
                        R.id.id_layout_msg_tv_id, R.id.id_layout_msg_tv_name,
                R.id.id_layout_msg_tv_phone,R.id.id_layout_msg_tv_content
        });

        id_message_lv1s.setAdapter(adapter2);
        id_message_lv1s.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                id_layout_msg_tv_phone1s=view.findViewById(R.id.id_layout_msg_tv_phone);
                list_phone = id_layout_msg_tv_phone1s.getText().toString();

                id_layout_msg_tv_content1s = view.findViewById(R.id.id_layout_msg_tv_content);
                list_content = id_layout_msg_tv_content1s.getText().toString();

                Toast.makeText(SmsActity.this, list_phone, Toast.LENGTH_SHORT).show();


                    for (int num = 0; num <= phone_num.size(); num++)
                    {
                        Toast.makeText(SmsActity.this, phone_num.toString(), Toast.LENGTH_SHORT).show();

                        if ((!phone_num.contains(String.valueOf(i))))
                        {
                            if(ContextCompat.checkSelfPermission(SmsActity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions(SmsActity.this, new String[]{Manifest.permission.SEND_SMS}, 3);
                            } else
                                if(ContextCompat.checkSelfPermission(SmsActity.this , Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED)
                                {
                                ActivityCompat.requestPermissions(SmsActity.this,new String[]{Manifest.permission.READ_PHONE_STATE},4);
                                 }
                            phone_num.add(String.valueOf(i));
                        } else{
                            Toast.makeText(SmsActity.this, "该短信已发送", Toast.LENGTH_SHORT).show();
                              }
                    }

            }
        });
    }

    private void initView() {

        id_message_lv1s = (ListView) findViewById(R.id.id_message_lv);

        //另外加
        //号码    选择   已发送  序号

        id_duanxin_ibt_jiahao1s = (ImageButton)findViewById(R.id.id_message_bt_tianjia);
        id_layout_number_lv1s = (ListView)findViewById(R.id.id_layout_number_lv);

        id_duanxin_ibt_jiahao1s.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SmsActity.this,FileDownloadActivity.class);
                startActivity(intent);
            }
        });

    }

    public void sendSMS(Set<String> phone, String body, long threadId){
        SmsManager msg = SmsManager.getDefault();
        Intent send = new Intent(SENT_SMS_ACTION);
        // 短信发送广播
        PendingIntent sendPI = PendingIntent.getBroadcast(
                this, 0, send, 0);
        Intent delive = new Intent(DELIVERED_SMS_ACTION);
        // 发送结果广播
        PendingIntent deliverPI = PendingIntent.getBroadcast(
                this, 0, delive, 0);
        //将数据插入数据库
        ContentValues cv = new ContentValues();
        for(String pno:phone ){
            msg.sendTextMessage(pno, null, body, sendPI, deliverPI);
            cv.put("thread_id", threadId);
            cv.put("date", System.currentTimeMillis());
            cv.put("body", body);
            cv.put("read", 0);
            cv.put("type", 2);
            cv.put("address", pno);
            this.getContentResolver().insert(SMS_URI, cv);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    sendSMS(addr, body, id);
                } else {
                    Toast.makeText(this,"恭喜发送成功！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendSMS(addr, body, id);
                } else {
                    sendSMS(addr, body, id);

                    Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show();
                }
            case 3:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    sendSingalMsg(list_phone,list_content);
                } else {
                    Toast.makeText(this,"恭喜发送成功！",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this,"抱歉您没有此权限！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendSingalMsg(list_phone,list_content);
                } else {
                    sendSingalMsg(list_phone,list_content);
                    id_layout_msg_tv_content1s.setBackgroundColor(Color.RED);
//                    Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show();
                }
            default:
        }
    }

    private void sendSingalMsg(String phone, String content) {

        try{
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<String> messages = SmsManager.getDefault().divideMessage(content);
            for (String text : messages) {
                SmsManager.getDefault().sendTextMessage(phone, null, text, null, null);
            }
            Log.d("MainActivity", "1");
//            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    protected void onResume() {
        super.onResume();
        //注册监听
        registerReceiver(sendMessage, new IntentFilter(SENT_SMS_ACTION));
    }
    BroadcastReceiver sendMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            // 判断短信是否成功
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(SmsActity.this, "发送成功！", Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    Toast.makeText(SmsActity.this, "发送失败！", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };


}
