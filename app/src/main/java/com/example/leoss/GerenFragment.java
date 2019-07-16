package com.example.leoss;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GerenFragment extends Fragment implements View.OnClickListener {
    private final static int FLAG=123;
    private  final  static  int FLAG2=2;
    private Button mButtonConnection;
    private TextView  mTextView_Bb;
    String content;
    private final static int MULTI_LINE = 1234;
    private int length;
    private ProgressBar mProgressBar;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    private IntentFilter intentFilter;
    View view;
    private Context mContext;
    private boolean[] checkItems;

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private SharedPreferences sp;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //该View表示该碎片的主界面,最后要返回该view
       view = inflater.inflate(R.layout.fragment_geren, container, false);
        //找到主界面view后，就可以进行UI的操作了。
        //注意：因为主界面现在是view，所以在找寻控件时要用view.findViewById
        infindview();

        sp = getActivity().getSharedPreferences("peng", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        Boolean butt=true;
        editor.putBoolean("buttoncf",butt);
        editor.apply();

       // if (mTextView_Bb.getText().toString().equals())
/**
 * 新建文件夹
 */
        File file1 = new File("/sdcard/AndroidTest/APK" );
        if (!file1.exists()) {
            file1.mkdirs();
        }

//        mButtonStartInterface.setOnClickListener(this);
        //Toast.makeText(MainActivity.this,content,Toast.LENGTH_SHORT).show();

        mButtonConnection.setOnClickListener(this);

        net();


        return view;
    }

    /**
     * 网络判断
     */
    private void net()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null&&networkInfo.isAvailable())
        {


            /**
             * 避免重复点击下载
             */
            Boolean buttcf= sp.getBoolean("buttoncf",false);
            if (buttcf)
            {

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        connectServlet();
                    }
                }).start();

            }else
                {
                Toast.makeText(getActivity(),"正在下载中噢",Toast.LENGTH_SHORT).show();
              }



            Toast.makeText(getActivity(),"当前网络可用",Toast.LENGTH_SHORT).show();

        }else
        {
            Toast.makeText(getActivity(),"当前网络不可用",Toast.LENGTH_SHORT).show();

            if (fileIsExists("/sdcard/AndroidTest/APK/jump.apk"))
            {
                Toast.makeText(getActivity(), "APK已下载", Toast.LENGTH_SHORT).show();
                fileDodw_anzhuan();
            }
        }
    }




    @SuppressLint("HandlerLeak")
    private Handler handle = new Handler()
    {
        /**
         *多线程下载时使用handler来更新porgressbar
         */
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MULTI_LINE:
                    int progress = msg.arg1;
                    mProgressBar.setProgress(msg.arg1 * 100 / length);
                    break;
            }
        }


    };






    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case FLAG://123

                    content=(String) msg.obj;

                    if (content.equals(mTextView_Bb.getText().toString()))//判断当前版本号
                    {
                        Toast.makeText(getActivity(),"已是最新 版本:"+content,Toast.LENGTH_SHORT).show();


                    }
//                    else
//                        {
//                          Boolean  butt=sp.getBoolean("buttoncf",false);
//                           if (butt)
//                            {
//                               AlertDialog_a();
//                           }
//                         }
                    break;

                case FLAG2://2

                    content=(String) msg.obj;
                    AlertDialog_a();

                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void AlertDialog_a() {

        android.support.v7.app.AlertDialog.Builder normalMoreButtonDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        normalMoreButtonDialog.setIcon(R.drawable.ic_launcher_foreground);
        normalMoreButtonDialog.setTitle("提示");
        normalMoreButtonDialog.setMessage("发现新版本可以更新噢*v*");

        //设置按钮
        normalMoreButtonDialog.setPositiveButton("确认"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                        //点击确定按钮
                        if (fileIsExists("/sdcard/AndroidTest/APK/jump.apk"))
                        {

                            Toast.makeText(getActivity(),"已下载文件，请安装",Toast.LENGTH_SHORT).show();
                            fileDodw_anzhuan();

                        }
                        else
                        {
                            //单线程
////                           MyAsyncTast task = new MyAsyncTast();
////                           task.execute();
                            //多线程
                            MYther();
                        }
                    }
                });
//                    normalMoreButtonDialog.setNegativeButton("button2"
//                            , new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    dialog.dismiss();
//                                }
//                            });
        normalMoreButtonDialog.setNeutralButton("取消"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

        normalMoreButtonDialog.create().show();

    }

/**
 * 安装完成后自动删除
 */

//public class InitApkBroadCastReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//            deleteFolderFile("/sdcard/AndroidTest/APK/jump.apk",true);
//            Toast.makeText(context , "监听到系统广播添加" , Toast.LENGTH_LONG).show();
//        }
//
//        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//
//            deleteFolderFile("/sdcard/AndroidTest/APK/jump.apk",true);
//            Toast.makeText(context , "监听到系统广播移除" , Toast.LENGTH_LONG).show();
//
//        }
//
//        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
//            deleteFolderFile("/sdcard/AndroidTest/APK/jump.apk",true);
//            Toast.makeText(context , "监听到系统广播替换" , Toast.LENGTH_LONG).show();
//        }
//
//
////        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
////            System.out.println("监听到系统广播添加");
////
////        }
////
////        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
////            System.out.println("监听到系统广播移除");
////
////        }
////
////        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
////            System.out.println("监听到系统广播替换");
////
////        }
//
//    }
//
//
//}


    /**
     * delate
     */

    public void deleteFolderFile(String filePath, boolean dedeleteThisPath){
        try {
            Toast.makeText(getActivity(),"aaaaa",Toast.LENGTH_SHORT).show();
            String rootPathSD = Environment.getExternalStorageDirectory().getPath();
            File file = new File(rootPathSD + filePath);//获取SD卡指定路径
            // File file = new File( filePath);//获取SD卡指定路径
            File[] files = file.listFiles();//获取SD卡指定路径下的文件或者文件夹
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()){//如果是文件直接删除
                    File photoFile = new File(files[i].getPath());
                    Log.d("photoPath -->> ", photoFile.getPath());
                    photoFile.delete();
                }else {
                    if (dedeleteThisPath) {//如果是文件夹再次迭代进里面找到指定文件路径
                        File[] myfile = files[i].listFiles();
                        for (int d = 0; d < myfile.length; d++)
                        {
                            File photoFile = new File(myfile[d].getPath());
                            photoFile.delete();
                        }
                    }

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /**
     * 多线程下载
     */
    private void MYther() {
        new Thread( new Runnable()
        {

            @Override
            public void run()
            {
                String urlString = "http://47.100.203.0/download/jump.apk";
                // 连接网络的权限不要忘记添加
                URL url;
                try
                {
                    url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    //获得传输内容的长度
                    length = connect.getContentLength();
                    InputStream input = connect.getInputStream();
                    //定义要写入文件的路径
                    File file = new File("/sdcard/AndroidTest/APK", "jump.apk");
                    //当文件不存在时创建文件

                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    MyThread[] threads = new MyThread[5];
                    for (int i = 0; i < 5; i++)
                    {
                        //开启自定义的线程，并传入相应的值
                        MyThread thread = null;
                        if (i == 4) {
                            //最后一个文件传输的内容可能不为整数，所以要单独拿出来进行传输
                            thread = new MyThread(length / 5 * 4, length, urlString, file.getAbsolutePath());
                        } else {
                            //注意减一
                            thread = new MyThread(length / 5 * i, length / 5 * (i + 1)-1, urlString,
                                    file.getAbsolutePath());
                        }
                        //开启线程
                        thread.start();
                        threads[i] = thread;
                    }

                    //但没有下载完时，不断对已经下载的数量进行更新。
                    boolean isFinish = true;
                    while (isFinish)
                    {
                        int sum = 0;
                        for (MyThread thread : threads) {
                            sum += thread.getSum();
                        }


                        Message msg = handle.obtainMessage();
                        msg.what = MULTI_LINE;
                        msg.arg1 = sum;
                        handle.sendMessage(msg);
                        if (sum + 10 >= length)
                        {
                            isFinish = false;
                        }
                        //下载完时，线程休眠
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    /**
     * 控件初始化
     */
    private void infindview()
    {
        mTextView_Bb=(TextView)view.findViewById(R.id.geten_textview_Bb);
        mButtonConnection = (Button)view.findViewById(R.id.button_connection);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);

    }

    /**
     *
     * 动态权限获取
     */
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

    /**
     * Onclick事件
     *
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_connection:

                fileIsExists("/sdcard/AndroidTest/APK/jump.apk");

                // fileDodw_anzhuan();


                //直接开启一个线程进行连网操作
                // TestView获取线程
                verifyStoragePermissions(getActivity());
               Boolean butt= sp.getBoolean("buttoncf",false);
               if (butt)
               {
                   new Thread(new Runnable()
                   {
                       @Override
                       public void run()
                       {
                           connectServlet();
                       }
                   }).start();
               }else {
                   Toast.makeText(getActivity(),"别着急*v*",Toast.LENGTH_SHORT).show();
               }


                break;



            //case R.id.button_start_download:
            // startActivity(new Intent(MainActivity.this,IndexActivity.class));

            //break;
            default:
                break;
        }
    }



    /**
     * 获取txt文件
     */
    private void connectServlet()
    {
        String urlString = "http://47.100.203.0/download/down.txt";
        //连接网络的权限不要忘记添加
        URL url;
        try
        {
            //创建URL，传入网址
            url = new URL(urlString);
            //建立URL连接，打开连接
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            //获得连接的输入流，读取传过来的信息
            InputStream input=connect.getInputStream();
            //封装读取文件
            BufferedReader br=new BufferedReader(new InputStreamReader(input));
            String line =br.readLine();
            StringBuffer builder=new StringBuffer();

            while(line!=null)
            {
                builder.append(line);
                line=br.readLine();
            }
            //通过Msg来传递消息，在handler中更新线程
            Message msg=handler.obtainMessage();

            msg.obj=builder.toString().trim();
              if (!builder.toString().trim().equals(mTextView_Bb.getText().toString())){

                  msg.what=FLAG2;

              }else {

                  msg.what=FLAG;  //turn
              }
            handler.sendMessage(msg);
            br.close();
            input.close();

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
    }

    /**
     * 跳转到安装页面
     */
    public void fileDodw_anzhuan()
    {
        if ("file:/storage/emulated/0/jump.apk".endsWith(".apk"))
        {

            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                /* Android N 写法*/
                intent2.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", new File("/sdcard/AndroidTest/APK/jump.apk"));
                intent2.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                /* Android N之前的老版本写法*/
                intent2.setDataAndType(Uri.fromFile(new File("/sdcard/AndroidTest/APK/jump.apk")), "application/vnd.android.package-archive");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(intent2);
        }

            mTextView_Bb.setText(content);


    }

//    private boolean isAppInstalled(String uri){
//        PackageManager pm = getActivity().getPackageManager();
//        boolean installed =false;
//        try{
//            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
//            installed =true;
//        }catch(PackageManager.NameNotFoundException e){
//            installed =false;
//        }
//        return installed;
//    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch(PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

}
