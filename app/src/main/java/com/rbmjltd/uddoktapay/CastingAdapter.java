package com.rbmjltd.uddoktapay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class CastingAdapter extends RecyclerView.Adapter<CastingAdapter.ViewHolder> {

    private List<CastingItem> castingItems;

    public CastingAdapter(List<CastingItem> castingItems) {
        this.castingItems = castingItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_casting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CastingItem castingItem = castingItems.get(position);
        holder.actorImage.setImageResource(castingItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return castingItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView actorImage;

        public ViewHolder(View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.actor_image);
        }
    }
}