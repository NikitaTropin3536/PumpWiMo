package com.example.pumpwimo.models;

public class Quest {

    private String quest_name;
    private int quest_rating;
    private int quest_image;

    // Constructor
    public Quest(String quest_name, int quest_rating, int quest_image) {
        this.quest_name = quest_name;
        this.quest_rating = quest_rating;
        this.quest_image = quest_image;
    }

    public String getQuest_name() {
        return quest_name;
    }

    public int getQuest_rating() {
        return quest_rating;
    }

    public int getQuest_image() {
        return quest_image;
    }

    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public void setQuest_rating(int quest_rating) {
        this.quest_rating = quest_rating;
    }

    public void setQuest_image(int quest_image) {
        this.quest_image = quest_image;
    }
}
