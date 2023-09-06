package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfilUser extends AppCompatActivity {
    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_user);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);
        String loggedInUname = db.getLoggedInUserId();
        DatabaseHelper.UserData userData = db.getUserData(loggedInUname);

        EditText nama = findViewById(R.id.etNama);
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        Button ubah = findViewById(R.id.btnEdit);
        Button batal = findViewById(R.id.btnBatal);

        if(userData != null){
            nama.setText(userData.getValueNama());
            username.setText(userData.getValueUsername());
            password.setText(userData.getValuePassword());
        }

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                String role = userData.getValueRole();

                if(strNama.trim().isEmpty() || strUsername.trim().isEmpty() || strPass.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Harap isi seluruh data yang diminta!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean ada = db.isUserIdExists(strUsername);
                    if(!ada){
                        boolean registered = db.updateUserData(loggedInUname, strNama,strUsername,strPass,role);
                        if(registered == true){
                            Toast.makeText(getApplicationContext(), "Profil Anda berhasil Diubah,\nSilahkan Login kembali.", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(EditProfilUser.this, LoginPage.class);
                            startActivity(loginIntent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ubah Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Username anda sudah terpakai/terdaftar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), UserMenu.class);
                startActivity(back);
                finish();
            }
        });

    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, UserMenu.class);
        startActivity(back);
        finish();
    }
}