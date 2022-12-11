package com.example.myproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputFood extends AppCompatActivity {
    ImageView imageView;
    Button btn_close, btn_save, google_map, btn_date;
    EditText foodname, foodcount, calorie, foodeval;
    TimePicker timePicker;
    DatePickerDialog datePickerDialog;

    Uri uri;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_food);

        imageView = findViewById(R.id.imageView);
        btn_close = findViewById(R.id.btn_close);
        btn_save = findViewById(R.id.btn_save);
        foodname = findViewById(R.id.foodName);
        foodcount = findViewById(R.id.foodCount);
        calorie = findViewById(R.id.calorie);
        foodeval = findViewById(R.id.foodEval);
        timePicker = findViewById(R.id.timePicker);
        google_map = findViewById(R.id.google_map);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodname1 = foodname.getText().toString();
                String foodcount1 = foodcount.getText().toString();
                String calorie1 = calorie.getText().toString();
                String foodeval1 = foodeval.getText().toString();
                String time1 = timePicker.getHour() + ":" + timePicker.getMinute();
                String date1 = btn_date.getText().toString();

                //make date1 format 2022년 12월 8일 to yyyy-MM-dd using SimpleDateFormat
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                Date date = null;
                try {
                    date = inputFormat.parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                String outputDate = outputFormat.format(date);

                if (foodname1.equals("") || calorie1.equals("")) {
                    Toast.makeText(InputFood.this, "필수 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    //Hint 도 글자로 인식하는듯
                } else {
                    insertUser(foodname1, foodcount1, calorie1, foodeval1, time1, outputDate);
                }
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        google_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputFood.this, MapsActivity2.class);
                startActivity(intent);
            }
        });

        btn_date = findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 날짜를 가져온다.
                Calendar calendar = Calendar.getInstance();
                int pyear = calendar.get(Calendar.YEAR);
                int pmonth = calendar.get(Calendar.MONTH);
                int pday = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(InputFood.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = year + "년 " + month + "월 " + day + "일";

                                //set btn_date text to date
                                btn_date.setText(date);
                            }
                        }, pyear, pmonth, pday);
                datePickerDialog.show();
            }//onClick
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String date1 = dateFormat.format(calendar.getTime());
        btn_date.setText(date1);

        //칼로리검색 리스트뷰
        listView = findViewById(R.id.listView);
        Button btn_search = findViewById(R.id.btn_search);

        AppDatabase foodDB = AppDatabase.getDBInstance(this);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchfood = "%" + foodname.getText().toString() + "%" ;
                //처음에는 List<FoodDB> 로 간단하게 했지만 리스트뷰 중복출력문제가 있어서 수정
                //List<FoodDB> foodList = foodDB.foodDBDao().searchFoodByName(searchfood);
                Set<FoodDB> foodset = new HashSet<>();
                foodset.addAll(foodDB.foodDBDao().searchFoodByName(searchfood));
                //set을 리스트로 변환
                List<FoodDB> foodList = new ArrayList<>(foodset);

                FoodListAdapter adapter = new FoodListAdapter(InputFood.this, foodList);

                listView.setAdapter(adapter);
            }
        });

        //칼로리검색 리스트뷰 클릭시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FoodDB foodDB = (FoodDB) adapterView.getItemAtPosition(position);

                foodname.setText(foodDB.foodname);
                calorie.setText(foodDB.calorie);
                foodcount.setText("1");
            }
        });

    }

    public class FoodListAdapter extends ArrayAdapter<FoodDB> {

        // Constructor
        public FoodListAdapter(Context context, List<FoodDB> foods) {
            super(context, 0, foods);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            FoodDB food = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_food_calorie, parent, false);
            }

            // Lookup view for data population
            TextView foodName = (TextView) convertView.findViewById(R.id.listview_foodname);
            TextView foodCalories = (TextView) convertView.findViewById(R.id.listView_calorie);

            // Populate the data into the template view using the data object
            foodName.setText(food.foodname);
            foodCalories.setText(food.calorie);

            // Return the completed view to render on screen
            return convertView;
        }
    }


    public void insertUser(String foodname1, String foodcount1, String calorie1, String foodeval1, String time1, String date1) {
        User user = new User();
        user.foodname = foodname1;
        user.foodcount = foodcount1;
        user.calorie = calorie1;
        user.foodeval = foodeval1;
        user.time = time1;
        user.date = date1;
        if (uri != null) {
            user.imageUri = uri.toString();
        }

        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        db.userDao().insertUser(user);

        setResult(Activity.RESULT_OK);

        finish();
    }


    public void getImageFromGalary(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    imageView.setImageURI(data.getData());
                    uri = data.getData();
                    //make imageview2 invisible
                    //ImageView imageView2 = findViewById(R.id.imageView2);
                    //imageView2.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

