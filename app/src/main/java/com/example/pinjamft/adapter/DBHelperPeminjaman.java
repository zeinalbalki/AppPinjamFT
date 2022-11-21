package com.example.pinjamft.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperPeminjaman extends SQLiteOpenHelper {

    public static final String database_name = "db_peminjaman";
    public static final String tabel_name = "tabel_pinjamft";

    public static final String row_id = "_id";
    public static final String row_nama_peminjam = "NamaPeminjam";
    public static final String row_nim_peminjam = "NimPeminjam";
    public static final String row_lembaga = "Lembaga";
    public static final String row_perihal = "Perihal";
    public static final String row_jenis_pengajuan = "JenisPengajuan";
    public static final String row_tanggal_pengajuan = "TanggalPengajuan";
    public static final String row_tanggal_peminjaman = "TanggalPeminjaman";
    public static final String row_tanggal_selesai = "TanggalSelesai";
    public static final String row_status = "Status";

    private SQLiteDatabase db2;

    public DBHelperPeminjaman(Context context) {
        super(context, database_name, null, 2);
        db2 = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tabel_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nama_peminjam + " TEXT," + row_nim_peminjam + " TEXT," + row_lembaga + " TEXT," +
                row_perihal + " TEXT," + row_jenis_pengajuan + " TEXT," + row_tanggal_pengajuan + " TEXT," +
                row_tanggal_peminjaman + " TEXT," + row_tanggal_selesai + " TEXT," + row_status + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabel_name);
    }

    //Get Data Belum Disetujui
    public Cursor dataBelumDisetujui(){
        Cursor cur = db2.rawQuery("SELECT * FROM " + tabel_name + " WHERE " + row_status + "=" + "'Belum Disetujui'", null);
        return cur;
    }

    //Get Data Disetujui
    public Cursor dataDisetujui(){
        Cursor cur = db2.rawQuery("SELECT * FROM " + tabel_name + " WHERE " + row_status + "=" + "'Disetujui'", null);
        return cur;
    }

    //Get Data Selesai
    public Cursor dataSelesai(){
        Cursor cur = db2.rawQuery("SELECT * FROM " + tabel_name + " WHERE " + row_status + "=" + "'Selesai'", null);
        return cur;
    }

    //Get All SQLite Data
    public Cursor allData(){
        Cursor cur = db2.rawQuery("SELECT * FROM " + tabel_name + " ORDER BY " + row_id + " DESC ", null);
        return cur;
    }

    //GET 1 DATA BY ID
    public Cursor oneData(long id){
        Cursor cur = db2.rawQuery("SELECT * FROM " + tabel_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db2.insert(tabel_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db2.update(tabel_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db2.delete(tabel_name, row_id + "=" + id, null);
    }
}
