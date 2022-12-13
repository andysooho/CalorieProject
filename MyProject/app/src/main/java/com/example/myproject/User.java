package com.example.myproject;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User { // implements Serializable
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "foodname")
    public String foodname;

    @ColumnInfo(name = "foodcount")
    public String foodcount;

    @ColumnInfo(name = "calorie")
    public int calorie;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "foodeval")
    public String foodeval;

    @ColumnInfo(name = "imageUri")
    public String imageUri;

    @ColumnInfo(name = "foodwhen")
    public String foodwhen;

    @ColumnInfo(name = "location")
    public String location;
}
