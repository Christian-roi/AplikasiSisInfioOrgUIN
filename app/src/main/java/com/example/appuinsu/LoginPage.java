package com.example.appuinsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button login = findViewById(R.id.btLogin);
        Button guest = findViewById(R.id.btGuest);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuAdmin = new Intent(LoginPage.this, AdminMenu.class);
                startActivity(menuAdmin);
                finish();
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuUser = new Intent(LoginPage.this, UserMenu.class);
                startActivity(menuUser);
                finish();
            }
        });

    }

    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}