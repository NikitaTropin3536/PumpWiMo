package com.example.pumpwimo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pumpwimo.R;
import com.example.pumpwimo.activities.BoardActivity;
import com.example.pumpwimo.activities.QuestActivity;
import com.example.pumpwimo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

//        binding.fram1.setOnClickListener(v -> {
//            // переход на квесты
//            Intent intent = new Intent(getActivity(), QuestActivity.class);
//            startActivity(intent);
//        });
//
//        binding.fram2.setOnClickListener(v1 -> {
//            // переход на рейтинг
//
//        });
//
//        binding.fram3.setOnClickListener(v2 -> {
//            // к друзьям
//
//        });
//
//        binding.fram4.setOnClickListener(v3 -> {
//            // награды
//
//        });

    }



}
