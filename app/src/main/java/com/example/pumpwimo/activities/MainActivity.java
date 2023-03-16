package com.example.pumpwimo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pumpwimo.R;
import com.example.pumpwimo.adapters.ViewPagerAdapter;
import com.example.pumpwimo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    TextView[] dots;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.backBtn.setVisibility(View.INVISIBLE);
        binding.backBtn.setOnClickListener(v -> {
            if (getItem(0) > 0) {
                binding.slideViewPager.setCurrentItem(getItem(-1), true);
            }
        });

        binding.nextBtn.setOnClickListener(v -> {
            if (getItem(0) < 4) {
                binding.slideViewPager.setCurrentItem(getItem(1), true);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.skipButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        viewPagerAdapter = new ViewPagerAdapter(this);

        binding.slideViewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        // что с ней не так?
        binding.slideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpIndicator(int position) {
        dots = new TextView[5];
        binding.indicatorLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black, getApplicationContext().getTheme()));
            binding.indicatorLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.colorWhite, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            if (position == 0) {
                binding.backBtn.setVisibility(View.INVISIBLE);
            } else if (position >= 1) {
                binding.backBtn.setVisibility(View.VISIBLE);
            } else {
                binding.backBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // 1.Почему мы прибавляем i?
    private int getItem(int i) {
        return binding.slideViewPager.getCurrentItem() + i;
    }
}
