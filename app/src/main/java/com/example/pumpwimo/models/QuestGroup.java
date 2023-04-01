package com.example.pumpwimo.models;

import android.graphics.drawable.Drawable;

public class QuestGroup {
    public String title; // название
    public String description; // описание
    public int cardFon; // фон
    public int icon; // иконка

    public QuestGroup(String title, String description, int cardFon, int icon) {
        this.title = title;
        this.description = description;
        this.cardFon = cardFon;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCardFon() {
        return cardFon;
    }

    public int getIcon() {
        return icon;
    }
}
