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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AnalysisFood extends AppCompatActivity {
    TextView breakfastCalorie, lunchCalorie, dinnerCalorie, totalCalorie, totalcalorie_date;
    CalendarView calendarView;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_food);

        breakfastCalorie = findViewById(R.id.breakfast_calculated);
        lunchCalorie = findViewById(R.id.lunch_calculated);
        dinnerCalorie = findViewById(R.id.dinner_calculated);
        totalCalorie = findViewById(R.id.total_calculated);
        totalcalorie_date = findViewById(R.id.totalcal);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        date = dateFormat.format(calendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String date_fortotal = sdf.format(currentDate);
        totalcalorie_date.setText(date+"\n총 칼로리");

        AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
        int totalcal = db.userDao().getTotalCaloriesForAllFoodOnDate(date_fortotal);
        totalCalorie.setText(totalcal+" kcal");


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


                int breakfastcal = db.userDao().getTotalCaloriesForBreakfastOnDate(date);
                int lunchcal = db.userDao().getTotalCaloriesForLunchOnDate(date);
                int dinnercal = db.userDao().getTotalCaloriesForDinnerOnDate(date);
                int totalcal = db.userDao().getTotalCaloriesForAllFoodOnDate(date);

                //change breakfastcal to string

                breakfastCalorie.setText(Integer.toString(breakfastcal));
                lunchCalorie.setText(Integer.toString(lunchcal));
                dinnerCalorie.setText(Integer.toString(dinnercal));
                totalCalorie.setText(Integer.toString(totalcal));
            }
        });




    }
}
