package com.example.pumpwimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;

import com.example.pumpwimo.databinding.ActivityMainBinding;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}