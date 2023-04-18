package com.example.pumpwimo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivitySplashscreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // создаем новый поток
        Thread thread = new Thread(new MyThread());
        thread.start();
    }

    // переход на новую активность
    private void transition() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(450);
            } catch (InterruptedException ignored) {
            }
            // проходит 0, 45 секунды - ереход
            transition();
        }
    }
}