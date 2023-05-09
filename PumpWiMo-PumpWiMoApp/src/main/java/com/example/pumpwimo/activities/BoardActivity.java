package com.example.pumpwimo.activities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityBoardBinding;
import com.google.android.material.navigation.NavigationView;

public class BoardActivity extends AppCompatActivity {

    private ActivityBoardBinding binding;

    // для drawer navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    // для перехода на фрагменыт активности
    private Intent intent;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.nav_quests:
                        break;

                    case R.id.nav_rating:
                        break;

                    case R.id.nav_friends:
                        break;

                    case R.id.nav_awards:
                        break;

                    case R.id.nav_success:
                        break;

                    case R.id.nav_favourites:
                        break;

                    case R.id.nav_settings:
                        break;

                    case R.id.nav_obr_cv:
                        break;

                    case R.id.nav_logout:
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

//        putCardColors();
//
//        // переход на активность с квестами
//        binding.card1.setOnClickListener(v -> {
//            intent = new Intent(BoardActivity.this, QuestGroupActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });
//
//        // переход на друзей
//        binding.card2.setOnClickListener(v -> {
//            intent = new Intent(BoardActivity.this, FriendsActivity.class);
//            startActivity(intent);
//            // todo анимация
//        });
//
//        // переход на активность "Избранное"
//        binding.card3.setOnClickListener(v -> {
//            intent = new Intent(BoardActivity.this, ActivitySuccess.class);
//            startActivity(intent);
//            // todo анимация
//        });
//
//        // переход на активность "Рейтинг"
//        binding.card5.setOnClickListener(v -> {
//            intent = new Intent(BoardActivity.this, RatingActivity.class);
//            startActivity(intent);
//            // todo анимация
//        });
//
//        // переход на фрагмент с наградами
//        binding.card6.setOnClickListener(v -> {
//            // todo анимация
//        });
//
//        // переход на фрагмент настроек
//        binding.card7.setOnClickListener(v -> {
//            // todo анимация
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

//    @ColorInt
//    private int color(@ColorRes int res) {
//        return ContextCompat.getColor(this, res);
//    }

    // ставим цвета нашим карточкам
//    private void putCardColors() {
//        binding.card1.setBackgroundResource(R.drawable.fon_1);
//        binding.card2.setBackgroundResource(R.drawable.fon_2);
//        binding.card3.setBackgroundResource(R.drawable.fon_4);
//        binding.card5.setBackgroundResource(R.drawable.fon_5);
//        binding.card6.setBackgroundResource(R.drawable.fon_6);
//        binding.card7.setBackgroundResource(R.drawable.fon_8);
//    }
}