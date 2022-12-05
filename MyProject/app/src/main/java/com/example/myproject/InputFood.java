package com.example.myproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InputFood extends AppCompatActivity {
    ImageView imageView;
    Button btn_close, btn_save, google_map;
    EditText foodname, foodcount, calorie, date, time, foodeval;
    TimePicker timePicker;


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

                if (foodname1.equals("") || foodcount1.equals("") || calorie1.equals("") || foodeval1.equals("")) {
                    Toast.makeText(InputFood.this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    insertItem(foodname1, foodcount1, calorie1, foodeval1, time1);
                }
            }
        });

        google_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(InputFood.this, MapsActivity2.class);
                startActivity(intent);
            }
        });


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
    public void insertItem(String foodname1, String foodcount1, String calorie1, String foodeval1, String time1) {
        Item item = new Item();
        item.foodname = foodname1;
        item.foodcount = foodcount1;
        item.calorie = calorie1;
        item.foodeval = foodeval1;
        item.time = time1;

        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        db.itemDao().insertitem(item);

        setResult(Activity.RESULT_OK);

        finish();
    }

    public void getImageFromGalary(View v){
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



}

