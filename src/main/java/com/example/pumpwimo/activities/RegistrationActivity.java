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

// в данной активности впервые используется Firebase

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth auth; // для авторизации
    private DatabaseReference users; // для работы с табличками внутри бд

//    // user data access object
//    private UserDao userDao = UserDatabaseApplication.getUserDatabase()
//            .userDao();

    // константы для работы с камерой и директорией с картинками
    private final int PERMISSION_REQ_CODE_1 = 10;
    private final int PERMISSION_REQ_CODE_2 = 11;
    private final int pic_id = 555;
    private final int gal_id = 666;

    // intent для камеры
    private Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    // intent для директории с изображениями
    private Intent intent_file = new Intent();

    // изменил аватарку или нет или нет
    private int change = 0;

    // диалоговое окно для "постановки" аватарки
    private Dialog dialog;

    // Snackbar
    private Snackbar snackbar;
    private View snackbarView;
    private Snackbar.SnackbarLayout snackbarLayout;

    // хороший или плохой хеш пароля
    private int goodHashPassword = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance(); // запускаем авторизацию в бд
        FirebaseDatabase db = FirebaseDatabase.getInstance(); // подключаемся к бд

        /*
        Указываем название таблички, с которой мы будем работать
         */
        // табличка Users - пользователи
        users = db.getReference("Users");
    }
}

//        // обработка нажатия на кнопку создания аккаунта
//        binding.createAcc.setOnClickListener(v -> {
//
//            // сохраняем все пользовательские данные
//            String userName = binding.editName.getText().toString();
//            String userPhone = binding.editPhone.getText().toString();
//            String userMail = binding.editMail.getText().toString();
//            String userPassword = doHashPassword(binding.editPass.getText().toString());
//
//            String userAvatar = "sksllslsls"; // todo доделать

//            // Регистрация пользователя
//            auth.createUserWithEmailAndPassword(userMail, userPassword)
//                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        /*
//                        обработчик события, который вызовет функцию onSuccess только в том случае,
//                        если пользователь будет успешно добавлен в базу данных
//                        */
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
////                            User user = new User(userName,
////                                    userPhone, userMail,
////                                    userPassword, userAvatar);
////                            userDao.save(user); // сохраняем юзера в локальную базу даннных
//
//                                     /*
//                                    добавляем нового пользователя в табличку Users (firebase);
//                                    ключ, по которому мы идентифицируем пользователя - id пользователя
//                                    */
//
//                            users.child(Objects.requireNonNull(FirebaseAuth.getInstance()
//                                            .getCurrentUser()).getUid()).setValue(user)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        /*
//                                        обработчик события, который срботает, когда будет успешное добавление пользователя
//                                        */
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            // переход на "рабочий стол" пользователя
//                                            Intent intent = new Intent(RegistrationActivity.this, BoardActivity.class);
//                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                            finish();
//                                        }
//                                    });
//                        }
//                    });
//        });
//
//        // добавляем рефакторинг в editPhone
//        binding.editPhone.addTextChangedListener(new CurrencyTextWatcherEditPhone());
//
//        // тыкаем на картинку - появляется диалоговое окно
//        binding.circleSteveJobs.setOnClickListener(v -> {
//            showDialogSelect(this);
//        });
//    }

    // метод проверяет введённые данные на соответствие требованиям
//    private int check() {
//    }

    // тыкаем на кнопочку back на нижней панели
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }
//
//    // уникальный хеш пароля
//    @SuppressLint("HardwareIds")
//    private String doHashPassword(String password) {
//        try {
//
//            /**
//             * класс MessageDigest предоставляет приложениям
//             * функциональность алгоритма дайджеста сообщений,
//             * такого как SHA-1 или SHA-256.
//             */
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//
//            md.update(password
//                    .getBytes(StandardCharsets.UTF_8));
//
//            goodHashPassword = 1;
//            // все хорошо, мы сохранили хороший хеш пароля
//
//            return Hex.toHexString(
//                    md.digest()) +
//                    Settings.Secure.getString(
//                    this.getContentResolver(),
//                    Settings.Secure.ANDROID_ID
//            );
//        } catch (NoSuchAlgorithmException exception) {
//
//            // все плохо, мы сохранили не тот хеш, который хотели
//            return password + binding.editPhone.getText().toString();
//        }
//    }
//
//    // далее идет ряд методов для работы с камерой
//
//    // проверка на permissions для камеры
//    private boolean allPermissionsGrantedCamera() {
//
//        /**
//         * ContextCompat - помощник для доступа к функциям в контексте (context)
//         */
//
//        /**
//         * checkSelfPermission - метод, который определяет,
//         * было ли вам предоставлено конкретное разрешение.
//         */
//
//        /**
//         * PacketManager - класс для извлечения различного рода информации,
//         * относящейся к пакетам приложений,
//         * которые в данный момент установлены на устройстве.
//         */
//        return ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//        ) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    // проверка на permissions для директории с картинками
//    private boolean allPermissionsGrantedGal() {
//        return ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//        ) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    // ставим аватарку - камера
//    private void doAvatar() {
//        if (allPermissionsGrantedCamera()) {
//            // tpermissions предоставлены
//
//            startCamera(); // запуск камеры
//        } else {
//            // permissions не предоставлены - запрашиваем их - вылетает окошко
//
//            /**
//             * ActivityCompat - помощник для доступа к функциям Activity
//             */
//
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[] {
//                            Manifest.permission.CAMERA
//                    }, // список запрашиваемых permissions
//                    PERMISSION_REQ_CODE_1
//            );
//        }
//    }
//
//    // ставим аватарку - файлик
//    private void openFile() {
//        if (allPermissionsGrantedGal()) {
//
//            startDirectoryImages(); // заходим в хранилище изображений
//        } else {
//
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[] {
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                    },
//                    PERMISSION_REQ_CODE_2
//            );
//        }
//    }
//
//    // делаем photo
//    private void startCamera() {
//        startActivityForResult(camera_intent, pic_id);
//        // теперь мы на экране с камерой
//
//        // сохранение фото происходит автоматом, ибо вы делаете фотку для галереи
//    }
//
//    // tоткрывается директория с файликами, мы выбираем картинку
//    private void startDirectoryImages() {
//        intent_file.setType("image/*");
//        intent_file.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        startActivityForResult(Intent.createChooser(intent_file,
//                "Task image_file"), gal_id);
//
//        // теперь мы на экране с директорией для картинок
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == pic_id) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            remove();
//            binding.circleSteveJobs.setImageBitmap(photo);
//        } else if (requestCode == gal_id) {
//            Uri uri = data.getData();
//            remove();
//            binding.circleSteveJobs.setImageURI(uri);
//        }
//    }
//
//    // удаляем текст и фон
//    private void remove() {
//        if (change == 0) {
//            binding.photo.setVisibility(View.GONE);
//            binding.fonCamera.setVisibility(View.GONE);
//        }
//        ++change;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_REQ_CODE_1) {
//            if (allPermissionsGrantedCamera()) {
//                /**
//                 * если после запроса на permissions они предоставлены,
//                 * то мы запускам камеру
//                 */
//                startCamera();
//            }
//            // если не предоставлены - просто исчезает разрешение
//        } else if (requestCode == PERMISSION_REQ_CODE_2) {
//            if (allPermissionsGrantedGal()) {
//                startDirectoryImages();
//            }
//        }
//    }
//
//    // создание диалогового окна
//    private void showDialogSelect(Context context) {
//        dialog = new Dialog(context);
//        dialog.setContentView(R.layout.alert_registration);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView select_1;
//        TextView select_2;
//
//        select_1 = dialog.findViewById(R.id.select_1);
//        select_2 = dialog.findViewById(R.id.select_2);
//
//        select_1.setOnClickListener(v -> {
//            dialog.dismiss();
//            doAvatar();
//        });
//        select_2.setOnClickListener(v -> {
//            dialog.dismiss();
//            openFile();
//        });
//        dialog.show();
//    }
