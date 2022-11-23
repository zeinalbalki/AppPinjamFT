package com.example.pinjamft.adapter;

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

import com.example.pinjamft.R;

import org.w3c.dom.Text;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater ly;
    private SparseBooleanArray mSelectedItems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_data, viewGroup, false);
        MyHolder holder = new MyHolder();

        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListNamaPeminjam = (TextView) v.findViewById(R.id.listNamaPeminjam);
        holder.ListNimPeminjam = (TextView) v.findViewById(R.id.listNimPeminjam);
        holder.ListLembaga = (TextView) v.findViewById(R.id.listLembaga);
        holder.ListPerihal = (TextView) v.findViewById(R.id.listPerihal);
        holder.ListJenisPengajuan = (TextView) v.findViewById(R.id.listJenisPengajuan);
        holder.ListTanggalPengajuan = (TextView) v.findViewById(R.id.listTanggalPengajuan);
        holder.ListTanggalPeminjaman = (TextView) v.findViewById(R.id.listTanggalPeminjaman);
        holder.ListStatus = (TextView)v.findViewById(R.id.listStatus);

        v.setTag(holder);
        return v;
    }

    @SuppressLint({"Range", "SetTextI18n"})
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_id)));
        holder.ListNamaPeminjam.setText("Nama Peminjam : " + cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_nama_peminjam)));
        holder.ListNimPeminjam.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_nim_peminjam)));
        holder.ListLembaga.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_lembaga)));
        holder.ListPerihal.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_perihal)));
        holder.ListJenisPengajuan.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_jenis_pengajuan)));
        holder.ListTanggalPengajuan.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_tanggal_pengajuan)));
        holder.ListTanggalPeminjaman.setText(cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_tanggal_peminjaman)) +
                " sampai " + cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_tanggal_selesai)));
        holder.ListStatus.setText("Status: " + cursor.getString(cursor.getColumnIndex(DBHelperPeminjaman.row_status)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListNamaPeminjam;
        TextView ListNimPeminjam;
        TextView ListLembaga;
        TextView ListPerihal;
        TextView ListJenisPengajuan;
        TextView ListTanggalPengajuan;
        TextView ListTanggalPeminjaman;
        TextView ListStatus;
    }
}

