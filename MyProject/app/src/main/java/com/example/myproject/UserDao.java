package com.example.myproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE date = :date")
    List<User> getUserByDate(String date);

    @Query("SELECT SUM(calorie) FROM user WHERE foodwhen = '아침' AND date = :date")
    int getTotalCaloriesForBreakfastOnDate(String date);

    @Query("SELECT SUM(calorie) FROM user WHERE foodwhen = '점심' AND date = :date")
    int getTotalCaloriesForLunchOnDate(String date);

    @Query("SELECT SUM(calorie) FROM user WHERE foodwhen = '저녁' AND date = :date")
    int getTotalCaloriesForDinnerOnDate(String date);


    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}