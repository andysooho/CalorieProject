package com.example.myproject;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    Button btn_inputfood, btn_meallist, btn_analysis;
    private ImageView Gif;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);

        Gif = (ImageView) findViewById(R.id.gif_image);
        Glide.with(this).load(R.drawable.giphy).into(Gif);

        String str = "나의 뱃살 일지";
        TextView tv = (TextView)findViewById(R.id.titl);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#8258FA")), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);

        btn_inputfood = findViewById(R.id.btn_inputfood);
        btn_meallist = findViewById(R.id.btn_meallist);
        btn_analysis = findViewById(R.id.btn_analysis);

        btn_inputfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputFood.class);
                startActivity(intent);

            }
        });

        btn_meallist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, MealList.class);
                startActivity(intent2);
            }
        });

        btn_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, AnalysisFood.class);
                startActivity(intent3);
            }
        });

        // Request READ_EXTERNAL_STORAGE permission
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

        //음식 DB 처리
        AppDatabase FoodDataBase = AppDatabase.getDBInstance(this);

        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("FoodDB.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            FoodDB foodinput = new FoodDB();
            while ((line = reader.readLine()) != null) {
                //Log.d("FoodDB", line);
                String[] token = line.split("\t");

                foodinput.foodNo = token[0];
                foodinput.foodname = token[1];
                foodinput.calorie = Integer.parseInt(token[2]);
                //Log.d("file_test", Arrays.toString(tokens));
                FoodDataBase.foodDBDao().insertFood(foodinput);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}