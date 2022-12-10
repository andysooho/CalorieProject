package com.example.myproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {User.class, FoodDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract FoodDBDao foodDBDao();

    public static AppDatabase INSTANCE;
    public static AppDatabase getDBInstance(Context context){
        //INSTANCE가 null일 경우에만 DB를 생성
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "itemdb").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
