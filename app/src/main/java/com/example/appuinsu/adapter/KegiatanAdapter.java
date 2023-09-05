package com.example.appuinsu.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appuinsu.R;

public class KegiatanAdapter extends CursorAdapter {
    public KegiatanAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_kegiatan, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = view.findViewById(R.id.foto);
        TextView namaTextView = view.findViewById(R.id.judul);

        // Ambil data dari cursor
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
        String foto = cursor.getString(cursor.getColumnIndexOrThrow("foto"));
        view.setTag(id);
        // Setel data ke tampilan
        imageView.setImageURI(Uri.parse(foto));
        namaTextView.setText(nama);
    }
}
