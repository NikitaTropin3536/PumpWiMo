package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityBoardBinding;
import com.example.pumpwimo.fragments.HomeFragment;
import com.example.pumpwimo.fragments.ProfileFragment;
import com.example.pumpwimo.fragments.SettingsFragment;

import io.ak1.OnBubbleClickListener;

public class BoardActivity extends AppCompatActivity {

    private ActivityBoardBinding binding;

    private static final String TAG = BoardActivity.class.getSimpleName();

    private HomeFragment homefragment = new HomeFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        // панель навигации
        binding.bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Fragment selectedFragment = null; // пока выбранный fragment = null
                switch (i) {
                    case R.id.menuProfile:
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.menuHome:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.menuSettings:
                        selectedFragment = new SettingsFragment();
                        break;
                }
                if (selectedFragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                } else {
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}