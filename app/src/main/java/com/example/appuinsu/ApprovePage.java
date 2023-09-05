package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ApprovePage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_page);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        EditText nama = findViewById(R.id.etNama);
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        Button approve = findViewById(R.id.btnTerima);
        nama.setEnabled(false);
        username.setEnabled(false);
        password.setEnabled(false);

        int idx = getIntent().getIntExtra("id",0);
        String query = "SELECT * FROM tb_anggota WHERE id = " +idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                //Value
                String valNama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String valUsername= cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String valPass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                //Set to TextView
                nama.setText(valNama);
                username.setText(valUsername);
                password.setText(valPass);

            }while(cursor.moveToNext());
        }

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveUser();
            }
        });
    }

    private void approveUser(){
        DatabaseHelper db = new DatabaseHelper(this);
        int idx = getIntent().getIntExtra("id", 0);
        String idValue = String.valueOf(idx);
        String roleValue = "anggota";
        ContentValues values = new ContentValues();
        values.put("role", roleValue);
        db.getWritableDatabase().update("tb_anggota", values, "id = ?", new String[]{idValue});
        db.close();
        Toast.makeText(getApplicationContext(), "User Telah Diberi akses Anggota", Toast.LENGTH_SHORT).show();
        Intent result = new Intent(getApplicationContext(), AnggotaApprovePage.class);
        startActivity(result);
        finish();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, AnggotaApprovePage.class);
        startActivity(back);
        finish();
    }
}