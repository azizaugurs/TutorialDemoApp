package com.example.shanu.tutorialdemoapp.RoomDatabase;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class} ,version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RoomInterface roomInterfaceDao();


}
