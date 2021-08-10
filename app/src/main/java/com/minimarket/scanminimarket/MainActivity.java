package com.minimarket.scanminimarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.minimarket.scanminimarket.Login.LoginActivity;
import com.minimarket.scanminimarket.Scan.ScanActivity;
import com.minimarket.scanminimarket.SharedPreferences.PrefManager;
import com.minimarket.scanminimarket.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context context;
    PrefManager manager;

    private boolean doubleback;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        manager = new PrefManager(context);

        binding.goScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScanActivity.class);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Keluar Aplikasi?")
                    .setPositiveButton("Ya", (dialogInterface, i) -> {
                        manager.removeSession();
                        finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    })
                    .setNegativeButton("tidak", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleback){
            toast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
        }else {
            toast = Toast.makeText(this, "Tekan sekali lagi untuk keluar",Toast.LENGTH_LONG);
            toast.show();
            doubleback = true;

            Handler handler = new Handler();
            handler.postDelayed((Runnable) () -> {
                doubleback = false;
            }, 2000);
        }

    }
}
