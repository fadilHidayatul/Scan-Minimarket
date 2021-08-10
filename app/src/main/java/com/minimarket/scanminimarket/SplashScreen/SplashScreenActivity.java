package com.minimarket.scanminimarket.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.minimarket.scanminimarket.Login.LoginActivity;
import com.minimarket.scanminimarket.MainActivity;
import com.minimarket.scanminimarket.R;
import com.minimarket.scanminimarket.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transition);
        binding.splash.startAnimation(anim);

        Intent intent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }

                super.run();
            }
        };

        timer.start();
    }
}
