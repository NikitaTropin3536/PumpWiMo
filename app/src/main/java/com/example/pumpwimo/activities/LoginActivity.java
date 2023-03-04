package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pumpwimo.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // войти
        binding.buttonLogin.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, BoardActivity.class);
            startActivity(i);
            finish();
        });

        // создать аккаунт
        binding.buttonCreateAccount.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(i);
            finish();
        });

        // совпадение или нет
    }
}