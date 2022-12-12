package com.example.myproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalysisFood extends AppCompatActivity {
    TextView breakfastCalorie, lunchCalorie, dinnerCalorie, totalCalorie;
    CalendarView calendarView;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_food);

        breakfastCalorie = findViewById(R.id.breakfast_calculated);

        //캘린더뷰
        calendarView = findViewById(R.id.calendarView3);
        //캘린더뷰 날짜 선택 리스너
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date tmpdate = sdf.parse(year + "-" + (month + 1) + "-" + dayOfMonth, new java.text.ParsePosition(0));
                date = sdf.format(tmpdate);
                Toast.makeText(AnalysisFood.this, date, Toast.LENGTH_SHORT).show();

                AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
                int breakfastcal = db.userDao().getTotalCaloriesForBreakfastOnDate(date);

                //change breakfastcal to string

                breakfastCalorie.setText(Integer.toString(breakfastcal));
            }
        });




    }
}
