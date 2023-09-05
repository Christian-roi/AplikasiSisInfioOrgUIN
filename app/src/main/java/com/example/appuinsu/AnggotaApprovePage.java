package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appuinsu.adapter.AnggotaAdapter;
import com.example.appuinsu.model.ModelAnggota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnggotaApprovePage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    ListView listView;
    List<ModelAnggota> lists = new ArrayList<>();
    AnggotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_approve_page);
        setTitle("Pendaftar Baru");
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_items);

        adapter = new AnggotaAdapter(AnggotaApprovePage.this, lists);
        listView.setAdapter(adapter);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String idx = lists.get(position).getId();
                Intent intent = new Intent(AnggotaApprovePage.this, ApprovePage.class);
                intent.putExtra("id", Integer.parseInt(idx));
                startActivity(intent);
            }
        });
    }

    private void getData(){
        ArrayList<HashMap<String, String >> rows = db.getAnggotaApprove();
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

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, AnggotaPage.class);
        startActivity(back);
        finish();
    }
}