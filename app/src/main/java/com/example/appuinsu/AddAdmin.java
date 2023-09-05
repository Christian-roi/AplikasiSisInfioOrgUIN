package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAdmin extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        db = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditText nama = findViewById(R.id.etNama);
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        Button daftar = findViewById(R.id.btnDaftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                String role = "admin";

                if(strNama.trim().isEmpty() || strUsername.trim().isEmpty() || strPass.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Harap isi seluruh data yang diminta!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean ada = db.isUserIdExists(strUsername);
                    if(!ada){
                        boolean registered = db.insertAnggota(strNama, strUsername, strPass, role);
                        if(registered == true){
                            Toast.makeText(getApplicationContext(), "Berhasil Tambah Admin", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(getApplicationContext(), AnggotaPage.class);
                            startActivity(loginIntent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Tambah Anggota Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Username admin sudah terpakai/terdaftar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        password.setText("12345");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, AnggotaPage.class);
        startActivity(back);
        finish();
    }
}