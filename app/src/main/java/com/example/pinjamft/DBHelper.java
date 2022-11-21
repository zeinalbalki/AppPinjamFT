package com.example.pinjamft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "application.db";
    public static final String table_login = "users";

    public static final String input_idUser = "_id";
    public static final String input_username = "Username";
    public static final String input_password = "Password";

    private SQLiteDatabase db;

    public DBHelper(Context context){
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_login + " ORDER BY " + input_idUser + " DESC ", null);
        return cur;
    }

    //GET 1 DATA BY ID
    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_login + " WHERE " + input_idUser + "=" + id, null);
        return cur;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryLogin = "CREATE TABLE " + table_login + "(" + input_idUser + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + input_username + " TEXT, " + input_password + " TEXT)";
        db.execSQL(queryLogin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_login);
    }

    public void insertDataLogin(ContentValues values){
        db.insert(table_login, null, values);
    }

    public boolean checkUser(String username, String password){
        String[] id = {input_idUser};
        SQLiteDatabase db = getReadableDatabase();
        String seleksi = input_username + "=?" + " and " + input_password + "=?";

        String[] seleksiData = {username, password};
        Cursor cursor = db.query(table_login, id, seleksi, seleksiData, null, null, null);

        int data = cursor.getCount();
        cursor.close();
        db.close();

        if(data > 0){
            return  true;
        } else {
            return false;
        }
    }
}
