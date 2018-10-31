package com.example.shanu.tutorialdemoapp.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoomInterface {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void addUser(User user);
}
