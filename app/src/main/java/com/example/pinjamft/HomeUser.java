package com.example.pinjamft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeUser extends AppCompatActivity {

    Button BtnAjukan, BtnLihatSemuaPengajuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        BtnAjukan = (Button) findViewById(R.id.btnAjukan);
        BtnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeUser.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}