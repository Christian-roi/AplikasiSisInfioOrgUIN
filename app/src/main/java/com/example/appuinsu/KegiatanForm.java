package com.example.appuinsu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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

public class KegiatanForm extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView imageView;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan_form);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        EditText etNama = findViewById(R.id.etNama);
        EditText etWaktu = findViewById(R.id.etWaktu);
        EditText etTempat = findViewById(R.id.etTempat);
        EditText etDesk = findViewById(R.id.etDesk);
        Button upload = findViewById(R.id.btUpload);
        Button submit = findViewById(R.id.btSubmit);
        imageView = findViewById(R.id.preview);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = imageView.getDrawable();
                String judul = etNama.getText().toString();
                String waktu = etWaktu.getText().toString();
                String tempat = etTempat.getText().toString();
                String deskripsi = etDesk.getText().toString();
                boolean cek = judul.isEmpty() || waktu.isEmpty() || tempat.isEmpty() || deskripsi.isEmpty();
                if(!imagePath.isEmpty() && cek == false){
                    boolean result = db.insertKegiatan(judul,waktu,tempat,deskripsi,imagePath);
                    if(result == true){
                        Intent success = new Intent(getApplicationContext(), KegiatanPage.class);
                        startActivity(success);
                        Toast.makeText(KegiatanForm.this, "Kegiatan Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(KegiatanForm.this, "Data Gagal Ditambah", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Harap upload gambar terlebih dahulu.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        Intent back = new Intent(this, AdminMenu.class);
        startActivity(back);
        finish();
    }
}