package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailGuest extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_guest);
        actionBar = getSupportActionBar();
        db = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView judul = findViewById(R.id.judul);
        TextView tp = findViewById(R.id.timeplace);
        TextView desc = findViewById(R.id.deskripsi);
        ImageView imageView = findViewById(R.id.foto);

        int idx = getIntent().getIntExtra("id",0);
        String query = "SELECT * FROM tb_kegiatan WHERE id = " +idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //Value
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String waktu = cursor.getString(cursor.getColumnIndexOrThrow("waktu"));
                String tempat = cursor.getString(cursor.getColumnIndexOrThrow("tempat"));
                String deskripsi = cursor.getString(cursor.getColumnIndexOrThrow("deskripsi"));
                String foto = cursor.getString(cursor.getColumnIndexOrThrow("foto"));
                //Set to TextView
                judul.setText(nama);
                tp.setText(tempat+", "+waktu);
                desc.setText(deskripsi);
                imageView.setImageURI(Uri.parse(foto));

            }while(cursor.moveToNext());
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, GuestPage.class);
        startActivity(back);
        finish();
    }
}