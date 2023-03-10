package com.example.pumpwimo.models;

public class User { // класс нашего пользователя

    private String email, password, nickName,phone;

    public User() {}
    public User(String email, String password, String phone, String nickName) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickName() {
        return nickName;
    }

}
