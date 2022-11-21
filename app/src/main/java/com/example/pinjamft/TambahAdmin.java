package com.example.pinjamft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import com.example.pinjamft.DBHelper;
import com.example.pinjamft.adapter.DBHelperPeminjaman;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahAdmin extends AppCompatActivity {



    DBHelper dbHelper;
    EditText TxID, TxUsername, TxPassword;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_admin);

        dbHelper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelperPeminjaman.row_id, 0);

        TxID = (EditText)findViewById(R.id.txID);
        TxUsername = (EditText)findViewById(R.id.txUsername);
        TxPassword = (EditText)findViewById(R.id.txPassword);

        getData();

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
    }

    private void getData() {
        Cursor cur = dbHelper.oneData(id);
        if(cur.moveToFirst()){
            @SuppressLint("Range") String idpinjam = cur.getString(cur.getColumnIndex(DBHelper.input_idUser));
            @SuppressLint("Range") String username = cur.getString(cur.getColumnIndex(DBHelper.input_username));
            @SuppressLint("Range") String password = cur.getString(cur.getColumnIndex(DBHelper.input_password));

            TxID.setText(idpinjam);
            TxUsername.setText(username);
            TxPassword.setText(password);
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
                TxUsername.setText("");
                TxPassword.setText("");
        }
        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(TambahAdmin.this);
                builder.setMessage("Data ini akan dihapus");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(id);
                        Toast.makeText(TambahAdmin.this, "Terhapus", Toast.LENGTH_SHORT).show();
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
        String username = TxUsername.getText().toString().trim();
        String password = TxPassword.getText().toString().trim();

        ContentValues values = new ContentValues();

        values.put(DBHelper.input_idUser, idpinjam);
        values.put(DBHelper.input_username, username);
        values.put(DBHelper.input_password, password);

        if (username.equals("") || password.equals("")){
            Toast.makeText(TambahAdmin.this, "Isi Data Dengan Lengkap", Toast.LENGTH_SHORT).show();
        }else {
            if(idpinjam.equals("")){
                dbHelper.insertDataLogin(values);
            }else {
                dbHelper.updateData(values, id);
            }

            Toast.makeText(TambahAdmin.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}