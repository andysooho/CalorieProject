package com.example.myproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "foodname")
    public String foodname;

    @ColumnInfo(name = "calorie")
    public String calorie;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "foodeval")
    public String foodeval;





}
