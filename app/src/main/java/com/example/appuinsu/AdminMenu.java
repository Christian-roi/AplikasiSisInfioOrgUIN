package com.example.appuinsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMenu extends AppCompatActivity {

    private CardView keuangan, kegiatan, anggota, absensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        keuangan = findViewById(R.id.btnUang);
        kegiatan = findViewById(R.id.btnKegiatan);
        anggota = findViewById(R.id.btnAnggota);
        absensi = findViewById(R.id.btnAbsensi);
        Button logout = findViewById(R.id.btnKeluar);

        keuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finance = new Intent(AdminMenu.this, FinancePage.class);
                startActivity(finance);
                finish();
            }
        });

        kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kegiatan = new Intent(AdminMenu.this, KegiatanPage.class);
                startActivity(kegiatan);
                finish();
            }
        });

        anggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anggota = new Intent(AdminMenu.this, AnggotaPage.class);
                startActivity(anggota);
                finish();
            }
        });

        absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent absensi = new Intent(AdminMenu.this, AdminAbsensiPage.class);
                startActivity(absensi);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keluar = new Intent(AdminMenu.this, LoginPage.class);
                startActivity(keluar);
                finish();
            }
        });
    }
}