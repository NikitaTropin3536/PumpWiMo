package com.example.pumpwimo.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.pumpwimo.R;

import com.example.pumpwimo.databinding.ActivityRegistrationBinding;
import com.example.pumpwimo.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    private FirebaseAuth auth; // для авторизации

    private FirebaseDatabase db; // для подключения к базе даннных

    private DatabaseReference users; // для работы с табличками внутри бд

    private int permission; // переменная для проверки регитсрации

    private final static String STRING_1 = "Введите данные полностью";
    private final static String STRING_2 = "Учтите требования";

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
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

        // обработка нажатия на кнопку создания аккаунта
        binding.createAcc.setOnClickListener(v -> {
            check(); // 1. проверка требований

            // 2. совпадение - не думаю
            switch (permission) {
                case 1:
                    Snackbar.make(binding.registerLayout, STRING_1, Snackbar.LENGTH_SHORT).show();
                    Log.v("text", STRING_1);
                    break;
                case 2:
                    Snackbar.make(binding.registerLayout, STRING_2, Snackbar.LENGTH_SHORT).show();
                    Log.v("text", STRING_2);
                    break;
                case 3:

                    // Регистрация пользователя
                    auth.createUserWithEmailAndPassword(binding.mailET.getText().toString(), binding.passwordET.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                /*
                                обработчик события, который вызовет функцию onSuccess только в том случае,
                                если пользователь будет успешно добавлен в базу данных
                                */
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User();
                                    user.setEmail(binding.mailET.getText().toString());
                                    user.setPassword(binding.passwordET.getText().toString());
                                    user.setPhone(binding.telephoneET.getText().toString());
                                    user.setNickName(binding.nameET.getText().toString());

                                     /*
                                    добавляем нового пользователя в табличку users,
                                    ключ, по которому мы идентифицируем пользователя - id пользователя
                                    */

                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                /*
                                                обработчик события, который срботает, когда будет успешное добавление пользователя
                                                */
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    // Написать НАШЕ УВЕДОМЛЕНИЕ!!!!!!!!!!
                                                }
                                            });

                                }
                            }); // создать пользователя с email - ом и паролем
            }
        });
    }

    // check() проверяет введённые данные на соответствие требованиям
    private void check() {
        if (TextUtils.isEmpty(binding.mailET.getText().toString())
                || TextUtils.isEmpty(binding.passwordET.getText().toString())
                || TextUtils.isEmpty(binding.telephoneET.getText().toString())
                || TextUtils.isEmpty(binding.nameET.getText().toString())) {
            permission = 1; // введите данные полностью
        } else if (!binding.mailET.getText().toString().contains("@") || binding.passwordET.getText().toString().length() <= 8) {
            permission = 2; // учтите требования
        } else if (binding.mailET.getText().toString().contains("@") && binding.passwordET.getText().toString().length() > 8) {
            permission = 3; // разрешение
        }
        Log.v("Permission", "permission == " + permission);
        Log.i("Check", "check == end");
    }

    private void showTheDialog() {
        // создем диалоговое окно
        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());

        View register_confimation_window = getLayoutInflater().inflate(R.layout.register_confirmation_window, null);

        Button buttonAction = register_confimation_window.findViewById(R.id.buttonAction);

        buttonAction.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, BoardActivity.class);
            startActivity(intent);
            finish();
        });

        dialog.setView(register_confimation_window);
        AlertDialog dialog2 = dialog.create();
        dialog2.show();
        //
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}