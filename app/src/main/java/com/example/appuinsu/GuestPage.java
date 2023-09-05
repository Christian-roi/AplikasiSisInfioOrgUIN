package com.example.appuinsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appuinsu.adapter.KegiatanAdapter;

public class GuestPage extends AppCompatActivity {
    DatabaseHelper db;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        ListView listView = findViewById(R.id.list_item);
        Cursor cursor = db.getAllKegiatan();
        KegiatanAdapter adapter = new KegiatanAdapter(this, cursor);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int idx = (int) view.getTag();
                Intent intent = new Intent(getApplicationContext(), DetailGuest.class);
                intent.putExtra("id", idx);
                startActivity(intent);
            }
        });
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

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent keluar = new Intent(GuestPage.this, LoginPage.class);
        super.startActivity(keluar);
        finish();
    }
}