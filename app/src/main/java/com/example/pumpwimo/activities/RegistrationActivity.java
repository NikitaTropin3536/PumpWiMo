package com.example.pumpwimo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    ActivityRegistrationBinding binding;

    FirebaseAuth auth; // для авторизации

    FirebaseDatabase db; // для подключения к базе даннных

    DatabaseReference users; // для работы с табличками внутри бд

    int permission; // переменная для проверки регитсрации

    public final static String STRING_1 = "Введите данные полностью";
    public final static String STRING_2 = "Учтите требования";

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_registration);

        // теперь мы поместим данные в наши переменные
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance(); // подключаемся к бд

        /*
        Указываем название таблички, с которой мы будем работать
         */
        // табличка Users - пользователи
        users = db.getReference("Users");

        // обработка нажатия на кнопку создания аккаунта
        binding.buttonCreateAccount.setOnClickListener(v -> {
            check(); // 1. проверка

            // 2. совпадение - не думаю
            switch (permission) {
                case 1:
                    Snackbar.make(binding.registrationLayout, STRING_1, Snackbar.LENGTH_SHORT).show();
                    Log.v("text", STRING_1);
                    break;
                case 2:
                    Snackbar.make(binding.registrationLayout, STRING_2, Snackbar.LENGTH_SHORT).show();
                    Log.v("text", STRING_2);
                    break;
                case 3:
                    // Регистрация пользователя
                    auth.createUserWithEmailAndPassword(binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                /*
                                обработчик события, который вызовет функцию onSuccess только в том случае,
                                если пользователь будет успешно добавлен в базу данных
                                */
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User();
                                    user.setEmail(binding.inputEmail.getText().toString());
                                    user.setPassword(binding.inputPassword.getText().toString());
                                    user.setPhone(binding.yourTelephone.getText().toString());
                                    user.setNickName(binding.nickName.getText().toString());

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
                                                    showСonfirmationWindow(); // вызывается метод уведомления, жмем н кнопку и все!!!!
                                                }
                                            });

                                }
                            }); // создать пользователя с email - ом и паролем
            }
        });
    }


    //check() проверяет введённые данные на соответствие требованиям
    private void check() {
        if (TextUtils.isEmpty(binding.inputEmail.getText().toString())
                || TextUtils.isEmpty(binding.inputPassword.getText().toString())
                || TextUtils.isEmpty(binding.yourTelephone.getText().toString())
                || TextUtils.isEmpty(binding.nickName.getText().toString())) {
            permission = 1; // введите данные полностью
        } else if (!binding.inputEmail.getText().toString().contains("@") || binding.inputPassword.getText().toString().length() <= 8) {
            permission = 2; // учтите требования
        } else if (binding.inputEmail.getText().toString().contains("@") && binding.inputPassword.getText().toString().length() > 8) {
            permission = 3; // разрешение
        }
        Log.v("Permission", "permission == " + permission);
        Log.i("Check", "check == end");
    }

    // метод вывода подтверждения регистрации
    private void showСonfirmationWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this); // создаем некий объект

        LayoutInflater inflater = LayoutInflater.from(this);

        View register_confirmation = inflater.inflate(R.layout.register_confirmation_window, null);

        // ставим нашу разметку
        dialog.setView(register_confirmation);

        Button buttonAction = findViewById(R.id.buttonAction);
        buttonAction.setOnClickListener(v -> {
            Intent i = new Intent(RegistrationActivity.this, BoardActivity.class);
            startActivity(i);
            finish();
        });
    }
}