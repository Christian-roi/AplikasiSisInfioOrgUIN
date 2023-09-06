package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AbsenUserForm extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_user_form);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);
        String loggedInUname = db.getLoggedInUserId();
        DatabaseHelper.UserData userData = db.getUserData(loggedInUname);

        EditText nama = findViewById(R.id.etNama);
        RadioButton hadir = findViewById(R.id.hadir);
        RadioButton sakit = findViewById(R.id.sakit);
        RadioButton izin = findViewById(R.id.izin);
        Button absen = findViewById(R.id.btAbsen);

        if(userData != null){
            nama.setText(userData.getValueNama());
            nama.setEnabled(false);
        }

        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valNama = nama.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                boolean sudahAbsen = db.hasAttendanceForDate(currentDate, valNama);
                if (sudahAbsen) {
                    // Tampilkan notifikasi kepada pengguna bahwa dia sudah mengisi absen hari ini
                    String waktuAbsenTerakhir = db.getWaktuAbsensiHariIniByUsername(valNama);
                    tampilkanAlertWaktuAbsenTerakhir(waktuAbsenTerakhir);
                    //Toast.makeText(AbsenUserForm.this, "Anda Sudah Absen Hari Ini", Toast.LENGTH_SHORT).show();
                } else {
                    if(hadir.isChecked() && !valNama.isEmpty()){
                        String valStatus = "Hadir";
                        boolean result = db.insertAbsen(valNama,valStatus);
                        if(result == true){
                            Toast.makeText(AbsenUserForm.this, "Absen Dikirim", Toast.LENGTH_SHORT).show();
                            Intent success = new Intent(getApplicationContext(), UserMenu.class);
                            startActivity(success);
                            finish();
                        }else {
                            Toast.makeText(AbsenUserForm.this, "Terjadi Kesalahan Absen", Toast.LENGTH_SHORT).show();
                        }
                    } else if (sakit.isChecked() && !valNama.isEmpty()) {
                        String valStatus = "Sakit";
                        boolean result = db.insertAbsen(valNama,valStatus);
                        if(result == true){
                            Toast.makeText(AbsenUserForm.this, "Absen Dikirim", Toast.LENGTH_SHORT).show();
                            Intent success = new Intent(getApplicationContext(), UserMenu.class);
                            startActivity(success);
                            finish();
                        }else {
                            Toast.makeText(AbsenUserForm.this, "Terjadi Kesalahan Absen", Toast.LENGTH_SHORT).show();
                        }
                    } else if (izin.isChecked() && !valNama.isEmpty()) {
                        String valStatus = "Izin";
                        boolean result = db.insertAbsen(valNama,valStatus);
                        if(result == true){
                            Toast.makeText(AbsenUserForm.this, "Absen Dikirim", Toast.LENGTH_SHORT).show();
                            Intent success = new Intent(getApplicationContext(), UserMenu.class);
                            startActivity(success);
                            finish();
                        }else {
                            Toast.makeText(AbsenUserForm.this, "Terjadi Kesalahan Absen", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AbsenUserForm.this, "Harap Isi data absen", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void tampilkanAlertWaktuAbsenTerakhir(String waktuAbsen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Waktu Absen Terakhir");
        builder.setMessage("Anda sudah melakukan absen pada pukul " + waktuAbsen);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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