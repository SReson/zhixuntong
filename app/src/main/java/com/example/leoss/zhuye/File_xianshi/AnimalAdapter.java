package com.example.leoss.zhuye.File_xianshi;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leoss.R;

import java.util.ArrayList;
import java.util.List;

//import activity.music.wcy.me.textexcelapp.R;

/**
 * Created by Administrator on 2019/3/25.
 */

public class AnimalAdapter extends BaseAdapter {
    private List<FileBean> mData;
    List<FileBean> backcitysBeanList;

    private Context mContext;
    private String path;
    MyFilter myFilter;


    public AnimalAdapter(List<FileBean> mData, Context mContext) {
        this.mData = mData;
        backcitysBeanList = mData;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_zhuye_tongxun_file, parent, false);
        }
        ImageView img_icon = (ImageView)convertView.findViewById(R.id.id_imgtou);
        TextView id_name = (TextView)convertView.findViewById(R.id.id_name);
        TextView id_says = (TextView)convertView.findViewById(R.id.id_says);

        img_icon.setBackgroundResource(mData.get(position).iconId);
        id_name.setText(mData.get(position).path);
        long  ae=mData.get(position).size;
      id_says.setText((ae/1000)+"KB");

        return convertView;
    }


    public Filter getFileter()
    {
        if (myFilter ==null)
        {
            myFilter = new MyFilter();
        }
        return myFilter;
    }


    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter{
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<FileBean> list ;

            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list  = backcitysBeanList;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (FileBean citysBean:backcitysBeanList)
                {
                    if (citysBean.getPath().contains(charSequence)){ //要匹配的item中的view
                        list.add(citysBean);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {

            mData = (List<FileBean>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }

}