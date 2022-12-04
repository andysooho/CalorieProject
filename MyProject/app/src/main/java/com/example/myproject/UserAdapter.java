package com.example.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    List<User> userList;
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.foodname.setText(userList.get(position).foodname);
        holder.calorie.setText(userList.get(position).calorie);
        //holder.carbohydrate.setText(item.carbohydrate);
        //holder.protein.setText(item.protein);
        //holder.fat.setText(item.fat);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setItemList(List<User> userList) {
        this.userList = userList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodname;
        TextView calorie;
        TextView carbohydrate;
        TextView protein;
        TextView fat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.item_foodname);
            calorie = itemView.findViewById(R.id.item_calorie);
            //carbohydrate = itemView.findViewById(R.id.carbohydrate);
            //protein = itemView.findViewById(R.id.protein);
            //fat = itemView.findViewById(R.id.fat);
        }
    }
}
