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

public class KelolaAdmin extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogChoice.DialogChoiceListener {

    ListView Is;
    DBHelper dbHelper;
    Context context;
    int listData;
    SharedPreferences viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_admin);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaAdmin.this, RegisterActivity.class));
            }
        });

        dbHelper = new DBHelper(this);
        Is = (ListView)findViewById(R.id.list_admin);
        Is.setOnItemClickListener(this);

        viewData = getSharedPreferences("currentListView", 0);
        listData = viewData.getInt("currentListView", 0);
        setupListView();

    }

    private void setupListView() {
        if (listData == 0){
            allData();
        }
    }

    public void allData(){
        Cursor cursor = dbHelper.allData();
        CustomCursor customCursor = new CustomCursor(this, cursor, 1);
        Is.setAdapter(customCursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kelola_admin, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long I) {
        TextView getID = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = dbHelper.oneData(id);
        cur.moveToFirst();

        Intent idpinjam = new Intent(KelolaAdmin.this, TambahAdmin.class);
        idpinjam.putExtra(DBHelper.input_idUser, id);
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
            Intent intent = new Intent(KelolaAdmin.this, PeminjamanSuperadmin.class);
            startActivity(intent);
            return true;
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
        }
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}