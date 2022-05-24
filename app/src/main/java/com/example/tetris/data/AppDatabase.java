package com.example.tetris.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tetris.user.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
