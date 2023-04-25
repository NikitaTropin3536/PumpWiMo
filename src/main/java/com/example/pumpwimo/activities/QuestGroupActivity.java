package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pumpwimo.R;
import com.example.pumpwimo.adapters.QuestGroupViewPagerAdapter;
import com.example.pumpwimo.databinding.ActivityQuestBinding;

public class QuestGroupActivity extends AppCompatActivity {

    private ActivityQuestBinding binding;

    private TextView[] dots;

    // todo адаптер
    private QuestGroupViewPagerAdapter questGroupViewPagerAdapter;

    // todo масссив цветов для индикатора
    private int[] indicatorColors = {
            R.color.orange,
            R.color.red,
            R.color.green,
            R.color.blue,
            R.color.colorBlack_2,
            R.color.white
    };

    public static int where = 0;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    // todo в этом классе есть костыльный код!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.back.setVisibility(View.INVISIBLE);
        // todo кнопочка back
        binding.back.setOnClickListener(v -> {
            binding.slideViewPager.setCurrentItem(getItem(-1), true);
            --QuestGroupActivity.where;
        });

        // todo кнопка следающий
        binding.next.setOnClickListener(v -> {
            binding.slideViewPager.setCurrentItem(getItem(1), true);
            ++QuestGroupActivity.where;
        });

        binding.first.setVisibility(View.INVISIBLE);
        // todo листануть на первый
        binding.first.setOnClickListener(v -> {
            binding.slideViewPager.setCurrentItem(0, true);
            QuestGroupActivity.where = 0;
        });

        // todo листануть на последний
        binding.last.setOnClickListener(v -> {
            binding.slideViewPager.setCurrentItem(5, true);
            QuestGroupActivity.where = 5;
        });

        // todo инициализируем ажаптер
        questGroupViewPagerAdapter = new QuestGroupViewPagerAdapter(this);

        // todo ставим viewPager - у адаптер
        binding.slideViewPager.setAdapter(questGroupViewPagerAdapter);

        setUpIndicator(0);
        binding.slideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpIndicator(int position) {
        dots = new TextView[6];
        binding.indicatorLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white_gr, getApplicationContext().getTheme()));
            binding.indicatorLayout.addView(dots[i]);
        }

        setIndicatorColor(position); // todo ставим цвет индикатору
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // todo это плохой код - так писать не надо!
            setUpIndicator(position);
            if (position == 0) {
                binding.back.setVisibility(View.INVISIBLE);
                binding.first.setVisibility(View.INVISIBLE);
            } else if (position >= 1) {
                binding.back.setVisibility(View.VISIBLE);
                binding.first.setVisibility(View.VISIBLE);
            }
            if (position == 5) {
                binding.next.setVisibility(View.INVISIBLE);
                binding.last.setVisibility(View.INVISIBLE);
            } else {
                binding.next.setVisibility(View.VISIBLE);
                binding.last.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setIndicatorColor(int position) {
        dots[position].setTextColor(getResources().getColor(indicatorColors[position], getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        /*
        todo | метод, благодаря которому мы узнаем,
        todo | на каком элементе находится пользователь
         */
        return binding.slideViewPager.getCurrentItem() + i;
    }

    // todo нажатие на back на нижней панели - переходим на BoardActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}