package com.example.myproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "foodname")
    public String foodname;

    @ColumnInfo(name = "foodcount")
    public String foodcount;

    @ColumnInfo(name = "calorie")
    public String calorie;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "foodeval")
    public String foodeval;
}
