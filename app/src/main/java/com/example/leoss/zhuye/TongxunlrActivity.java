package com.example.leoss.zhuye;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.leoss.R;
import com.example.leoss.zhuye.File_xianshi.ToFileActivity;

public class TongxunlrActivity extends AppCompatActivity {

    private WebView id_tongxunlr_webview1s;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongxunlr);
        initView();

    }

    private void initView() {


    }


    public void skip_to_file(View view){
        Intent intent = new Intent();
        intent.setClass(this,ToFileActivity.class);
        startActivity(intent);
    }

    public void skip_to_course(View view){
        Intent intent = new Intent();
        intent.setClass(this,CourseActivity.class);
        startActivity(intent);
    }

}
