package com.example.pumpwimo.adapters;

import static android.content.res.Configuration.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.example.pumpwimo.R;
import com.example.pumpwimo.activities.BoardActivity;
import com.example.pumpwimo.activities.QuestGroupActivity;
import com.example.pumpwimo.activities.QuestsActivity;

public class QuestGroupViewPagerAdapter extends PagerAdapter {

    private Context context;

    // todo humans
    private int[] humansPortraint = {
            R.drawable.jack_ma,
            R.drawable.ilon_mask,
            R.drawable.marissa,
            R.drawable.bill_gates,
            R.drawable.jeff_bezos,
            R.drawable.steve_jobs_2
    };

    // todo humans
    private int[] humansLandscape = {
            R.drawable.jack_ma_2,
            R.drawable.ilon_mask_2,
            R.drawable.marissa_2,
            R.drawable.bill_gates_2,
            R.drawable.jeff_bezos_2,
            R.drawable.steve_jobs_3
    };

    // todo заголовки
    private int[] titles = {
            R.string.quest_group_title_1,
            R.string.quest_group_title_2,
            R.string.quest_group_title_3,
            R.string.quest_group_title_4,
            R.string.quest_group_title_5,
            R.string.quest_group_title_6
    };

    // todo описания
    private int[] descriptions = {
            R.string.quest_group_description_1,
            R.string.quest_group_description_2,
            R.string.quest_group_description_3,
            R.string.quest_group_description_4,
            R.string.quest_group_description_5,
            R.string.quest_group_description_6
    };

    // todo иконки квестов
    private int[] iconsQuests = {
            R.drawable.icon_quest_group_1,
            R.drawable.icon_quest_group_2,
            R.drawable.icon_quest_group_3,
            R.drawable.icon_quest_group_4,
            R.drawable.icon_quest_group_5,
            R.drawable.icon_quest_group_6
    };

    // todo shape - ы для кнопок
    private int[] butBeginShapes = {
            R.drawable.item_quest_group_shape_1,
            R.drawable.item_quest_group_shape_2,
            R.drawable.item_quest_group_shape_3,
            R.drawable.item_quest_group_shape_4,
            R.drawable.item_quest_group_shape_5,
            R.drawable.item_quest_group_shape_6
    };

    // todo текст на кнопке
    private int[] butBeginTexts = {
            R.string.but_begin_text_1,
            R.string.but_begin_text_2,
            R.string.but_begin_text_3,
            R.string.but_begin_text_4,
            R.string.but_begin_text_5,
            R.string.but_begin_text_6
    };

    // todo полученные пользователем деньги
    protected int[] receivedMoney = {
            1, 2, 3, 4, 5, 6
    };

    // todo выполненные квесты
    protected int[] completedQuest = {
            1, 2, 3, 4, 5, 6
    };

    public QuestGroupViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return humansPortraint.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.quest_group_slider, container, false);

        // todo объявление и инициализация view slider_layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView human = view.findViewById(R.id.human); // todo предприниматель
        TextView title = view.findViewById(R.id.title); // todo заголовок
        TextView description = view.findViewById(R.id.description); // todo описание

        ImageView money = view.findViewById(R.id.money); // todo всего денег
        TextView count_money = view.findViewById(R.id.count_money); // todo полученные деньги

        ImageView icon_quests = view.findViewById(R.id.icon_quests); // todo иконки квесты
        TextView count_quests = view.findViewById(R.id.count_quests); // todo выыполненные квесты

        Button begin = view.findViewById(R.id.but_begin); // todo кнопка начать

        if (onPosition().orientation == ORIENTATION_PORTRAIT) {
            human.setImageResource(humansPortraint[position]);
        } else if (onPosition().orientation == ORIENTATION_LANDSCAPE) {
            human.setImageResource(humansLandscape[position]);
        }

        title.setText(titles[position]);
        description.setText(descriptions[position]);

        money.setImageResource(R.drawable.money);
        count_money.setText(String.valueOf(receivedMoney[position]));

        icon_quests.setImageResource(iconsQuests[position]);
        count_quests.setText(String.valueOf(completedQuest[position]));

        // todo ставим кнопочке shape
        begin.setBackgroundResource(butBeginShapes[position]);

        // todo ставим текст на кнопочку
        begin.setText(butBeginTexts[position]);

        if (position == 5) { // todo если мы на 5 - ом экране - меняем цвет текста на кнопке
            begin.setTextColor(R.color.colorBlack);
        }

        begin.setOnClickListener(v -> {
            openQuestActivity(context);

        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }

    private Configuration onPosition() {
        Context context = this.context.getApplicationContext();
        Configuration configuration = context.getResources().getConfiguration();
        return configuration;
    }

    private void openQuestActivity(Context context) {
        context.startActivity(new Intent(context, QuestsActivity.class));
    }
}