package com.example.abdo.cinemaapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abdo.cinemaapp.MovieActivity;
import com.example.abdo.cinemaapp.R;
import com.example.abdo.cinemaapp.Model.Trend;
import com.example.abdo.cinemaapp.TvShowActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrendAdapter extends  RecyclerView.Adapter<TrendAdapter.MyViewHolder> {

    ImageView img;
    ArrayList<Trend>arrayList;
    private Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movietrendlayout, viewGroup, false);
        context = viewGroup.getContext();
        return new MyViewHolder(itemView);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.MovieTrendImage);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Picasso.with(context).load(arrayList.get(i).getImage()).into(img);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trend s = arrayList.get(i);
                if (s.getType().equals("movie")) {
                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra("id", s.getId());
                    context.startActivity(intent);
                }
                else if (s.getType().equals("show"))
                {
                    Intent intent = new Intent(context, TvShowActivity.class);
                    intent.putExtra("id", s.getId());
                    context.startActivity(intent);
                }
            }
        });

    }
    public TrendAdapter(ArrayList<Trend> a) {
        arrayList = a;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
