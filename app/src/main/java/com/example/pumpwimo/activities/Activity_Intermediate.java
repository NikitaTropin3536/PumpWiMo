package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.pumpwimo.R;

public class Activity_Intermediate extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // кнопка - Начать
        Button buttonBegin = findViewById(R.id.begin);
        buttonBegin.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Intermediate.this, RegistrationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // кнопка - Войти
        TextView come = findViewById(R.id.come);
        come.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Intermediate.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (MainActivity.whatIsIt == 1) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (MainActivity.whatIsIt == 2) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

}