package com.example.leoss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.leoss.duanxingxufa.MsgMobanActivity;
import com.example.leoss.zhuye.TongxunlrActivity;

import java.util.ArrayList;

public class ShouyeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    public View view;

    private Button id_zhuye_bt_tongxun1s,button_a2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //该View表示该碎片的主界面,最后要返回该view
         view = inflater.inflate(R.layout.fragment_shouye, container, false);
        //找到主界面view后，就可以进行UI的操作了。
        //注意：因为主界面现在是view，所以在找寻控件时要用view.findViewById

        // 初始化布局 View视图
        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }

        }.start();


        return view;
    }
    ///////////////////////////

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.id_zhuye_bt_tongxun:
                intent.setClass(getActivity(),TongxunlrActivity.class);
                break;
            case R.id.button2:
                intent.setClass(getActivity(),MsgMobanActivity.class);
                break;
        }
        startActivity(intent);
    }

    /***
     * 当即将销毁时 isRunning = false;
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    /**
     * ID初始化+事件监听
     */
    private void initViews() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
       button_a2=view.findViewById(R.id.button2);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
        ll_point_container = (LinearLayout) view.findViewById(R.id.ll_point_container);
        tv_desc = (TextView)    view.findViewById(R.id.tv_desc);


        id_zhuye_bt_tongxun1s = view.findViewById(R.id.id_zhuye_bt_tongxun);
        id_zhuye_bt_tongxun1s.setOnClickListener(this);
        button_a2.setOnClickListener(this);

    }

    /**
     * 初始化要显示的数据
     */
    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04, R.drawable.img05};

        // 文本描述
        contentDescs = new String[]{
                "第一页",
                "第二页",
                "第三页",
                "第四页",
                "第五页"
        };

        /**
         *  初始化要展示的5个ImageView
         */

        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.img_quan);
            layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }
//适配器
    private void initAdapter()
    {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        //int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }


    /**
     * Vipager
     */
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//       System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//       newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    public void onPageSelected(int position)
    {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

//    for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//       View childAt = ll_point_container.getChildAt(position);
//       childAt.setEnabled(position == i);
//    }
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }




}

