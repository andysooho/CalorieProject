package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    List<User> userList;
    Context context;

    public UserAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int mPosition = holder.getAdapterPosition();

        holder.foodname.setText(userList.get(position).foodname);
        holder.calorie.setText(userList.get(position).calorie);
        holder.date.setText(userList.get(position).date);
        holder.foodwhen.setText(userList.get(position).foodwhen);


        //수정화면으로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("uId",userList.get(mPosition).uid);
                intent.putExtra("upName",userList.get(mPosition).foodname);
                intent.putExtra("upCalorie",userList.get(mPosition).calorie);
                intent.putExtra("upDate",userList.get(mPosition).date);
                //imageUri넘기기
                if(userList.get(mPosition).imageUri != null){
                    intent.putExtra("upImageUri",userList.get(mPosition).imageUri);
                }
                //intent.putExtra("upImageUri",userList.get(mPosition).imageUri);
                context.startActivity(intent);
            }
        });
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /*
    //리스트저장
    public void setItemList(List<User> userList) {
        this.userList = userList;
    }

     */

    //사용자삭제
    public void deleteUser(int position) {
        this.userList.remove(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodname, calorie, date, foodwhen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.item_foodname);
            calorie = itemView.findViewById(R.id.item_calorie);
            date = itemView.findViewById(R.id.item_date);
            foodwhen = itemView.findViewById(R.id.item_when);
        }
    }




}
