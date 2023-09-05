package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminDetailKeuangan extends AppCompatActivity {
    ActionBar actionBar;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_keuangan);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        TextView nama = findViewById(R.id.nama);
        TextView deskripsi = findViewById(R.id.desc);
        TextView jenis = findViewById(R.id.jenis);
        TextView nominal = findViewById(R.id.nominal);
        TextView judul = findViewById(R.id.judul);
        Button edit = findViewById(R.id.btnEdit);
        Button hapus = findViewById(R.id.btnDelete);

        int idx = getIntent().getIntExtra("id",0);
        String query = "SELECT * FROM tb_keuangan WHERE id = " +idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //Value
                String valNama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String valDeskripsi= cursor.getString(cursor.getColumnIndexOrThrow("keterangan"));
                String valNominal = cursor.getString(cursor.getColumnIndexOrThrow("nominal"));
                String valJenis = cursor.getString(cursor.getColumnIndexOrThrow("jenis"));
                //Set to TextView
                judul.setText("Judul "+valJenis);
                nama.setText(valNama);
                deskripsi.setText(valDeskripsi);
                jenis.setText(valJenis);
                nominal.setText("Rp. "+valNominal+",-");
            }while(cursor.moveToNext());
        }
        deskripsi.setMovementMethod(new ScrollingMovementMethod());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminEditKeuangan.class);
                intent.putExtra("id", idx);
                startActivity(intent);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDelete();
            }
        });

    }

    private void showAlertDelete(){
        int idx = getIntent().getIntExtra("id",0);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda yakin ingin menghapus data ini?");
        alertDialogBuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteDataById("tb_keuangan", idx);
                Intent successIntent = new Intent(getApplicationContext(), FinancePage.class);
                startActivity(successIntent);
                finish();
            }
        });
        // Tombol "Batal" dalam AlertDialog
        alertDialogBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, FinancePage.class);
        startActivity(back);
        finish();
    }
}