package com.example.shanu.tutorialdemoapp.RoomDatabase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomInterface {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void addUser(User user);
}
