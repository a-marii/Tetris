package com.example.tetris.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tetris.user.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE login = :login")
    User getByLogin(String login);

    @Insert
    void insert(User user);

    @Update
    void update(User user);


}