package com.rbmjltd.uddoktapay;
import android.content.ClipData;
import android.content.Context;
import android.graphics.text.TextRunShaper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myviewholder> {
    Context context;
    List<movieitem> items;

    public Myadapter(Context context, List<movieitem> items) {
        this.context = context;
        this.items = items;
    }
    public void setFilteredList(List<movieitem>filteredList)
    {
        this.items=filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.movie_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        movieitem currentItem = items.get(position);

        // Bind data to views
        holder.name.setText(currentItem.getTitle());
        holder.des.setText(currentItem.getDescription());
        holder.imageView.setImageResource(currentItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
