package com.example.myproject;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {

    EditText upNameEdit, upCalorieEdit;
    TextView upDateText;
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

        String upImageUri = getIntent().getStringExtra("upImageUri");

        if(upImageUri != null){
            Uri imageUri = Uri.parse(upImageUri);
            //imageView.setImageURI(uri);
            try {
                InputStream in = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        //변수 값으로 에디트 텍스트에 값 넣기
        upNameEdit.setText(upName);
        upCalorieEdit.setText(upCalorie);
        upDateText.setText(upDate);

        //수정 버튼
        closeBtn.setOnClickListener(v -> {
            finish();
        });
    }
}