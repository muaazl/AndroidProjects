package com.example.mysuqerheroapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeroAdapter extends RecyclerView .Adapter<HeroViewHolder> {

    Context context;
    List<Hero> herosList = new ArrayList<>();

    public HeroAdapter(Context context, List<Hero> heros) {
        this.context = context;
        this.herosList = heros;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.heroview,parent,false);
        HeroViewHolder myViewHolder = new HeroViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Hero hero = herosList.get(position);
        holder.name.setText(hero.getName());
        holder.universe.setText(hero.getUniverse());
        holder.pic.setImageResource(hero.getImage());

        holder.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(hero.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.herosList.size();
    }
}
