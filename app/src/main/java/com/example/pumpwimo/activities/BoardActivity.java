package com.example.pumpwimo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.pumpwimo.R;
import com.example.pumpwimo.databinding.ActivityBoardBinding;
import com.example.pumpwimo.fragments.Homefragment;
import com.example.pumpwimo.fragments.ProfileFragment;
import com.example.pumpwimo.fragments.SettingsFragment;

import io.ak1.OnBubbleClickListener;

public class BoardActivity extends AppCompatActivity {

    private ActivityBoardBinding binding;

    private static final String TAG = BoardActivity.class.getSimpleName();

//    private Homefragment homefragment = new Homefragment();
//    private ProfileFragment profileFragment = new ProfileFragment();
//    private SettingsFragment settingsFragment = new SettingsFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new Homefragment()).commit();

        // панель навигации
        binding.bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Fragment selectedFragment = null; // пока выбранный fragment = null
                switch (i) {
                    case R.id.navigationUser:
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.navigationHome:
                        selectedFragment = new Homefragment();
                        break;
                    case R.id.navigationSettings:
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
}