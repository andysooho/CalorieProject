package com.example.myproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Converter {
    @TypeConverter
    public static Bitmap fromByteArray(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @TypeConverter
    public static byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        return stream.toByteArray();
    }
}
