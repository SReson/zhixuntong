package com.example.leoss;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class ShouyeActivity extends AppCompatActivity
{
    private BottomNavigationView navigationView;

    private Fragment shouyeFragment;
    private Fragment sousuoFragment;
    private Fragment gerenFragment;

    public Fragment[] fragmentlist;

    private int lastFragment;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    /**
     * 权限获取
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);

        verifyStoragePermissions(ShouyeActivity.this);
        initFragment();

    }

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
     * 初始化
     */
    private void initFragment(){
        navigationView = (BottomNavigationView) findViewById(R.id.bnv_main);
        navigationView.setItemIconTintList(null);

        shouyeFragment = new ShouyeFragment();
        sousuoFragment = new SousuoFragment();
        gerenFragment = new GerenFragment();

        fragmentlist = new Fragment[]{shouyeFragment,sousuoFragment,gerenFragment};

        lastFragment = 0;
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main,shouyeFragment).show(shouyeFragment).commit();
        navigationView.setSelectedItemId(R.id.navigation_shouye2);
    }

    /**
     * 底部导航
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {
            resetToDefaultIcon();
            switch (menuItem.getItemId())
            {
                case R.id.navigation_shouye2:
                    if(lastFragment!=0){
                        switchFragment(lastFragment,0);
                        lastFragment = 0;
                    }
                    //设置按钮的
                    menuItem.setIcon(R.drawable.img_zhuye);
                    return true;
                case R.id.navigation_sousuo2:
                    if(lastFragment!=1){
//                        Toast.makeText(ShouyeActivity.this, "hahah", Toast.LENGTH_SHORT).show();
                        switchFragment(lastFragment,1);
                        lastFragment = 1;
                    }
                    //设置按钮的
                    menuItem.setIcon(R.drawable.img_sousuo);
                    return true;
                case R.id.navigation_geren2:
                    if(lastFragment!=2){
//                        Toast.makeText(ShouyeActivity.this, "hahah", Toast.LENGTH_SHORT).show();
                        switchFragment(lastFragment,2);
                        lastFragment = 2;
                    }
                    //设置按钮的
                    menuItem.setIcon(R.drawable.img_geren);
                    return true;

            }
            return false;
        }

    };

    /**
     *
     * @param lastFragment 表示点击按钮前的页面
     * @param index 表示点击按钮对应的页面
     */
    private void switchFragment(int lastFragment, int index)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragmentlist[lastFragment]);

        //判断transaction中是否加载过index对应的页面，若没加载过则加载
        if (fragmentlist[index].isAdded() == false)
        {
            transaction.add(R.id.fl_main, fragmentlist[index]);
        }
        //根据角标将fragment显示出来
        transaction.show(fragmentlist[index]).commitAllowingStateLoss();
    }

    /**
     * 重新配置每个按钮的图标
     */
    private void resetToDefaultIcon() {
        navigationView.getMenu().findItem(R.id.navigation_shouye2).setIcon(R.drawable.img_zhuye);
        navigationView.getMenu().findItem(R.id.navigation_sousuo2).setIcon(R.drawable.img_sousuo);
        navigationView.getMenu().findItem(R.id.navigation_geren2).setIcon(R.drawable.img_geren);
    }


}
