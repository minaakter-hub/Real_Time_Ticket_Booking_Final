package com.rbmjltd.uddoktapay;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class upcomadapter extends RecyclerView.Adapter<upcomadapter.MovieViewHolder> {

    private List<Integer> movieList;
    private List<String> movieNames;
    public upcomadapter(List<Integer> movieList, List<String> movieNames) {
        this.movieList = movieList;
        this.movieNames = movieNames;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieName;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcom_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieImage.setImageResource(movieList.get(position));
        holder.movieName.setText(movieNames.get(position)); // Set the movie name
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }
}