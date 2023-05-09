package com.example.pumpwimo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityRegistrationBinding;
import com.example.pumpwimo.other.editRefactor.CurrencyTextWatcherEditPhone;
import com.example.pumpwimo.usersDatabase.User;
import com.example.pumpwimo.usersDatabase.UserDao;

import org.bouncycastle.util.encoders.Hex;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

// TDOO ПРОВЕРЕНО

/** в данной активности впервые используется Firebase */

/** активность регистрации */
public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    /** для авторизации */
    private FirebaseAuth auth;

    /** для работы с табличками в базе данных */
    private DatabaseReference users;

    /** user data access object */
    private UserDao userDao = UserDatabaseApplication
            .getUserDatabase()
            .userDao();

    /** константы для работы с камерой и директорией с картинками */
    private final int PERMISSION_REQ_CODE_1 = 10;
    private final int PERMISSION_REQ_CODE_2 = 11;
    private final int pic_id = 555;
    private final int gal_id = 666;

    /** intent для камеры */
    private Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    /** intent для директории с изображениями */
    private Intent intent_file = new Intent();

    /**
     * пользователь изменил аватарку или нет или нет
     * 1 - изменил
     * 0 - нет
     * */
    private int change = 0;

    /** диалоговое окно для аватарки */
    private Dialog dialog;

    /** Snackbar */
    private Snackbar snackbar;
    private View snackbarView;
    private Snackbar.SnackbarLayout snackbarLayout;

    /**
     * хороший или плохой хеш пароля
     * 1 - хороший хеш
     * 0 - плохой хеш
     * */
    private int goodHashPassword = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /** запускаем авторизацию в бд */
        auth = FirebaseAuth.getInstance();

        /** подключаемся к бд */
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        /**
        * Указываем название таблички, с которой мы будем работать
        * табличка Users - пользователи
         */
        users = db.getReference("Users");

        /** обработка нажатия на кнопку создания аккаунта */
        binding.createAcc.setOnClickListener(v -> {

            /** сохраняем все пользовательские данные в переменне */
            String userName = binding.editName.getText().toString();
            String userPhone = binding.editPhone.getText().toString();
            String userMail = binding.editMail.getText().toString();
            String userPassword = binding.editPass.getText().toString();

            String userAvatar = "sksllslsls";
            // todo доделать
            // todo должен брать uri картинки

            /** Регистрация пользователя */
            auth.createUserWithEmailAndPassword(userMail, userPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        /**
                        обработчик события, который вызовет функцию onSuccess только в том случае,
                        если пользователь будет успешно добавлен в базу данных
                        */
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            /** создаем наешго пользователя */
                            User user = new User(userName, userPhone,
                                    userMail, userPassword, userAvatar);

                            /** сохраняем пользователя в локальную базу данных */
                            userDao.save(user);
                             /**
                             * добавляем нового пользователя в табличку Users (Firebase);
                             * ключ, по которому мы идентифицируем пользователя - id
                             */

                            users.child(Objects.requireNonNull(
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()).getUid())
                                    .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        /**
                                        * обработчик события, который срботает только тогда, когда будет успешное добавление пользователя
                                        */
                                        @Override
                                        public void onSuccess(Void unused) {
                                            /** переход на "рабочий стол" пользователя */
                                            Intent intent = new Intent(RegistrationActivity.this, BoardActivity.class);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                            finish();
                                        }
                                    });
                        }
                    });
        });
    }

        /** добавляем автоматический рефакторинг в editPhone */
        binding.editPhone.addTextChangedListener(
            new CurrencyTextWatcherEditPhone()
        );

        /** тыкаем на картинку - появляется диалоговое окно */
        binding.circleSteveJobs.setOnClickListener(v -> {
            showDialogSelect(this);
        });

    }

    // далее идет ряд методов для работы с камерой

    /** проверка на permissions для камеры */
    private boolean allPermissionsGrantedCamera() {
        /**
         * ContextCompat - помощник для доступа к функциям в контексте (context)
         */

        /**
         * checkSelfPermission - метод, который определяет,
         * было ли вам предоставлено конкретное разрешение.
         */

        /**
         * PacketManager - класс для извлечения различного рода информации,
         * относящейся к пакетам приложений,
         * которые в данный момент установлены на устройстве.
         */
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    /** проверка на permissions для директории с картинками */
    private boolean allPermissionsGrantedGal() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    /** ставим аватарку - 1. камера */
    private void doAvatar() {
        if (allPermissionsGrantedCamera()) {
            /** permissions предоставлены */
            startCamera(); /** запускаем камеру */
        } else {
            /** permissions не предоставлены - запрашиваем их - вылетает окошко */

            /**
             * ActivityCompat - помощник для доступа к функциям Activity
             */

            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.CAMERA
                    }, // список запрашиваемых permissions
                    PERMISSION_REQ_CODE_1
            );
        }
    }

    /** ставим аватарку - 2. файлик (картинка из директории) */
    private void openFile() {
        if (allPermissionsGrantedGal()) {
            startDirectoryImages(); /** заходим в хранилище изображений */
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQ_CODE_2
            );
        }
    }

    /** делаем photo */
    private void startCamera() {
        startActivityForResult(camera_intent, pic_id);
        /**
         * сохранение фото происходит автоматом, так как вы делаете фотку для галереи
         */
    }

    /** открывается директория с файликами, мы выбираем картинку */
    private void startDirectoryImages() {
        // todo а что тут творится?
        intent_file.setType("image/*");
        intent_file.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent_file,
                "Task image_file"), gal_id);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) { // фотка
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            remove();
            binding.circleSteveJobs.setImageBitmap(photo);
        } else if (requestCode == gal_id) { // файлик
            Uri uri = data.getData();
            remove();
            binding.circleSteveJobs.setImageURI(uri);
        }
    }

    // удаляем текст и фон с аватарки
    private void remove() {
        if (change == 0) {
            binding.photo.setVisibility(View.GONE);
            binding.fonCamera.setVisibility(View.GONE);
        }
        ++change;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQ_CODE_1) {
            if (allPermissionsGrantedCamera()) {
                /**
                 * если после запроса на permissions они предоставлены,
                 * то мы запускам камеру
                 */
                startCamera();
            }
            /** если не предоставлены - просто исчезает разрешение */
        } else if (requestCode == PERMISSION_REQ_CODE_2) {
            if (allPermissionsGrantedGal()) {
                startDirectoryImages();
            }
        }
    }

    /** создание диалогового окна */
    private void showDialogSelect(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_registration);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView select_1;
        TextView select_2;

        select_1 = dialog.findViewById(R.id.select_1);
        select_2 = dialog.findViewById(R.id.select_2);

        select_1.setOnClickListener(v -> {
            dialog.dismiss();
            doAvatar();
        });

        select_2.setOnClickListener(v -> {
            dialog.dismiss();
            openFile();
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /** метод проверяет введённые данные на соответствие требованиям */
    private int check(String userName,
                      String userPhone,
                      String userMail,
                      String userPassword) {
    }

    /** метод возвращает уникальный хеш пароля */
    @SuppressLint("HardwareIds")
    private String doHashPassword(String password) {

        // todo а что тут вообще происходит?
        try {
            /**
             * класс MessageDigest предоставляет приложениям
             * функциональность алгоритма дайджеста сообщений,
             * такого как SHA-1 или SHA-256.
             */
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes(StandardCharsets.UTF_8));

            // все хорошо, мы сохранили хороший хеш пароля, не произошло никаких ошибок
            goodHashPassword = 1;

            return Hex.toHexString(md.digest())
                    + Settings.Secure.getString
                    (this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (NoSuchAlgorithmException exception) {
            // все плохо, мы сохранили не тот хеш, который хотели
            return password + binding.editPhone.getText().toString();
        }
    }
}