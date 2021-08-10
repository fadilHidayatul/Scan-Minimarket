package com.minimarket.scanminimarket.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minimarket.scanminimarket.R;
import com.minimarket.scanminimarket.UtilsApi.ApiInterface;
import com.minimarket.scanminimarket.UtilsApi.UtilsApi;
import com.minimarket.scanminimarket.databinding.ActivityResultBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;
    private String idbarang;
    private String jenisBarang, namaBarang, tglMasuk, bulan, harga, stok, img, detailBarang;

    ApiInterface apiInterface;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        apiInterface = UtilsApi.getApiService();
        Intent intent = getIntent();
        idbarang = intent.getStringExtra("idbarang");

//        Toast.makeText(this, ""+idbarang, Toast.LENGTH_SHORT).show();
        getDataBarang();

        goDetail();
        binding.back.setOnClickListener(view -> finish());

    }

    private void getDataBarang() {
        apiInterface.getBarang(idbarang).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            JSONArray array = object.getJSONArray("DATA");
                            JSONObject data = array.getJSONObject(0);

                            binding.namaBarang.setText(data.getString("nama_barang"));
                            binding.jenisBarang.setText(data.getString("jenis_barang"));
                            Glide.with(context).load(UtilsApi.imgURL+data.getString("img")).into(binding.imgBarang);

                            jenisBarang = data.getString("jenis_barang");
                            namaBarang = data.getString("nama_barang");
                            tglMasuk = data.getString("tgl_masuk");
                            bulan = data.getString("bulan");
                            harga = data.getString("harga");
                            stok = data.getString("stok");
                            img = data.getString("img");
                            detailBarang = data.getString("detail_barang");

                        }else {
                            Toast.makeText(ResultActivity.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Toast.makeText(ResultActivity.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ResultActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goDetail() {
        binding.btnSeeDetailResult.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, DetailResultActivity.class);
            intent1.putExtra("jenis", jenisBarang);
            intent1.putExtra("nama", namaBarang);
            intent1.putExtra("tgl", tglMasuk);
            intent1.putExtra("bulan", bulan);
            intent1.putExtra("harga", harga);
            intent1.putExtra("stok", stok);
            intent1.putExtra("img", img);
            intent1.putExtra("detail", detailBarang);
            startActivity(intent1);

        });
    }
}
