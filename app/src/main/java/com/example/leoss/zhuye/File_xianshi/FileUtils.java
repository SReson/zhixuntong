package com.example.leoss.zhuye.File_xianshi;


import com.example.leoss.R;

import java.io.File;

//import activity.music.wcy.me.textexcelapp.R;

class FileUtils
{
    public static final int TYPE_DOC = 0;
    /**apk类型*/
    public static final int TYPE_APK = 1;
    /**压缩包类型*/
    public static final int TYPE_ZIP = 2;
    public static int getFileIconByPath(String path){
        path = path.toLowerCase();
        int iconId = R.drawable.file_wenjian;
        if (path.endsWith(".txt")){
            iconId = R.drawable.file_wenjian;
        }else if(path.endsWith(".doc") || path.endsWith(".docx")){
            iconId = R.drawable.file_word1;
        }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
            iconId = R.drawable.file_excel;
        }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
            iconId = R.drawable.file_ppt;
        }else if(path.endsWith(".xml")){
            iconId = R.drawable.file_wenjian;
        }else if(path.endsWith(".htm") || path.endsWith(".html")){
            iconId = R.drawable.file_wenjian;
        }
        return iconId;
    }
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
//    public static int getFileType(String path)
//    {
//        path = path.toLowerCase();
//        if (path.endsWith(".doc") || path.endsWith(".docx") || path.endsWith(".xls") || path.endsWith(".xlsx")
//                || path.endsWith(".ppt") || path.endsWith(".pptx")) {
//            return TYPE_DOC;
//        }else if (path.endsWith(".apk")) {
//            return TYPE_APK;
//        }else if (path.endsWith(".zip") || path.endsWith(".rar") || path.endsWith(".tar") || path.endsWith(".gz")) {
//            return TYPE_ZIP;
//        }else{
//            return -1;
//        }
//    }

    public static int getFileType(String path)
    {
        path = path.toLowerCase();
        if (path.endsWith(".xls") || path.endsWith(".xlsx")) {
            return TYPE_DOC;
        }else if (path.endsWith(".apk")) {
            return TYPE_APK;
        }else if (path.endsWith(".zip") || path.endsWith(".rar") || path.endsWith(".tar") || path.endsWith(".gz")) {
            return TYPE_ZIP;
        }else{
            return -1;
        }
    }

}
