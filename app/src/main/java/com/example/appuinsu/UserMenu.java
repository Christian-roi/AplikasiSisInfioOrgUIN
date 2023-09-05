package com.example.appuinsu;

import androidx.annotation.NonNull;
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

public class UserMenu extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        db = new DatabaseHelper(this);
        String savedUsername = db.getLoggedInUserId();
        ListView listView = findViewById(R.id.list_item);
        Cursor cursor = db.getAllKegiatan();
        KegiatanAdapter adapter = new KegiatanAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int idx = (int) view.getTag();
                Intent intent = new Intent(getApplicationContext(), DetailUser.class);
                intent.putExtra("id", idx);
                startActivity(intent);
            }
        });
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
        } else if (id == R.id.profil) {
            Intent profil = new Intent(UserMenu.this, EditProfilUser.class);
            super.startActivity(profil);
            return true;
        } else if (id == R.id.logout) {
            Intent keluar = new Intent(UserMenu.this, LoginPage.class);
            super.startActivity(keluar);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent keluar = new Intent(UserMenu.this, LoginPage.class);
        super.startActivity(keluar);
        finish();
    }
}