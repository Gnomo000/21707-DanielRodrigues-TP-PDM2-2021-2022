package com.example.woods.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.woods.model.Orders;
import com.example.woods.model.User;
import com.example.woods.model.Woods;

@Database(entities = {Woods.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WoodsDao getWoodsDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "woodsDB").
                    allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
