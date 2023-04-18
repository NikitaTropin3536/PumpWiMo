package com.example.pumpwimo.activities;

import static com.example.pumpwimo.R.*;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityRegistrationBinding;
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

    private int permission; // todo переменная для проверки требований к введенным данным

    // todo snackbar строчки
    private final static String STRING_1 = "Введите данные полностью";
    private final static String STRING_2 = "Учтите требования";

    // todo уникальный идентификатор устройства
//    @SuppressLint("HardwareIds")
//    private final String android_id = Settings.Secure.getString(this.getContentResolver(),
//            Settings.Secure.ANDROID_ID);

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

    // todo для определения нашего "местоположения"
    private int where = 0;

    // изменял иили нет
    private int change = 0;

    // todo диалоговое окно
    private Dialog dialog;

    @SuppressLint({"ShowToast", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // todo теперь мы поместим данные в наши переменные
        auth = FirebaseAuth.getInstance(); // запускаем авторизацию в бд
        // todo для подключения к базе даннных
        FirebaseDatabase db = FirebaseDatabase.getInstance(); // подключаемся к бд

        /*
        todo Указываем название таблички, с которой мы будем работать
         */
        // todo табличка Users - пользователи
        users = db.getReference("Users");

        // todo обработка нажатия на кнопку создания аккаунта
        binding.createAcc.setOnClickListener(v -> {
            check(); // todo 1. проверка требований

            // todo 2. совпадение - не думаю
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
                    // todo Регистрация пользователя
                    String passwordDatabase = doHashPassword(binding.editPass.getText().toString());
                    auth.createUserWithEmailAndPassword(binding.editMail.getText().toString(), passwordDatabase)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                /*
                                обработчик события, который вызовет функцию onSuccess только в том случае,
                                если пользователь будет успешно добавлен в базу данных
                                (добавляем в Firebase - TODO 1)
                                */
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User(
                                            binding.editName.getText().toString(),
                                            binding.editPhone.getText().toString(),
                                            binding.editMail.getText().toString(),
                                            passwordDatabase,
                                            getUriPicAvatar(binding.circleSteveJobs) // todo в базу даных передается строчка, в которой лежит uri
                                    );
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
                                                    overridePendingTransition(anim.slide_in_right, anim.slide_out_left);
                                                    finish();
                                                }
                                            });
                                }
                            });
            }
        });

        // todo тыкаем на картинку - появляется диалоговое окно
        binding.circleSteveJobs.setOnClickListener(v -> {
            showDialogSelect(this);
        });
    }

    // todo метод проверяет введённые данные на соответствие требованиям
    private void check() {
        if (TextUtils.isEmpty(binding.editName.getText().toString())
                || TextUtils.isEmpty(binding.editPhone.getText().toString())
                || TextUtils.isEmpty(binding.editMail.getText().toString())
                || TextUtils.isEmpty(binding.editPass.getText().toString())) {
            permission = 1; // введите данные полностью
        } else if (!binding.editMail.getText().toString().contains("@")
                || binding.editPass.getText().toString().length() < 8) {
            permission = 2; // учтите требования
        } else if (binding.editMail.getText().toString().contains("@")
                && binding.editPass.getText().toString().length() >= 8) {
            permission = 3; // разрешение
        }
        Log.v("Permission", "permission == " + permission);
    }

    // todo тыкаем на кнопочку back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(anim.slide_in_left, anim.slide_out_right);
    }

    // todo уникальный хеш пароля
    private String doHashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            return Hex.toHexString(md.digest()); //+ android_id;
        } catch (NoSuchAlgorithmException exception) {
            return password + binding.editPhone.getText().toString();
        }
    }

    // todo получаем String c uri картинки на нашей аватарке
    private String getUriPicAvatar(CircleImageView circleImageView) {
        circleImageView.buildDrawingCache();
        Bitmap avatar = circleImageView.getDrawingCache();
        bytes = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(RegistrationActivity.
                this.getContentResolver(),
                avatar,
                "Avatar",
                null);
        return Uri.parse(path).toString();
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
        dialog.setContentView(layout.alert_registration);
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
}
