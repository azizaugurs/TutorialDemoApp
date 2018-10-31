package com.example.shanu.tutorialdemoapp.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class} ,version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RoomInterface roomInterfaceDao();


}
