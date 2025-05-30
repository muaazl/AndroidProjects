package com.example.mysuqerheroapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeroViewHolder extends RecyclerView.ViewHolder {

    TextView name, universe;
    Button goButton;
    ImageView pic;

    public HeroViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.tv_name);
        universe = itemView.findViewById(R.id.tv_universe);
        goButton = itemView.findViewById(R.id.button);
        pic = itemView.findViewById(R.id.img_pic);
    }
}
