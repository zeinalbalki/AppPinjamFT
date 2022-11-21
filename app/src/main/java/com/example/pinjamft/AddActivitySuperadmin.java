package com.example.pinjamft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinjamft.adapter.DBHelperPeminjaman;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivitySuperadmin extends AppCompatActivity {

    DBHelperPeminjaman dbHelper;
    TextView TvStatus;
    Button BtnProses, BtnPersetujuan;
    EditText TxID, TxNamaPeminjam, TxNIM, TxLembaga, TxPerihal, TxJenisPengajuan, TxTglPengajuan, TxTglPeminjaman,
            TxTglSelesai, TxStatus;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_superadmin);

        dbHelper = new DBHelperPeminjaman(this);

        id = getIntent().getLongExtra(DBHelperPeminjaman.row_id, 0);

        TxID = (EditText)findViewById(R.id.txID);
        TxNamaPeminjam = (EditText)findViewById(R.id.txNamaPeminjam);
        TxNIM = (EditText)findViewById(R.id.txNIM);
        TxLembaga = (EditText)findViewById(R.id.txLembaga);
        TxPerihal = (EditText)findViewById(R.id.txPerihal);
        TxJenisPengajuan = (EditText)findViewById(R.id.txJenisPengajuan);
        TxTglPengajuan = (EditText)findViewById(R.id.txTglPengajuan);
        TxTglPeminjaman = (EditText)findViewById(R.id.txTglPeminjaman);
        TxTglSelesai = (EditText)findViewById(R.id.txTglSelesai);
        TxStatus = (EditText)findViewById(R.id.txStatus);

        TvStatus = (TextView)findViewById(R.id.tVStatus);
        BtnProses = (Button)findViewById(R.id.btnProses);
        BtnPersetujuan = (Button)findViewById(R.id.btnPersetujuan);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        getData();

        TxTglPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        TxTglSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog2();
            }
        });

        BtnPersetujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesPersetujuan();
            }
        });

        BtnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesKembali();
            }
        });

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
    }

    private void prosesPersetujuan() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivitySuperadmin.this);
        builder.setMessage("Setujui pengajuan ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Proses", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idpinjam = TxID.getText().toString().trim();
                String kembali = "Disetujui";

                ContentValues values = new ContentValues();

                values.put(DBHelperPeminjaman.row_status, kembali);
                dbHelper.updateData(values, id);
                Toast.makeText(AddActivitySuperadmin.this, "Pengajuan Disetujui", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void prosesKembali() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivitySuperadmin.this);
        builder.setMessage("Selesai Peminjaman ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Proses", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idpinjam = TxID.getText().toString().trim();
                String kembali = "Selesai";

                ContentValues values = new ContentValues();

                values.put(DBHelperPeminjaman.row_status, kembali);
                dbHelper.updateData(values, id);
                Toast.makeText(AddActivitySuperadmin.this, "Proses Peminjaman Sudah Selesai", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TxTglPeminjaman.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDateDialog2() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TxTglSelesai.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData() {
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String tglPinjam = sdf1.format(c1.getTime());
        TxTglPengajuan.setText(tglPinjam);

        Cursor cur = dbHelper.oneData(id);
        if(cur.moveToFirst()){
            @SuppressLint("Range") String idpinjam = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_id));
            @SuppressLint("Range") String namaPeminjam = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_nama_peminjam));
            @SuppressLint("Range") String nimPeminjam = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_nim_peminjam));
            @SuppressLint("Range") String lembaga = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_lembaga));
            @SuppressLint("Range") String perihal = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_perihal));
            @SuppressLint("Range") String jenisPengajuan = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_jenis_pengajuan));
            @SuppressLint("Range") String tanggalPengajuan = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_tanggal_pengajuan));
            @SuppressLint("Range") String tanggalPeminjaman = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_tanggal_peminjaman));
            @SuppressLint("Range") String tanggalSelesai = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_tanggal_selesai));
            @SuppressLint("Range") String status = cur.getString(cur.getColumnIndex(DBHelperPeminjaman.row_status));

            TxID.setText(idpinjam);
            TxNamaPeminjam.setText(namaPeminjam);
            TxNIM.setText(nimPeminjam);
            TxLembaga.setText(lembaga);
            TxPerihal.setText(perihal);
            TxJenisPengajuan.setText(jenisPengajuan);
            TxTglPengajuan.setText(tanggalPengajuan);
            TxTglPeminjaman.setText(tanggalPeminjaman);
            TxTglSelesai.setText(tanggalSelesai);
            TxStatus.setText(status);

            if (TxID.equals("")){
                TvStatus.setVisibility(View.GONE);
                TxStatus.setVisibility(View.GONE);
                BtnProses.setVisibility(View.GONE);
            }else{
                TvStatus.setVisibility(View.VISIBLE);
                TxStatus.setVisibility(View.VISIBLE);
                BtnProses.setVisibility(View.VISIBLE);
            }

            if(status.equals("Belum Disetujui")){
                BtnProses.setVisibility(View.GONE);
                BtnPersetujuan.setVisibility(View.VISIBLE);
            }else if(status.equals("Disetujui")){
                BtnProses.setVisibility(View.VISIBLE);
                BtnPersetujuan.setVisibility(View.GONE);
            } else {
                BtnProses.setVisibility(View.GONE);
                BtnPersetujuan.setVisibility(View.GONE);
                TxNamaPeminjam.setEnabled(false);
                TxNIM.setEnabled(false);
                TxLembaga.setEnabled(false);
                TxPerihal.setEnabled(false);
                TxJenisPengajuan.setEnabled(false);
                TxTglPengajuan.setEnabled(false);
                TxTglPeminjaman.setEnabled(false);
                TxTglSelesai.setEnabled(false);
                TxStatus.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        String idpinjam = TxID.getText().toString().trim();
        String status = TxStatus.getText().toString().trim();

        MenuItem itemDelete = menu.findItem(R.id.action_delete);
        MenuItem itemClear = menu.findItem(R.id.action_clear);
        MenuItem itemSave = menu.findItem(R.id.action_save);

        if (idpinjam.equals("")){
            itemDelete.setVisible(false);
            itemClear.setVisible(true);
        }else {
            itemDelete.setVisible(true);
            itemClear.setVisible(false);
        }

        if(status.equals("Selesai")){
            itemSave.setVisible(false);
            itemDelete.setVisible(true);
            itemClear.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                insertAndUpdate();
        }
        switch (item.getItemId()){
            case R.id.action_clear:
                TxNamaPeminjam.setText("");
                TxNIM.setText("");
                TxLembaga.setText("");
                TxPerihal.setText("");
                TxJenisPengajuan.setText("");
                TxTglPeminjaman.setText("");
                TxTglSelesai.setText("");
        }
        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivitySuperadmin.this);
                builder.setMessage("Data ini akan dihapus");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(id);
                        Toast.makeText(AddActivitySuperadmin.this, "Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertAndUpdate(){
        String idpinjam = TxID.getText().toString().trim();
        String namaPeminjam = TxNamaPeminjam.getText().toString().trim();
        String nimPeminjam = TxNIM.getText().toString().trim();
        String lembagaPeminjam = TxLembaga.getText().toString().trim();
        String perihal = TxPerihal.getText().toString().trim();
        String jenisPengajuan = TxJenisPengajuan.getText().toString().trim();
        String tanggalPengajuan = TxTglPengajuan.getText().toString().trim();
        String tanggalPeminjaman = TxTglPeminjaman.getText().toString().trim();
        String tanggalSelesai = TxTglSelesai.getText().toString().trim();
        String status = "Belum Disetujui";

        ContentValues values = new ContentValues();

        values.put(DBHelperPeminjaman.row_nama_peminjam, namaPeminjam);
        values.put(DBHelperPeminjaman.row_nim_peminjam, nimPeminjam);
        values.put(DBHelperPeminjaman.row_lembaga, lembagaPeminjam);
        values.put(DBHelperPeminjaman.row_perihal, perihal);
        values.put(DBHelperPeminjaman.row_jenis_pengajuan, jenisPengajuan);
        values.put(DBHelperPeminjaman.row_tanggal_pengajuan, tanggalPengajuan);
        values.put(DBHelperPeminjaman.row_tanggal_peminjaman, tanggalPeminjaman);
        values.put(DBHelperPeminjaman.row_tanggal_selesai, tanggalSelesai);
        values.put(DBHelperPeminjaman.row_status, status);

        if (namaPeminjam.equals("") || nimPeminjam.equals("") || lembagaPeminjam.equals("") || perihal.equals("") || jenisPengajuan.equals("") || tanggalPengajuan.equals("")
                || tanggalPeminjaman.equals("") || tanggalSelesai.equals("")){
            Toast.makeText(AddActivitySuperadmin.this, "Isi Data Dengan Lengkap", Toast.LENGTH_SHORT).show();
        }else {
            if(idpinjam.equals("")){
                values.put(DBHelperPeminjaman.row_tanggal_pengajuan, tanggalPengajuan);
                dbHelper.insertData(values);
            }else {
                dbHelper.updateData(values, id);
            }

            Toast.makeText(AddActivitySuperadmin.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}