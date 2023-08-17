package com.example.appuinsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.rekrut) {
            Intent rekrut = new Intent(UserMenu.this, RecruitPage.class);
            super.startActivity(rekrut);
            return true;
        } else if (id == R.id.absensi) {
            Intent absen = new Intent(UserMenu.this, AbsenUserForm.class);
            super.startActivity(absen);
            return true;
        } else if (id == R.id.logout) {
            Intent keluar = new Intent(UserMenu.this, LoginPage.class);
            super.startActivity(keluar);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent keluar = new Intent(UserMenu.this, LoginPage.class);
        super.startActivity(keluar);
        finish();
    }
}