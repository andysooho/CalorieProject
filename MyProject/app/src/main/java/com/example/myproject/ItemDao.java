package com.example.myproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item")
    List<Item> getAllitem();

    @Insert
    void insertitem(Item item);

    @Delete
    void deleteitem(Item item);

    @Update
    void updateitem(Item item);

}