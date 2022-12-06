package com.example.myproject;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MealList extends AppCompatActivity {

    UserAdapter adapter;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_list);

        //RecyclerView 초기화 및 설정
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //useradapter 생성
        adapter = new UserAdapter(MealList.this);

        //recyclerview에 adapter 설정
        recyclerView.setAdapter(adapter);

        //사용자 조회
        loadUserList();

        //삭제 제스처
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                switch(direction){
                    case ItemTouchHelper.LEFT:
                        //사용자 클래스 생성
                        User user = new User();
                        user.uid = userList.get(position).uid;
                        //아이템 삭제
                        adapter.deleteUser(position);
                        //아이템 삭제 화면 재정리
                        adapter.notifyItemRemoved(position);
                        //DB생성
                        AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
                        //삭제 쿼리
                        db.userDao().deleteUser(user);
                        break;
                }

            }

            //제스처 그림 구현

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.RED) //왼쪽 스와프 배경 색상
                        .addSwipeLeftActionIcon(R.drawable.ic_delete) //왼쪽 스와프 아이콘
                        .addSwipeLeftLabel("삭제") //왼쪽 스와프 텍스트 설정
                        .setSwipeLeftLabelColor(Color.WHITE) //왼쪽 스와프 텍스트 색상
                        .create() // 생성
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView); //RecyclerView에 적용

    }

    /*
    //액티비티가 화면에 나타날 때 호출됨
    @Override
    protected void onResume() {
        super.onResume();
        //사용자 조회
        loadUserList();
    }

     */


    //액티비티 백그라운드에 있는데 호출되면 실행
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadUserList();
    }


    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //사용자조회
                    if (result.getResultCode() == RESULT_OK) {
                        loadUserList();
                    }
                }
            }
    );

    private void loadUserList() {
        //사용자 목록을 조회해서 리스트뷰에 출력
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        userList = db.userDao().getAllUser();

        if (userList.size() > 0) { //리스트뷰에 출력
            int position = userList.size() - 1;
            Toast.makeText(this, "최근 추가된 음식: " + userList.get(position).foodname, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "음식이 없습니다.", Toast.LENGTH_SHORT).show();
        }

        adapter.setItemList(userList);
    }
}