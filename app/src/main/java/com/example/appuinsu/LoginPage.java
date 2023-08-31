package com.example.appuinsu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        db = new DatabaseHelper(this);
        Button login = findViewById(R.id.btLogin);
        Button guest = findViewById(R.id.btGuest);
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                Boolean loggedIn = db.checkLogin(strUsername, strPass);
                if(loggedIn == true){
                    Boolean updateSession = db.upgradeSession("ada", 1);
                    if(updateSession == true){
                        String role = db.checkRole(strUsername);
                        String username = db.saveLoggedInUserId(strUsername);
                        if("admin".equals(role)){
                            Intent menuAdmin = new Intent(LoginPage.this, AdminMenu.class);
                            startActivity(menuAdmin);
                            finish();
                        }else if ("anggota".equals(role)){
                            Intent menuUser = new Intent(LoginPage.this, UserMenu.class);
                            startActivity(menuUser);
                            finish();
                        }else if("calon".equals(role)){
                            showAlertUnapproved();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Gagal Login", Toast.LENGTH_SHORT).show();
                }

            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuGuest = new Intent(LoginPage.this, GuestPage.class);
                startActivity(menuGuest);
                finish();
            }
        });

    }

    private void showAlertUnapproved(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info Login");
        builder.setMessage("Akun anda belum disetujui oleh Admin.\nMohon tunggu Admin menyetujui Akun anda.");
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}