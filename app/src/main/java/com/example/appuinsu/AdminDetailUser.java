package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminDetailUser extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_user);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        EditText nama = findViewById(R.id.etNama);
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        Button edit = findViewById(R.id.btnEdit);
        Button hapus = findViewById(R.id.btnDelete);

        int idx = getIntent().getIntExtra("id",0);
        String query = "SELECT * FROM tb_anggota WHERE id = " +idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                //Value
                String valNama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String valUsername= cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String valPass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String valRole = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                //Set to TextView
                nama.setText(valNama);
                username.setText(valUsername);
                password.setText(valPass);

            }while(cursor.moveToNext());
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                String role = db.getRoleById(idx);
                String currUsername = db.getUsername(idx);

                if(strNama.trim().isEmpty() || strUsername.trim().isEmpty() || strPass.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Harap isi seluruh data yang diminta!", Toast.LENGTH_SHORT).show();
                }else{
                    if(strUsername.equals(currUsername)){
                        boolean registered = db.updateUser(String.valueOf(idx), strNama, strUsername, strPass, role);
                        if (registered == true) {
                            Toast.makeText(getApplicationContext(), "User Berhasil Diubah.", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(getApplicationContext(), AnggotaPage.class);
                            startActivity(loginIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ubah Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        boolean ada = db.isUserIdExists(strUsername);
                        if (!ada) {
                            boolean registered = db.updateUser(String.valueOf(idx), strNama, strUsername, strPass, role);
                            if (registered == true) {
                                Toast.makeText(getApplicationContext(), "User Berhasil Diubah.", Toast.LENGTH_SHORT).show();
                                Intent successIntent = new Intent(getApplicationContext(), AnggotaPage.class);
                                startActivity(successIntent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Ubah Data Gagal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Username sudah terpakai/terdaftar.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDelete();
            }
        });
    }

    private void showAlertDelete(){
        int idx = getIntent().getIntExtra("id",0);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda yakin ingin menghapus data ini?");
        alertDialogBuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteDataById("tb_anggota", idx);
                Intent successIntent = new Intent(getApplicationContext(), AnggotaPage.class);
                startActivity(successIntent);
                finish();
            }
        });
        // Tombol "Batal" dalam AlertDialog
        alertDialogBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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