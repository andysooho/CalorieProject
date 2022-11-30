package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MealList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_list);

        loaditemList();
    }

    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //사용자조회
                    if(result.getResultCode() == RESULT_OK){
                        loaditemList();
                    }
                }
            }
    );

    private void loaditemList() {
        //사용자 목록을 조회해서 리스트뷰에 출력
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        List<Item> itemList = db.itemDao().getAllitem();

        if(itemList.size() > 0){ //리스트뷰에 출력
            int position = itemList.size() - 1;
            Toast.makeText(this, "최근 추가된 음식: " + itemList.get(position).foodname, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "음식이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }


}
