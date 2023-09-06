package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.appuinsu.adapter.AbsenAdapter;
import com.example.appuinsu.model.ModelAbsen;
import com.example.appuinsu.model.ModelAnggota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminAbsensiPage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    ListView listView;
    List<ModelAbsen> lists = new ArrayList<>();
    AbsenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_absensi_page);
        setTitle("Absensi Anggota");
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_items);

        adapter = new AbsenAdapter(AdminAbsensiPage.this, lists);
        listView.setAdapter(adapter);
        getData();
    }

    private void getData(){
        ArrayList<HashMap<String, String >> rows = db.getAbsen();
        for (int i = 0; i < rows.size(); i++){
            String id = rows.get(i).get("id");
            String nama = rows.get(i).get("nama");
            String waktu = rows.get(i).get("waktu");
            String status = rows.get(i).get("status");
            ModelAbsen data = new ModelAbsen();
            data.setId(id);
            data.setNama(nama);
            data.setWaktu(waktu);
            data.setStatus(status);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();
        lists.clear();
        getData();
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