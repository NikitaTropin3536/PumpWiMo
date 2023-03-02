package com.example.pumpwimo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
        R.drawable.cup,
        R.drawable.logo_line,
        R.drawable.logo_ring
    };

    public String[] slide_headings = {
        "EAT",
        "SLEEP",
        "CODE"
    };


    public String[] slide_descs = {
            "fflflflflflflflflflflflflflflflflflfl",
            "alssssssssssssss",
            "slllllllllllllllllllll"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.);
        TextView slideHeading = (TextView) view.findViewById();
         TextView slideDescription = (TextView) view.findViewById();

    }
}
