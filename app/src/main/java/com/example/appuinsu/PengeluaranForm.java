package com.example.appuinsu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PengeluaranForm extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_form);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(this);
        EditText nama = findViewById(R.id.etNama);
        EditText keterangan = findViewById(R.id.etKeterangan);
        EditText nominal = findViewById(R.id.etNominal);
        Button tambah = findViewById(R.id.btTambah);
        setupEditTextWithThousandSeparator(nominal);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valNama = nama.getText().toString();
                String valKet = keterangan.getText().toString();
                String valNominal = nominal.getText().toString();
                String jenis = "Pengeluaran";

                if(valNama.trim().isEmpty() || valKet.trim().isEmpty() || valNominal.trim().isEmpty()){
                    Toast.makeText(PengeluaranForm.this, "Harap Isi Seluruh Form!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean pemasukan = db.insertKeuangan(valNama, valKet, valNominal, jenis);
                    if(pemasukan == true){
                        Toast.makeText(PengeluaranForm.this, "Data Pengeluaran Berhasil ditambah.", Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(PengeluaranForm.this, FinancePage.class);
                        startActivity(success);
                        finish();
                    }else {
                        Toast.makeText(PengeluaranForm.this, "Data Pengeluaran Gagal ditambah.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void setupEditTextWithThousandSeparator(final EditText editText) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        decimalFormat.applyPattern("#,###");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                editText.removeTextChangedListener(this);
                String originalText = editable.toString();
                if (!originalText.isEmpty()) {
                    // Remove all non-digit characters
                    String cleanText = originalText.replaceAll("[^0-9]", "");
                    if (!cleanText.isEmpty()) {
                        // Parse the cleaned text to a long value
                        long value = Long.parseLong(cleanText);
                        // Format the value with thousand separators
                        String formattedText = decimalFormat.format(value);
                        editText.setText(formattedText);
                        editText.setSelection(formattedText.length());
                    }
                }
                editText.addTextChangedListener(this);
            }
        });
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