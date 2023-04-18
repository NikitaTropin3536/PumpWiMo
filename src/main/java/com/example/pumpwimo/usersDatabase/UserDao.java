package com.example.pumpwimo.usersDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from Users")
    List<User> getAll(); // получение всех пользователей

    @Insert
     void save(User user); // сохранение пользователя

    @Update
    void update(User user); // обновление юзера

    @Delete
    void delete(User user); // удаление узера

    @Query(value = "SELECT * FROM Users WHERE Phone = :s")
    User search(String s); // поиск по номеру телефона
}