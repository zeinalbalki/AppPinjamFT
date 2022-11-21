package com.example.pinjamft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DBHelper helper;
    EditText RegUsername, RegPassword, ConfPassword;
    Button BtnRegister;
    TextView ToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = new DBHelper(this);

        RegUsername = (EditText) findViewById(R.id.regUsername);
        RegPassword = (EditText) findViewById(R.id.regPassword);
        ConfPassword = (EditText) findViewById(R.id.regConfPassword);

        BtnRegister = (Button) findViewById(R.id.btnRegister);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = RegUsername.getText().toString().trim();
                String password = RegPassword.getText().toString().trim();
                String confPassword = ConfPassword.getText().toString().trim();

                ContentValues values = new ContentValues();
                if(password.equals("") || username.equals("")){
                    Toast.makeText(RegisterActivity.this, "Data harus di isi", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confPassword)){
                    Toast.makeText(RegisterActivity.this, "Password tidak cocok !", Toast.LENGTH_SHORT).show();
                } else {
                    values.put(DBHelper.input_username, username);
                    values.put(DBHelper.input_password, password);

                    helper.insertDataLogin(values);

                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, KelolaAdmin.class);
                    startActivity(intent);
                }
                clear();
            }
        });

        ToLogin = (TextView) findViewById(R.id.toLogin);
        ToLogin.setText(fromHtml("Kembali ke Menu " +
                "</font><font color='#3b5988'>Kelola Admin</font>"));
        ToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, KelolaAdmin.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "Halaman Kelola Admin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }

        return result;
    }

    private void clear(){
        RegUsername.setText("");
        RegPassword.setText("");
        ConfPassword.setText("");
    }
}