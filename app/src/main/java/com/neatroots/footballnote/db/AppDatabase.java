package com.neatroots.footballnote.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDetailDao userDetailDao();

    public static AppDatabase INSTANCE;

    public static AppDatabase getDBInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "userList")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}


