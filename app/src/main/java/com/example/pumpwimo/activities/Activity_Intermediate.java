package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
        });

        // кнопка - Войти
        TextView come = findViewById(R.id.come);
        come.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Intermediate.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}