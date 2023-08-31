package com.example.appuinsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.appuinsu.adapter.AnggotaAdapter;
import com.example.appuinsu.model.ModelAnggota;
import com.example.appuinsu.model.ModelFinance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnggotaPage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    ListView listView;
    List<ModelAnggota> lists = new ArrayList<>();
    AnggotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_page);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_items);

        adapter = new AnggotaAdapter(AnggotaPage.this, lists);
        listView.setAdapter(adapter);
        getData();
    }

    private void getData(){
        ArrayList<HashMap<String, String >> rows = db.getAnggota();
        for (int i = 0; i < rows.size(); i++){
            String id = rows.get(i).get("id");
            String nama = rows.get(i).get("nama");
            ModelAnggota data = new ModelAnggota();
            data.setId(id);
            data.setNama(nama);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();
        lists.clear();
        getData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anggota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tambah) {
            Intent tambah = new Intent(AnggotaPage.this, AnggotaAdminForm.class);
            super.startActivity(tambah);
            return true;
        } else if (id == R.id.baru) {
            Intent list = new Intent(AnggotaPage.this, AnggotaApprovePage.class);
            super.startActivity(list);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, AdminMenu.class);
        startActivity(back);
        finish();
    }
}