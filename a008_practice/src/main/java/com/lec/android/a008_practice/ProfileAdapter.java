package com.lec.android.a008_practice;

import android.os.ParcelFormatException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{

    List<Profile> itemList = new ArrayList<Profile>(); // 데이터 담는 리스트

    static ProfileAdapter adapter;
    // Adapter 생성자
    public ProfileAdapter(){this.adapter = this;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View itemView = inf.inflate(R.layout.item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile pf = itemList.get(position);
        holder.setProfile(pf);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }





    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvAddress, tvAge;
        ImageButton btnDelItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAdress);
            tvAge = itemView.findViewById(R.id.tvAge);

            btnDelItem = itemView.findViewById(R.id.btnDelItem);
            btnDelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removeItem(getAdapterPosition()); // 데이터 삭제
                    adapter.notifyDataSetChanged(); // 시각적으로 사라지기
                }
            });

        } // end 생성자


        public void setProfile(Profile pf){
            tvName.setText(pf.getName());
            tvAddress.setText(pf.getAddress());
            tvAge.setText("" + pf.getAge());
        } // end setItem()
    } // end ViewHolder


 // 리스트에 데이터 추가 수정 저장
    public void addItem(Profile pf){
        itemList.add(pf);
    }

    public void addItem(int position, Profile pf){
        itemList.add(position, pf);
    }
    public void setItem (ArrayList<Profile> pfList){
        this.itemList = pfList;
    }

    public Profile getItem(int position){
        return itemList.get(position);
    }

    public void setItem(int position, Profile pf){
        itemList.set(position, pf);
    }
    public void removeItem(int position){
        itemList.remove(position);
    }


} // end ProfileAdapter
