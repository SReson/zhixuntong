package com.example.leoss;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leoss.zhuye.ParseXls.Caozuo_teacher;
import com.example.leoss.zhuye.ParseXls.Teacher_info;

public class SousuoFragment extends Fragment implements SearchView.OnQueryTextListener {
    static final String[] PROJECTION = new String[]{ContactsContract.RawContacts._ID,
            ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY};
    private ListView id_tongxun_lv1s;
    private SearchView id_tongxun_search1s;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private String contact_name,contact_phone;
    private Button id_tongxun_bt_add1s;

    private String username;
    private String sex;
    private String address;
    private String major;
    private String phone;



    MyCursorAdapter mAdapter;
    Cursor mCursor;
    Caozuo_teacher caozuo;
//    SimpleCursorAdapter mAdapter;
    View view;

    Boolean flag = true;
    Boolean flag2 = true;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //该View表示该碎片的主界面,最后要返回该view
        view = inflater.inflate(R.layout.fragment_sousuo, container, false);
        //找到主界面view后，就可以进行UI的操作了。
        //注意：因为主界面现在是view，所以在找寻控件时要用view.findViewById
//        TextView textView = view.findViewById(R.id.sousuo_text1);
        //textView.setText("");
        initView();
        data_show();

        return view;
    }

    private void data_show()
    {
        caozuo = new Caozuo_teacher(getActivity());
        if(flag) {
        mCursor = caozuo.xianshi_all();
        mAdapter = new MyCursorAdapter(getActivity(),mCursor);
//        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.layout_tongxun_phone, cursor,
//                new String[]{"name", "phone"}, new int[]{
//                R.id.id_layouttx_tv_name,R.id.id_layouttx_tv_phone
//        });
            id_tongxun_lv1s.setAdapter(mAdapter);
        }
        id_tongxun_lv1s.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(getActivity(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                TextView name = view.findViewById(R.id.id_layouttx_tv_name);
                TextView phone = view.findViewById(R.id.id_layouttx_tv_phone);
                contact_name = name.getText().toString();
                contact_phone = phone.getText().toString();
                confirm_call();

            }
        });
        id_tongxun_lv1s.setTextFilterEnabled(true);
        id_tongxun_search1s.setIconifiedByDefault(false);
        id_tongxun_search1s.setOnQueryTextListener(this);
        id_tongxun_search1s.setSubmitButtonEnabled(false);
    }

    private void confirm_call() {
        alert = null;
        builder = new AlertDialog.Builder(getActivity());
        alert = builder.setIcon(R.drawable.img_logo)
                .setTitle("系统提示：")
                .setMessage("确定拨打"+contact_name+"的电话?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        call_phone(contact_phone);
                        Toast.makeText(getActivity(), "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("容我三思", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "你点击了中立按钮~", Toast.LENGTH_SHORT).show();
                    }
                }).create();             //创建AlertDialog对象
        alert.show();

    }

    private void call_phone(String phone_number) {
            //获取输入的电话号码
//        phone = et_phone.getText().toString().trim();
            //创建打电话的意图
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }else{
                Intent intent = new Intent();
                //设置拨打电话的动作
                intent.setAction(Intent.ACTION_CALL);
                //设置拨打电话的号码
                intent.setData(Uri.parse("tel:" + phone_number));
                //开启打电话的意图
                startActivity(intent);
            }
        }

    private void initView() {
        id_tongxun_lv1s = view.findViewById(R.id.id_tongxun_lv);
        id_tongxun_search1s = view.findViewById(R.id.id_tongxun_search);
        id_tongxun_bt_add1s = view.findViewById(R.id.id_tongxun_bt_add);
        id_tongxun_bt_add1s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_user();
            }
        });

        id_tongxun_search1s.setOnQueryTextListener(this);

    }

    private void add_user() {
        //实例化布局
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_sousuo_addperson,null);
        //找到并对自定义布局中的控件进行操作的示例
        final EditText id_sousuo_add_ed_username1s = (EditText) view.findViewById(R.id.id_sousuo_add_ed_username);
        final RadioGroup id_sousuo_add_radbt_sex1s = (RadioGroup)view.findViewById(R.id.id_sousuo_add_radbt_sex);
        final RadioButton id_sousuo_add_radbt_man1s = (RadioButton)view.findViewById(R.id.id_sousuo_add_radbt_man);
        final RadioButton id_sousuo_add_radbt_woman1s = (RadioButton)view.findViewById(R.id.id_sousuo_add_radbt_woman);
//        final EditText id_sousuo_add_ed_sex1s = (EditText) view.findViewById(R.id.id_sousuo_add_ed_sex);
        final EditText id_sousuo_add_ed_address1s = (EditText) view.findViewById(R.id.id_sousuo_add_ed_address);
        final EditText id_sousuo_add_ed_major1s = (EditText) view.findViewById(R.id.id_sousuo_add_ed_major);
        final EditText id_sousuo_add_ed_phone1s = view.findViewById(R.id.id_sousuo_add_ed_phone);

//        final String username = id_sousuo_add_ed_username1s.getText().toString().trim();
//        final String sex = id_sousuo_add_ed_sex1s.getText().toString().trim();
//        final String address = id_sousuo_add_ed_address1s.getText().toString().trim();
//        final String major = id_sousuo_add_ed_major1s.getText().toString().trim();
//        final String phone = id_sousuo_add_ed_phone1s.getText().toString().trim();
        //创建对话框
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setIcon(R.drawable.img_logo);//设置图标
        dialog.setTitle("添加联系人");//设置标题
        dialog.setView(view);//添加布局
        //设置按键
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                username = id_sousuo_add_ed_username1s.getText().toString();
                id_sousuo_add_radbt_sex1s.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        Toast.makeText(getActivity(), "hahhehe", Toast.LENGTH_SHORT).show();
//                        if(id_sousuo_add_radbt_man1s.getId()==radioGroup.getCheckedRadioButtonId()){
//                            Toast.makeText(getActivity(), "你选择了男", Toast.LENGTH_SHORT).show();
//                        }else if (id_sousuo_add_radbt_woman1s.getId()==radioGroup.getCheckedRadioButtonId()) {
//                            Toast.makeText(getActivity(), "你选择了女", Toast.LENGTH_SHORT).show();
//                        }
                            // RadioButton radioButton = view.findViewById(i);
//                        sex = radioButton.getText().toString();
//                        id_sousuo_add_ed_major1s.setText(sex);
//                        Toast.makeText(getContext(), "你选择了"+sex, Toast.LENGTH_SHORT).show();
                    }
                });
//                sex = id_sousuo_add_ed_sex1s.getText().toString();
                address = id_sousuo_add_ed_address1s.getText().toString();
                major = id_sousuo_add_ed_major1s.getText().toString();
                phone = id_sousuo_add_ed_phone1s.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(phone)){
                    Toast.makeText(getActivity(), "请输入正确的姓名和手机号！", Toast.LENGTH_SHORT).show();
                }else{
                    Caozuo_teacher caozuo_teacher = new Caozuo_teacher(getActivity());
                    Teacher_info teacher_info = new Teacher_info(username,sex,address,major,phone,"哈","嗯");
//                Teacher_info teacher_info = new Teacher_info(username,sex,address,major,phone,"哈","嗯");
//                Teacher_info teacher_info = new Teacher_info(username,sex,address,major,phone,"哈","嗯");
                    caozuo_teacher.add(teacher_info);
                    Toast.makeText(getActivity(), username+"信息添加成功！", Toast.LENGTH_SHORT).show();
                    flag = true;
                    data_show();
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "容我三思", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "先不加了！", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        if (id_tongxun_search1s != null) {
            // 得到输入管理对象
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            if (imm != null) {
                // 这将让键盘在所有的情况下都被隐藏
                imm.hideSoftInputFromWindow(id_tongxun_search1s.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
            }
            id_tongxun_search1s.clearFocus(); // 不获取焦点
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.length()!=0){
            Cursor cursor = caozuo.filter_show("name like '%"+s+"%'"+"or phone like '%"+s+"%'"+"or address like '%"+s+"%'");
            mAdapter = new MyCursorAdapter(getActivity(),cursor);
            id_tongxun_lv1s.setAdapter(mAdapter);
        }else{
            mCursor = caozuo.xianshi_all();
            mAdapter = new MyCursorAdapter(getActivity(),mCursor);
            id_tongxun_lv1s.setAdapter(mAdapter);
        }

        return true;
    }
}