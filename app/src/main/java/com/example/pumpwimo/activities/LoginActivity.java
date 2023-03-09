package com.example.pumpwimo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pumpwimo.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    FirebaseAuth auth; // для авторизации

    FirebaseDatabase db; // для подключения к базе даннных

    DatabaseReference users; // для работы с табличками внутри бд

    int permission; // переменная для проверки

    public final static String STRING_1 = "Введите данные полностью";
    public final static String STRING_2 = "Учтите требования";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // теперь мы поместим данные в наши переменные
        auth = FirebaseAuth.getInstance(); // запускаем авторизацию в бд
        db = FirebaseDatabase.getInstance(); // подключаемся к бд

        /*
        Указываем название таблички, с которой мы будем работать
         */
        // табличка Users - пользователи
        users = db.getReference("Users");

        // авторизация
        binding.buttonLogin.setOnClickListener(v -> {
            // пишем проверку
            //...
            //.........
            auth.signInWithEmailAndPassword(binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() { // успешная авторизация
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(LoginActivity.this, BoardActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(binding.loginLayout, "Неправильные данные", Snackbar.LENGTH_SHORT);
                        }
                    }); //не успешное добавление пользователя
        });

        // создать аккаунт
        binding.buttonCreateAccount.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(i);
        });
    }

    //check() проверяет введённые данные на соответствие требованиям
    private void check() {
        Log.v("Permission", "permission == " + permission);
        Log.i("Check", "check == end");
    }
}