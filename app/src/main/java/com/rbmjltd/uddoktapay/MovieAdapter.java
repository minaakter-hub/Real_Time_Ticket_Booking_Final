package com.rbmjltd.uddoktapay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Integer> movieList;
    private List<String> movieNames;
    private OnItemClickListener onItemClickListener; // Add listener

    public MovieAdapter(List<Integer> movieList, List<String> movieNames) {
        this.movieList = movieList;
        this.movieNames = movieNames;
    }

    public void setOnItemClickListener(OnItemClickListener listener) { // Setter for listener
        this.onItemClickListener = listener;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieName;

        public MovieViewHolder(View itemView, OnItemClickListener listener) { // Add listener to constructor
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView, onItemClickListener); // Pass listener to ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieImage.setImageResource(movieList.get(position));
        holder.movieName.setText(movieNames.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface OnItemClickListener { // Define listener interface
        void onItemClick(int position);
    }
}