package com.example.leoss.zhuye.File_xianshi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.SearchView;


import com.example.leoss.R;
import com.example.leoss.zhuye.ParseXls.ToFileXlsActivity;

import java.util.ArrayList;
import java.util.List;

//import activity.music.wcy.me.textexcelapp.File_Activity;
//import activity.music.wcy.me.textexcelapp.R;


public class ToFileActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{

    private Context mContext;
    List<FileBean> fileBeans;
    private AnimalAdapter mAdapter = null;
    private ListView list_animal;
    private SearchView id_file_search1s;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

//第二个界面-------------------------------------

    public String path;

    EditText id_file_ed_model1s;
    String speng;
    int DX_A;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_file);
        fileBeans =getFilesByType(0);
            DX_A=getIntent().getIntExtra("DX",0);
        id_file_search1s = (SearchView) findViewById(R.id.id_file_search);

        id_file_search1s.setOnQueryTextListener(this);

        mContext = this;
        list_animal = (ListView) findViewById(R.id.list_view);

        mAdapter = new AnimalAdapter(fileBeans, mContext);
        list_animal.setAdapter(mAdapter);
          speng=getIntent().getStringExtra("smsModel");

//        Intent intent=getIntent();
//        path=intent.getStringExtra("path");


        list_animal.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TextView id_name = view.findViewById(R.id.id_name);
                String path = id_name.getText().toString();
                Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show();


                    Intent intent=new Intent();
                    intent.putExtra("path",path);
                    intent.putExtra("DX",DX_A);
                    intent.putExtra("speng",speng);
                    intent.setClass(ToFileActivity.this,ToFileXlsActivity.class);

                    Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show();
                    startActivity(intent);



//                FileBean path= fileBeans.get(i);
//                Intent intent=new Intent(File_item_activit.this,File_Activity.class);
//                intent.putExtra("path",path.getPath());
//                Toast.makeText(mContext, path.getPath(), Toast.LENGTH_SHORT).show();
//                startActivity(intent);

            }
        });


        list_animal.setTextFilterEnabled(true);
        id_file_search1s.setIconifiedByDefault(false);
        id_file_search1s.setOnQueryTextListener(this);
//        id_file_search1s.setSubmitButtonEnabled(true);
        id_file_search1s.setSubmitButtonEnabled(false);



    }



    public List<FileBean> getFilesByType(int fileType)
    {
        List<FileBean> files = new ArrayList<FileBean>();

        Cursor c = null;

        c = getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "_size"}, null, null, null);
        int dataindex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
        int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

        while (c.moveToNext())
        {
            path = c.getString(dataindex);
            if (FileUtils.getFileType(path) == fileType)
            {
                if (!FileUtils.isExists(path))
                {
                    continue;
                }
                long size = c.getLong(sizeindex);

                FileBean fileBean = new FileBean(path, FileUtils.getFileIconByPath(path),size);
                files.add(fileBean);
            }
        }
        return files;

    }


    @Override
    public boolean onQueryTextSubmit(String query)
    {
        if (id_file_search1s != null) {
            // 得到输入管理对象
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                // 这将让键盘在所有的情况下都被隐藏
                imm.hideSoftInputFromWindow(id_file_search1s.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
            }
            id_file_search1s.clearFocus(); // 不获取焦点
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String s)
    {
        if(s == null || s.length() == 0)
        {
            list_animal.clearTextFilter();
            mAdapter.getFileter().filter("");
        }else
            {

            mAdapter.getFileter().filter(s);
//            list_animal.setFilterText(s);
            }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
