package com.example.myproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDBDao {

    @Query("SELECT * FROM fooddb")
    List<FoodDB> getAllFood();

    @Query("SELECT * FROM fooddb WHERE foodname LIKE :search")
    List<FoodDB> searchFoodByName(String search);

    @Insert
    void insertFood(FoodDB foodDB);



    @Delete
    void deleteUser(FoodDB foodDB);

    @Update
    void updateUser(FoodDB foodDB);
}