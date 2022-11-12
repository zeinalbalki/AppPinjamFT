package com.example.pinjamft;

import static android.text.Html.fromHtml;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    DBHelper helper;
    EditText LoginUsername, LoginPassword;
    Button BtnLogin;
    TextView ToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new DBHelper(this);
        LoginUsername = (EditText) findViewById(R.id.loginUsername);
        LoginPassword = (EditText) findViewById(R.id.loginPassword);
        BtnLogin = (Button) findViewById(R.id.btnLogin);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = LoginUsername.getText().toString().trim();
                String password = LoginPassword.getText().toString().trim();

                Boolean data = helper.checkUser(username, password);

                if(data == true){
                    Intent intent;
                    if(username == "admin"){
                        intent = new Intent(LoginActivity.this, MenuAdmin.class);
                    } else {
                        intent = new Intent(LoginActivity.this, MenuAdmin.class);
                    }
                    startActivity(intent);

                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    clear();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ToRegister = (TextView) findViewById(R.id.toRegister);
        ToRegister.setText(fromHtml("Don't have account? Create " +
                "</font><font color='#3b5988'>Account</font>"));

        ToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Halaman Register", Toast.LENGTH_SHORT).show();
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
        LoginUsername.setText("");
        LoginPassword.setText("");
    }
}