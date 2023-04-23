package com.example.pumpwimo.models;

import android.graphics.drawable.Drawable;

public class QuestGroupCover {
    private int number; // todo номер группы квестов
    private int human; // todo предприниматель, символизирующий опр волевое качество человека
    public String title; // todo название группы квестов
    public String description; // todo описание
    public int chet_money = 100; // todo сколько монеток всего
    public int icon_quests; // todo иконка квестов
    private int count_quests = 18; // todo сколько квестов всего
    private Drawable butBeginShape; // todo shape для кнопки "начать"

    public QuestGroupCover(int human, String title, String description, int icon_quests, Drawable butBeginShape) {
        this.human = human;
        this.title = title;
        this.description = description;
        this.chet_money = 100;
        this.icon_quests = icon_quests;
        this.count_quests = 18;
        this.butBeginShape = butBeginShape;
    }
}