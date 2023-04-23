package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityBoardBinding;
import com.example.pumpwimo.fragments.SettingsFragment;

public class BoardActivity extends AppCompatActivity {

    private ActivityBoardBinding binding;

    private Intent intent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        putCardColors();

        // todo переход на активность с квестами
        binding.card1.setOnClickListener(v -> {
            intent = new Intent(BoardActivity.this, QuestActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // todo переход на друзей
        binding.card2.setOnClickListener(v -> {
            intent = new Intent(BoardActivity.this, FriendsActivity.class);
            startActivity(intent);
            // todo анимация
        });

//        // todo переход на активность "Избранное"
//        binding.card3.setOnClickListener(v -> {
//            intent = new Intent(BoardActivity.this, );
//            startActivity(intent);
//            // todo анимация
//        });

        // todo переход на активность "Ретинг"
        binding.card5.setOnClickListener(v -> {
            intent = new Intent(BoardActivity.this, RatingActivity.class);
            startActivity(intent);
            // todo анимация
        });

        // todo переход на активность с наградами
        binding.card6.setOnClickListener(v -> {
            intent = new Intent(BoardActivity.this, AwardsActivity.class);
            startActivity(intent);
            // todo анимация
        });

//        // todo переход на активность настроек
//        binding.card7.setOnClickListener(v -> {
//            intent = new Intent(BoardActivity.this, .class);
//            startActivity(intent);
//            // todo анимация
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void putCardColors() {
        binding.card1.setBackgroundResource(R.drawable.fon_1);
        binding.card2.setBackgroundResource(R.drawable.fon_2);
        binding.card3.setBackgroundResource(R.drawable.fon_4);
        binding.card5.setBackgroundResource(R.drawable.fon_5);
        binding.card6.setBackgroundResource(R.drawable.fon_6);
        binding.card7.setBackgroundResource(R.drawable.fon_8);
    }
}