package com.example.appuinsu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AdminDetailKegiatan extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView imageView;

    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_kegiatan);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        EditText etNama = findViewById(R.id.etNama);
        EditText etWaktu = findViewById(R.id.etWaktu);
        EditText etTempat = findViewById(R.id.etTempat);
        EditText etDesk = findViewById(R.id.etDesk);
        Button upload = findViewById(R.id.btUpload);
        Button edit = findViewById(R.id.btnEdit);
        Button delete = findViewById(R.id.btnDelete);
        imageView = findViewById(R.id.preview);
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
                etNama.setText(nama);
                etWaktu.setText(waktu);
                etTempat.setText(tempat);
                etDesk.setText(deskripsi);
                imageView.setImageURI(Uri.parse(foto));

            }while(cursor.moveToNext());
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = imageView.getDrawable();
                String judul = etNama.getText().toString();
                String waktu = etWaktu.getText().toString();
                String tempat = etTempat.getText().toString();
                String deskripsi = etDesk.getText().toString();
                if(imagePath.isEmpty()){
                    imagePath = db.getFotoUri(idx);
                    boolean result = db.updateKegiatan(String.valueOf(idx), judul, waktu, tempat, deskripsi, imagePath);
                    if(result == true){
                        Toast.makeText(getApplicationContext(), "Kegiatan Berhasil diubah", Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(getApplicationContext(), KegiatanPage.class);
                        startActivity(success);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Upload Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    boolean result = db.updateKegiatan(String.valueOf(idx), judul, waktu, tempat, deskripsi, imagePath);
                    if(result == true){
                        Toast.makeText(getApplicationContext(), "Kegiatan Berhasil diubah", Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(getApplicationContext(), KegiatanPage.class);
                        startActivity(success);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Upload Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
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
                db.deleteDataById("tb_kegiatan", idx);
                Intent successIntent = new Intent(getApplicationContext(), KegiatanPage.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // Mengubah Uri menjadi path gambar
            imagePath = getPathFromUri(selectedImageUri);
            // Menampilkan gambar yang di-upload di ImageView
            imageView.setImageURI(selectedImageUri);
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return uri.getPath(); // fallback jika cursor null
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        Intent back = new Intent(this, KegiatanPage.class);
        startActivity(back);
        finish();
    }
}