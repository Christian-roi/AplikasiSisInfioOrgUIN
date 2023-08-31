package com.example.appuinsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GuestPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.menu_guest, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.rekrut) {
            Intent rekrut = new Intent(GuestPage.this, RecruitPage.class);
            super.startActivity(rekrut);
            return true;
        } else if (id == R.id.logout) {
            Intent keluar = new Intent(GuestPage.this, LoginPage.class);
            super.startActivity(keluar);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void onBackPressed() {
        Intent keluar = new Intent(GuestPage.this, LoginPage.class);
        super.startActivity(keluar);
        finish();
    }
}