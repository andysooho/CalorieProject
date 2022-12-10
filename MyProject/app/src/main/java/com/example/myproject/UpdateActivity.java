package com.example.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText upNameEdit, upCalorieEdit;
    TextView upDateText;
    //Bitmap upImage;
    ImageView imageView;


    int uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        upNameEdit = findViewById(R.id.up_name_edit);
        upCalorieEdit = findViewById(R.id.up_calorie_edit);
        upDateText = findViewById(R.id.up_date_text);
        imageView = findViewById(R.id.up_image);
        Button closeBtn = findViewById(R.id.btn_updataclose);

        //아이템 어뎁터에서 넘어온 데이터 변수에 담기
        String upName = getIntent().getStringExtra("upName");
        String upCalorie = getIntent().getStringExtra("upCalorie");
        String upDate = getIntent().getStringExtra("upDate");
        uId = getIntent().getIntExtra("uId", 0);
        //byte[] bytes = getIntent().getByteArrayExtra("upImage");
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        //변수 값으로 에디트 텍스트에 값 넣기
        upNameEdit.setText(upName);
        upCalorieEdit.setText(upCalorie);
        upDateText.setText(upDate);
        //imageView.setImageBitmap(bitmap);


        //수정 버튼
        closeBtn.setOnClickListener(v -> {
            finish();
        });
    }

}


/*
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

             */
