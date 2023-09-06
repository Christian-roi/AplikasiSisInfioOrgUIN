package com.example.appuinsu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appuinsu.R;
import com.example.appuinsu.model.ModelAbsen;
import com.example.appuinsu.model.ModelAnggota;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AbsenAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelAbsen> lists;

    public AbsenAdapter(Activity activity, List<ModelAbsen> lists){
        this.activity = activity;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null && inflater != null){
            view = inflater.inflate(R.layout.list_absen, null);
        }
        if(view != null){
            TextView nama = view.findViewById(R.id.nama);
            TextView waktu = view.findViewById(R.id.waktu);
            TextView status = view.findViewById(R.id.status);
            ModelAbsen modelAbsen = lists.get(i);
            nama.setText(modelAbsen.getNama());
            status.setText(modelAbsen.getStatus());
            String dateTime = modelAbsen.getWaktu();
            String formatWaktu = convertDateTime(dateTime);
            waktu.setText(formatWaktu);

        }
        return view;
    }

    public static String convertDateTime(String inputDateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            //SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm, EEEE dd MMMM yyyy", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, d MMMM yyyy, HH:mm", new Locale("id", "ID"));

            Date date = inputFormat.parse(inputDateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDateTime; // Jika terjadi kesalahan, kembalikan format asli
        }
    }
}
