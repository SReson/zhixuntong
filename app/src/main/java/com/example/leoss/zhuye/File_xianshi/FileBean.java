package com.example.leoss.zhuye.File_xianshi;

public class FileBean {
    /**
     * 文件的路径
     */
    public String path;
    /**
     * 文件图片资源的id，drawable或mipmap文件中已经存放doc、xml、xls等文件的图片
     */
    public int iconId;

    public long size;


    public FileBean(String path, int iconId, long size) {
        this.path = path;
        this.iconId = iconId;
        this.size=size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
