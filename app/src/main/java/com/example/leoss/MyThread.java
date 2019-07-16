package com.example.leoss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;

public class MyThread extends Thread{
    private int sum;
    public int getSum() {
        return sum;
    }
    //传入线程开始的地方
    private long start;
    //传入单个线程结束的地方
    private long end;
    //传入要下载的url路径
    private String urlPath;
    //传入要保存的file路径
    private String filePath;
    public MyThread() {
    }
    public MyThread(long start, long end, String urlPath, String filePath) {
        this.start = start;
        this.end = end;
        this.urlPath = urlPath;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {

            URL url = new URL(urlPath);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            //此条语句用时可搜索起作用
            connect.setAllowUserInteraction(true);
            //设置请求的的格式
            connect.setRequestProperty("Range", "bytes="+start+"-"+end);
            //得到服务器传过来的输入流
            InputStream input=connect.getInputStream();
            byte array[]=new byte[1024];
            File file=new File(filePath);
            //用随机读取文件，因为可以调整读取的位置
            RandomAccessFile randomAccessFile=new RandomAccessFile(file, "rw");
            //调整读取位置
            randomAccessFile.seek(start);
            int index=input.read(array);

            while(index!=-1){
                //对文件进行写的操作
                randomAccessFile.write(array, 0, index);
                sum+=index;
                index=input.read(array);
            }
            randomAccessFile.close();
            input.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
