package com.minimarket.scanminimarket.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minimarket.scanminimarket.MainActivity;
import com.minimarket.scanminimarket.SharedPreferences.PrefManager;
import com.minimarket.scanminimarket.UtilsApi.ApiInterface;
import com.minimarket.scanminimarket.UtilsApi.UtilsApi;
import com.minimarket.scanminimarket.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    PrefManager manager;
    ApiInterface apiInterface;
    private Context context;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        manager = new PrefManager(context);
        apiInterface = UtilsApi.getApiService();

        dialog = new SpotsDialog.Builder().setContext(context).setMessage("Please Wait").setCancelable(false).build();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.inputUsername.getText().toString())){
                    binding.inputUsername.setError("Masukkan Username");
                }else if(TextUtils.isEmpty(binding.inputPassword.getText().toString())){
                    binding.inputPassword.setError("Masukkan Password");
                }else {
                    dialog.show();
                    apiInterface.login(
                            binding.inputUsername.getText().toString(),
                            binding.inputPassword.getText().toString()
                    ).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                dialog.dismiss();
                                try {
                                    JSONObject object = new JSONObject(response.body().string());
                                    if (object.getString("status").equalsIgnoreCase("200")){
                                        JSONObject data = object.getJSONObject("data");

                                        Gson gson = new Gson();
                                        User.DataBean user = gson.fromJson(data + "", User.DataBean.class);

                                        manager.saveSession();
                                        manager.setNamaUser(PrefManager.NAMA_USER,user.getNama_user());
                                        manager.setUsername(PrefManager.USERNAME, user.getUsername());
                                        manager.setUsername(PrefManager.LEVEL, user.getLevel());

                                        Intent intent = new Intent(context, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        finish();
                                        startActivity(intent);
                                    }else {
                                        dialog.dismiss();
                                        Toast.makeText(context, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {
                                    JSONObject object = new JSONObject(response.errorBody().string());
                                    dialog.dismiss();
                                    Toast.makeText(context, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        PrefManager manager = new PrefManager(context);
        boolean id = manager.getSession();
        if (id){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
