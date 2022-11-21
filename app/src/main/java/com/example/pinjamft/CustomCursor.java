package com.example.pinjamft;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.pinjamft.DBHelper;
import com.example.pinjamft.DBHelper;

public class CustomCursor extends CursorAdapter {
    private LayoutInflater ly;
    private SparseBooleanArray mSelectedItems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursor(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_data_admin, viewGroup, false);
        CustomCursor.MyHolder holder = new CustomCursor.MyHolder();

        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListUsername = (TextView) v.findViewById(R.id.listUsername);
        holder.ListPassword = (TextView) v.findViewById(R.id.listPassword);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CustomCursor.MyHolder holder = (CustomCursor.MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.input_idUser)));
        holder.ListUsername.setText(cursor.getString(cursor.getColumnIndex(DBHelper.input_username)));
        holder.ListPassword.setText(cursor.getString(cursor.getColumnIndex(DBHelper.input_password)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListUsername;
        TextView ListPassword;
    }
}
