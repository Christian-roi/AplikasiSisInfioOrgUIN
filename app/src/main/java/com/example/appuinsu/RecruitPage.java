package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecruitPage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_page);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);

        EditText nama = findViewById(R.id.etNama);
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        EditText konfirm = findViewById(R.id.etKonfirmPass);
        Button daftar = findViewById(R.id.btnDaftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                String strPassConf = konfirm.getText().toString();
                String role = "calon";

                if(strNama.trim().isEmpty() || strUsername.trim().isEmpty() || strPass.trim().isEmpty() || strPassConf.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Harap isi seluruh data yang diminta!", Toast.LENGTH_SHORT).show();
                }else{
                    if(strPassConf.equals(strPass)){
                        boolean ada = db.isUserIdExists(strUsername);
                        if(!ada){
                            boolean registered = db.insertAnggota(strNama, strUsername, strPass, role);
                            if(registered == true){
                                Toast.makeText(getApplicationContext(), "Registrasi Berhasil !!", Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent(RecruitPage.this, LoginPage.class);
                                startActivity(loginIntent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Registrasi Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Username anda sudah terpakai/terdaftar.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, GuestPage.class);
        startActivity(back);
        finish();
    }
}