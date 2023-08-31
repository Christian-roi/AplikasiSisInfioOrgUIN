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
import com.example.appuinsu.model.ModelAnggota;
import com.example.appuinsu.model.ModelFinance;

import java.util.List;

public class AnggotaAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelAnggota> lists;

    public AnggotaAdapter(Activity activity, List<ModelAnggota> lists){
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
            view = inflater.inflate(R.layout.list_anggota, null);
        }
        if(view != null){
            TextView nama = view.findViewById(R.id.nama);
            ModelAnggota modelAnggota = lists.get(i);
            nama.setText(modelAnggota.getNama());
        }
        return view;
    }
}
