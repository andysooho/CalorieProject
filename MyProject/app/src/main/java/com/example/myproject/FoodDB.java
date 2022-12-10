package com.example.myproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FoodDB {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "foodNo")
    public String foodNo;

    @ColumnInfo(name = "foodname")
    public String foodname;

    @ColumnInfo(name = "calorie")
    public String calorie;
}
