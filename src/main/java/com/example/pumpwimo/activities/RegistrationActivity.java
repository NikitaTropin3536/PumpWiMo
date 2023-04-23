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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityRegistrationBinding;
import com.example.pumpwimo.other.editRefactor.CurrencyTextWatcherEditPhone;
import com.example.pumpwimo.usersDatabase.User;
import com.example.pumpwimo.usersDatabase.UserDao;
import com.example.pumpwimo.usersDatabase.UserDatabaseApplication;

import org.bouncycastle.util.encoders.Hex;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

// в данной активности впервые используется Firebase
public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth auth; // todo для авторизации
    private DatabaseReference users; // todo для работы с табличками внутри бд

    // todo Snackbar строчки
    private final static String STRING_1 = "Введите данные полностью";
    private final static String STRING_2 = "Неправильно введенный mail";
    private final static String STRING_3 = "Длина пароля должна быть больше или равна 8";

    // todo для uri
    private ByteArrayOutputStream bytes;

    // todo константы для работы с камерой и директорией с картинками
    private final int PERMISSION_REQ_CODE_1 = 10;
    private final int PERMISSION_REQ_CODE_2 = 11;
    private final int pic_id = 555;
    private final int gal_id = 666;

    // todo user data access object
    private UserDao userDao = UserDatabaseApplication.getUserDatabase().userDao();

    // todo intent для камеры
    private Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    // todo intent для директории с изображениями
    private Intent intent_file = new Intent();

    // изменял иили нет
    private int change = 0;

    // todo диалоговое окно
    private Dialog dialog;
    // todo snackbar
    private Snackbar snackbar;
    private View snackbarView;
    private Snackbar.SnackbarLayout snackbarLayout;

    // todo для определения нашего "местоположения" - камера или директория с картинками
    private int where = 0;

    // todo пароль хорошо захешировался или нет
    private int good = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance(); // запускаем авторизацию в бд
        FirebaseDatabase db = FirebaseDatabase.getInstance(); // подключаемся к бд

        /*
        todo Указываем название таблички, с которой мы будем работать
         */
        // todo табличка Users - пользователи
        users = db.getReference("Users");

        // todo сохраняем все пользовательские данные
        String userName = binding.editName.getText().toString();
        String userPhone = binding.editPhone.getText().toString();
        String userMail = binding.editMail.getText().toString();
        String userPassword = doHashPassword(binding.editPass.getText().toString());
//        Uri picUri = getUriPicAvatar(this, goToBitmap(binding.circleSteveJobs));

        // todo обработка нажатия на кнопку создания аккаунта
        binding.createAcc.setOnClickListener(v -> {

            // todo 2. совпадение - не думаю
            switch (check(userName, userPhone, userMail, userPassword)) {
                case 1:
                    showSnackbarNotPermission(this, STRING_1);
                    Log.v("permission", STRING_1);
                    break;
                case 2:
                    showSnackbarNotPermission(this, STRING_2);
                    Log.v("permission", STRING_2);
                    break;
                case 3:
                    showSnackbarNotPermission(this, STRING_3);
                    Log.v("permission", STRING_3);
                    break;
                case 4:
                    // todo Регистрация пользователя
                    auth.createUserWithEmailAndPassword(userMail, userPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                /*
                                обработчик события, который вызовет функцию onSuccess только в том случае,
                                если пользователь будет успешно добавлен в базу данных
                                (добавляем в Firebase - TODO 1)
                                */
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User(userName, userPhone, userMail, userPassword, "!");
                                    userDao.save(user); // сохраняем юзера в локальную базу даннных - TODO 2)

                                     /*
                                    добавляем нового пользователя в табличку users (firebase);
                                    ключ, по которому мы идентифицируем пользователя - id пользователя
                                    */

                                    users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                /*
                                                обработчик события, который срботает, когда будет успешное добавление пользователя
                                                */
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    // переход на "рабочий стол" пользователя
                                                    Intent intent = new Intent(RegistrationActivity.this, BoardActivity.class);
                                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                    finish();
                                                }
                                            });
                                }
                            });
            }
        });

        // todo добавляем авторефакторинг в ediText - ы
        binding.editPhone.addTextChangedListener(new CurrencyTextWatcherEditPhone());

        // todo тыкаем на картинку - появляется диалоговое окно
        binding.circleSteveJobs.setOnClickListener(v -> {
            showDialogSelect(this);
        });
    }

    // todo метод проверяет введённые данные на соответствие требованиям
    private int check(String userName, String userPhone, String userMail, String userPassword) {
        if (TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(userPhone)
                || TextUtils.isEmpty(userMail)
                || TextUtils.isEmpty(userPassword)) {
            return 1; // введите данные полностью
        } if (!userMail.contains("@")) {
           return 2; // email должен содержать @
        } if (userPassword.length() < 8) {
            return 3; // пароль должен содержать 8 и более символов
        }
        return 4;
    }

    // todo тыкаем на кнопочку back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // todo уникальный хеш пароля
    @SuppressLint("HardwareIds")
    private String doHashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            good = 1; // todo все хорошо, мы сохранили хороший хеш пароля
            return Hex.toHexString(md.digest()) + Settings.Secure.getString(
                    this.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
        } catch (NoSuchAlgorithmException exception) {
            // todo все плохо, мы сохранили не тот хеш, который хотели
            return password + binding.editPhone.getText().toString();
        }
    }

    // todo получаем Uri картинки на нашей аватарке
    private Uri getUriPicAvatar(Context context, Bitmap avatar) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), avatar, "Avatar", null);
        Uri picUri = Uri.parse(path);
        return picUri;
    }

    private Bitmap goToBitmap(CircleImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap image = drawable.getBitmap();
        return image;
    }

    // todo проверка на permission для камеры
    private boolean allPermissionsGrantedCamera() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    // todo проверка на permission для директории с картинками
    private boolean allPermissionsGrantedGal() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    // todo ставим аватарку - камера
    private void doAvatar() {
        if (allPermissionsGrantedCamera()) {
            // todo permissions предоставлены
            startCamera(); // todo запуск камеры
        } else {
            // todo если permissions не предоставлены - запрашиваем - вылетает окошко
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA}, // список запрашиваемых permissions
                    PERMISSION_REQ_CODE_1
            );
        }
        // todo сохранение фото происходит автоматом, ибо вы делаете фотку для галереи
    }

    // todo ставим аватарку - файлик
    private void openFile() {
        if (allPermissionsGrantedGal()) {
            startDirectoryImages();
        } else {
            // todo если permissions не предоставлены - запрашиваем - вылетает окошко
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQ_CODE_2
            );
        }
        // todo сохранение фото происходит автоматом, ибо вы делаете фотку для галереи
    }

    // todo делаем photo
    private void startCamera() {
        startActivityForResult(camera_intent, pic_id);
        where = 1; //todo теперь мы на экране с камерой
    }

    // todo открывается директория с файликами, мы выбираем картинку
    private void startDirectoryImages() {
        intent_file.setType("image/*");
        intent_file.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent_file, "Task image_file"), gal_id);
        where = 2; //todo теперь мы на экране с директорией
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            remove();
            binding.circleSteveJobs.setImageBitmap(photo);
        } else if (requestCode == gal_id) {
            Uri uri = data.getData();
            remove();
            binding.circleSteveJobs.setImageURI(uri);
        }
    }

    // todo удаляем текст и фон
    private void remove() {
        if (change == 0) {
            binding.photo.setVisibility(View.GONE);
            binding.fonCamera.setVisibility(View.GONE);
        }
        ++change;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQ_CODE_1) {
            if (allPermissionsGrantedCamera()) {
                /**
                 * todo | если после запроса на permissions они предоставлены,
                 * todo | то мы запускам камеру
                 */
                startCamera();
            }
            // todo если не предоставлены - просто исчезает разрешение
        } else if (requestCode == PERMISSION_REQ_CODE_2) {
            if (allPermissionsGrantedCamera()) {
                startDirectoryImages();
            }
        }
    }

    // todo создание диалогового окна
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

    private void showSnackbarNotPermission(Context context, String s) {
        snackbar = Snackbar.make(binding.registerLayout, s, Snackbar.LENGTH_LONG);
        snackbarView = getLayoutInflater().inflate(R.layout.snackbar_registration, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.addView(snackbarView, 0);
        snackbar.show();
    }
}