package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class AbsenUserForm extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_user_form);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditText nama = findViewById(R.id.etNama);
        RadioButton hadir = findViewById(R.id.hadir);
        RadioButton sakit = findViewById(R.id.sakit);
        RadioButton izin = findViewById(R.id.izin);
        Button absen = findViewById(R.id.btAbsen);

        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Isi Proses Absen
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