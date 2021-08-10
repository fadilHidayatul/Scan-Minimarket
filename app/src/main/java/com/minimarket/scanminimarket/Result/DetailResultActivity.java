package com.minimarket.scanminimarket.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.minimarket.scanminimarket.MainActivity;
import com.minimarket.scanminimarket.UtilsApi.UtilsApi;
import com.minimarket.scanminimarket.databinding.ActivityDetailResultBinding;
import com.minimarket.scanminimarket.databinding.ActivityResultBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailResultActivity extends AppCompatActivity {
    private ActivityDetailResultBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        getScannedResult();

        binding.backDetail.setOnClickListener(view -> finish());
        binding.otherScan.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void getScannedResult() {
        Intent intent = getIntent();
        String tgl = intent.getStringExtra("tgl").substring(8,10);
        String bln = intent.getStringExtra("bulan");
        String thn = intent.getStringExtra("tgl").substring(0,4);

        Glide.with(context).load(UtilsApi.imgURL + intent.getStringExtra("img")).into(binding.detailImgBarang);
        binding.txtNamaBarang.setText(intent.getStringExtra("nama"));
        binding.txtJenisBarang.setText(intent.getStringExtra("jenis"));
        binding.txtDeskripsiBarang.setText(intent.getStringExtra("detail"));
        binding.txtHarga.setText(formatRupiah(Double.parseDouble(intent.getStringExtra("harga"))));
        binding.txtStok.setText(intent.getStringExtra("stok")+" buah");
        binding.txtTglMasuk.setText("Masuk tgl "+ tgl + " " + bln + " " + thn);

    }

    private String formatRupiah(Double number){
        Locale localeId = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        return formatRupiah.format(number);
    }
}
