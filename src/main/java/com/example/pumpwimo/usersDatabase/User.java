package com.example.pumpwimo.usersDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Users")
public class User {
    // класс нашего пользователя

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "userName")
    @NotNull
    public String userName;

    @ColumnInfo(name = "userPhone")
    @NotNull
    public String userPhone;

    @ColumnInfo(name = "userMail")
    @NotNull
    public String userMail;

    @ColumnInfo(name = "userPassword")
    @NotNull
    public String userPassword;

    @ColumnInfo(name = "userPicUri")
    @NotNull
    public String userPicUri;

    public User(int id,
                @NotNull String userName,
                @NotNull String userPhone,
                @NotNull String userMail,
                @NotNull String userPassword,
                @NotNull String userPicUri) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userPicUri = userPicUri;
    }

//    public User(@NotNull String userName,
//                @NotNull String userPhone,
//                @NotNull String userMail,
//                @NotNull String userPassword,
//                @NotNull String userPicUri) {
//        this.userName = userName;
//        this.userPhone = userPhone;
//        this.userMail = userMail;
//        this.userPassword = userPassword;
//        this.userPicUri = userPicUri;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(@NotNull String userName) {
        this.userName = userName;
    }

    public void setUserPhone(@NotNull String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserMail(@NotNull String userMail) {
        this.userMail = userMail;
    }

    public void setUserPassword(@NotNull String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPicUri(@NotNull String userPicUri) {
        this.userPicUri = userPicUri;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getUserName() {
        return userName;
    }

    @NotNull
    public String getUserPhone() {
        return userPhone;
    }

    @NotNull
    public String getUserMail() {
        return userMail;
    }

    @NotNull
    public String getUserPassword() {
        return userPassword;
    }

    @NotNull
    public String getUserPicUri() {
        return userPicUri;
    }
}