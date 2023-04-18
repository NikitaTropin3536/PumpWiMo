package com.example.pumpwimo.usersDatabase;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Users")
public class User {
    // класс нашего пользователя

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "mail")
    @NotNull
    public String mail;

    @ColumnInfo(name = "password")
    @NotNull
    public String password;

    @ColumnInfo(name = "name")
    @NotNull
    public String name;

    @ColumnInfo(name = "phone")
    @NotNull
    public String phone;

    @ColumnInfo(name = "picUri")
    @NotNull
    public String picUri;

    public User(@NonNull String name,
                @NotNull String phone,
                @NotNull String mail,
                @NotNull String password,
                @NotNull String picUri) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.picUri = picUri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMail(@NotNull String mail) {
        this.mail = mail;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setPhone(@NotNull String phone) {
        this.phone = phone;
    }

    public void setPicUri(@NotNull String picUri) {
        this.picUri = picUri;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getMail() {
        return mail;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPhone() {
        return phone;
    }

    @NotNull
    public String getPicUri() {
        return picUri;
    }
}