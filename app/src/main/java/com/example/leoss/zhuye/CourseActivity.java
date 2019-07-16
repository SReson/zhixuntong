package com.example.leoss.zhuye;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.leoss.R;

public class CourseActivity extends AppCompatActivity {
    private WebView id_tongxunlr_webview1s;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initView();
    }

    private void initView() {
        id_tongxunlr_webview1s = findViewById(R.id.id_tongxunlr_webview);
        loadWeb();
    }

    private void loadWeb() {
        //WebView加载本地资源
//        webView.loadUrl("file:///android_asset/example.html");
        //WebView加载web资源
        id_tongxunlr_webview1s.loadUrl("http://47.100.203.0/green/");
        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        id_tongxunlr_webview1s.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }
            //WebViewClient帮助WebView去处理一些页面控制和请求通知
        });
        //启用支持Javascript
        WebSettings settings = id_tongxunlr_webview1s.getSettings();
        settings.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        id_tongxunlr_webview1s.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    closeDialog();
                } else {
                    //网页正在加载，打开ProgressDialog
                    openDialog(newProgress);
                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(CourseActivity.this);
                    dialog.setTitle("正在加载");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.setCancelable(true);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }

            private void closeDialog() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (id_tongxunlr_webview1s.canGoBack()) {
                id_tongxunlr_webview1s.goBack();   //返回上一页面
                return true;
            } else {
                System.exit(0);     //退出程序
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
