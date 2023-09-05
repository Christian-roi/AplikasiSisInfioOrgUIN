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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appuinsu.adapter.KegiatanAdapter;

public class KegiatanPage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan_page);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        ListView listView = findViewById(R.id.list_item);
        Cursor cursor = db.getAllKegiatan();
        KegiatanAdapter adapter = new KegiatanAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int idx = (int) view.getTag();
                Intent intent = new Intent(getApplicationContext(), AdminDetailKegiatan.class);
                intent.putExtra("id", idx);
                startActivity(intent);
                //Toast.makeText(KegiatanPage.this, "Ini Post dengan ID: "+idx, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_kegiatan, menu);
        // Menampilkan ikon pada semua opsi menu
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.tambah) {
            Intent tambah = new Intent(KegiatanPage.this, KegiatanForm.class);
            super.startActivity(tambah);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent back = new Intent(this, AdminMenu.class);
        startActivity(back);
        finish();
    }
}