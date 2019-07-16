package com.example.leoss;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.leoss.R;

public class MyCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mInflater.inflate(R.layout.layout_tongxun_phone,viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.id_layouttx_tv_name);
        TextView phone = (TextView) view.findViewById(R.id.id_layouttx_tv_phone);

        Log.d("xxxx", "cur="+cursor.getCount()+",c_count="+cursor.getColumnCount());
        name.setText(cursor.getString(1));
        phone.setText(cursor.getString(5));

        //super.bindView(view, context, cursor);
    }
}
