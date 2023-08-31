package com.example.appuinsu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.example.appuinsu.R;
import com.example.appuinsu.model.ModelFinance;

import java.util.List;

public class FinanceAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelFinance> lists;

    public FinanceAdapter(Activity activity, List<ModelFinance> lists){
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
            view = inflater.inflate(R.layout.list_keuangan, null);
        }
        if(view != null){
            TextView jenis = view.findViewById(R.id.jenis);
            TextView nominal = view.findViewById(R.id.nominal);
            ModelFinance modelFinance = lists.get(i);
            jenis.setText(modelFinance.getNama());
            nominal.setText("Rp. "+modelFinance.getNominal());
            if(modelFinance.getJenis() != null && modelFinance.getJenis().equals("Pemasukan")){
               Drawable drawable = view.getResources().getDrawable(R.drawable.ic_pemasukan);
               jenis.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            } else if (modelFinance.getJenis() != null && modelFinance.getJenis().equals("Pengeluaran")){
                Drawable drawable = view.getResources().getDrawable(R.drawable.ic_pengeluaran);
                jenis.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
        }
        return view;
    }
}
