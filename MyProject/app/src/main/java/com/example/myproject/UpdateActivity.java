package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText upNameEdit, upCalorieEdit;

    int uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        upNameEdit = findViewById(R.id.up_name_edit);
        upCalorieEdit = findViewById(R.id.up_calorie_edit);
        Button upBtn = findViewById(R.id.update_btn);

        //아이템 어뎁터에서 넘어온 데이터 변수에 담기
        String upName = getIntent().getStringExtra("upName");
        String upCalorie = getIntent().getStringExtra("upCalorie");
        uId = getIntent().getIntExtra("uId", 0);

        //변수 값으로 에디트 텍스트에 값 넣기
        upNameEdit.setText(upName);
        upCalorieEdit.setText(upCalorie);

        //수정 버튼
        upBtn.setOnClickListener(v -> {
            //수정할 데이터 변수에 담기
            String upName2 = upNameEdit.getText().toString();
            String upCalorie2 = upCalorieEdit.getText().toString();

            //사용자클래스 생성
            User user = new User();
            user.uid = uId;
            user.foodname = upName2;
            user.calorie = upCalorie2;

            //데이터베이스에 수정할 데이터 넣기
            AppDatabase db = AppDatabase.getDBInstance(UpdateActivity.this);
            db.userDao().updateUser(user);

            //메인 액티비티로 이동
            Intent intent = new Intent(this, MealList.class);
            startActivity(intent);

            finish();
        });

    }







}