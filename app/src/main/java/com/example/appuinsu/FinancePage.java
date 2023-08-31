package com.example.appuinsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appuinsu.adapter.FinanceAdapter;
import com.example.appuinsu.model.ModelFinance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FinancePage extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;
    ListView listView;
    List<ModelFinance> lists = new ArrayList<>();
    FinanceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_page);
        setTitle("Kelola Keuangan");
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_item);

        TextView saldo = findViewById(R.id.saldo);
        TextView pemasukan = findViewById(R.id.pemasukan);
        TextView pengeluaran = findViewById(R.id.pengeluaran);
        double valPemasukan = db.getTotalPemasukanByJenis("Pemasukan");
        double valPengeluaran = db.getTotalPemasukanByJenis("Pengeluaran");
        double totalSaldo = valPemasukan - valPengeluaran;
        pemasukan.setText(formatUang(valPemasukan));
        pengeluaran.setText(formatUang(valPengeluaran));
        saldo.setText(formatUang(totalSaldo));

        adapter = new FinanceAdapter(FinancePage.this, lists);
        listView.setAdapter(adapter);
        getData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_finance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.tambahPemasukan) {
            Intent tambah = new Intent(FinancePage.this, PemasukanForm.class);
            super.startActivity(tambah);
            return true;
        } else if (id == R.id.tambahPengeluaran) {
            Intent tambah = new Intent(FinancePage.this, PengeluaranForm.class);
            super.startActivity(tambah);
            return true;
        }else if (id == R.id.cetak) {
            // Lakukan sesuatu saat Option 3 dipilih
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String formatUang(double nominal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        return "Rp. " + decimalFormat.format(nominal) + ".-";
    }

    private void getData(){
        ArrayList<HashMap<String, String >> rows = db.getFinance();
        for (int i = 0; i < rows.size(); i++){
            String id = rows.get(i).get("id");
            String nama = rows.get(i).get("nama");
            String jenis = rows.get(i).get("jenis");
            String nominal = rows.get(i).get("nominal");
            ModelFinance data = new ModelFinance();
            data.setId(id);
            data.setNama(nama);
            data.setJenis(jenis);
            data.setNominal(nominal);
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
        Intent back = new Intent(this, AdminMenu.class);
        startActivity(back);
        finish();
    }
}