package com.example.pinjamft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.pinjamft.adapter.CustomCursorAdapter;
import com.example.pinjamft.adapter.DBHelperPeminjaman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PeminjamanSuperadmin extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogChoice.DialogChoiceListener {

    ListView Is;
    DBHelperPeminjaman dbHelper;
    Context context;
    int listData;
    SharedPreferences viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman_superadmin);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeminjamanSuperadmin.this, AddActivitySuperadmin.class));
            }
        });
        FloatingActionButton fabAdmin = (FloatingActionButton) findViewById(R.id.fabAdmin);
        fabAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeminjamanSuperadmin.this, KelolaAdmin.class));
            }
        });

        dbHelper = new DBHelperPeminjaman(this);
        Is = (ListView)findViewById(R.id.list_pinjam);
        Is.setOnItemClickListener(this);

        viewData = getSharedPreferences("currentListView", 0);
        listData = viewData.getInt("currentListView", 0);
        setupListView();

    }

    private void setupListView() {
        if (listData == 0){
            allData();
        }else if (listData == 1){
            dataBelumDisetujui();
        }else if (listData == 2){
            dataDisetujui();
        }else if (listData == 3){
            dataSelesai();
        }
    }

    public void allData(){
        Cursor cursor = dbHelper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    public void dataBelumDisetujui(){
        Cursor cursor = dbHelper.dataBelumDisetujui();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    public void dataDisetujui(){
        Cursor cursor = dbHelper.dataDisetujui();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    public void dataSelesai(){
        Cursor cursor = dbHelper.dataSelesai();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long I) {
        TextView getID = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = dbHelper.oneData(id);
        cur.moveToFirst();

        Intent idpinjam = new Intent(PeminjamanSuperadmin.this, AddActivitySuperadmin.class);
        idpinjam.putExtra(DBHelperPeminjaman.row_id, id);
        startActivity(idpinjam);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setupListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Intent intent = new Intent(PeminjamanSuperadmin.this, HomeUser.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.sort){
            DialogFragment dialogFragment = new DialogChoice();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getSupportFragmentManager(), "Choice Dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        SharedPreferences.Editor editor = viewData.edit();

        if (position == 0){
            editor.putInt("currentListView", 0);
            editor.apply();

            allData();
        }else if (position == 1){
            editor.putInt("currentListView", 1);
            editor.apply();

            dataBelumDisetujui();
        }else if (position == 2){
            editor.putInt("currentListView", 2);
            editor.apply();

            dataDisetujui();
        }else if (position == 3){
            editor.putInt("currentListView", 2);
            editor.apply();

            dataSelesai();
        }
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}