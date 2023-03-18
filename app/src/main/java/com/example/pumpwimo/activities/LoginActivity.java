package com.example.pumpwimo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.example.pumpwimo.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FirebaseAuth auth; // для авторизации

    private FirebaseDatabase db; // для подключения к базе даннных

    private DatabaseReference users; // для работы с табличками внутри бд

    private int permission; // переменная для проверки

    private final static String STRING_1 = "Введите данные полностью";
    private final static String STRING_2 = "Учтите требования";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // теперь мы поместим данные в наши переменные
        auth = FirebaseAuth.getInstance(); // запускаем авторизацию в бд
        db = FirebaseDatabase.getInstance(); // подключаемся к бд

        /*
        Указываем название таблички, с которой мы будем работать
         */
        // табличка Users - пользователи
        users = db.getReference("Users");

        binding.signInBtn.setOnClickListener(v -> {
            check();

            switch ((permission)) {
                case 1:
                    Snackbar.make(binding.loginLayout, STRING_1, Snackbar.LENGTH_SHORT).show();
                    Log.v("text", STRING_1);
                    break;
                case 2:
                    Snackbar.make(binding.loginLayout, STRING_2, Snackbar.LENGTH_SHORT).show();
                    Log.v("text", STRING_2);
                    break;
                case 3:
                    auth.signInWithEmailAndPassword(binding.usernameET.getText().toString(), binding.usernameET.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() { // успешная авторизация
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(LoginActivity.this, BoardActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(binding.loginLayout, "Неправильные данные\nВозможно Вы не зрегестрированы", Snackbar.LENGTH_SHORT).show();
                                }
                            }); //не успешное добавление пользователя
            }
        });

        // создать аккаунт
        binding.signUpBtn.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(i);
        });
    }

    private void check() {
        if (TextUtils.isEmpty(binding.usernameET.getText().toString())
                || TextUtils.isEmpty(binding.passwordET.getText().toString())) {
            permission = 1; // введите данные полностью
        } else if (!binding.usernameET.getText().toString().contains("@") || binding.passwordET.getText().toString().length() <= 8) {
            permission = 2; // учтите требования
        } else if (binding.usernameET.getText().toString().contains("@") && binding.passwordET.getText().toString().length() > 8) {
            permission = 3; // разрешение
        }
        Log.v("Permission", "permission == " + permission);
        Log.i("Check", "check == end");
    }
}