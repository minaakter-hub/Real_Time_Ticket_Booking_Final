package com.rbmjltd.uddoktapay;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    private final List<TimeItem> times;
    private final OnTimeSelectedListener onTimeSelectedListener;
    private int selectedPosition = -1; // Track the selected item

    public TimeAdapter(List<TimeItem> times, OnTimeSelectedListener onTimeSelectedListener) {
        this.times = times;
        this.onTimeSelectedListener = onTimeSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeItem timeItem = times.get(position);
        holder.dayButton.setText(timeItem.time);

        // Highlight selected item
        if (position == selectedPosition) {
            holder.dayButton.setBackgroundColor(Color.parseColor("#FFA500")); // Orange for selected
        }

        holder.dayButton.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            if (previousPosition != -1) {
                notifyItemChanged(previousPosition); // Reset previous selection
            }
            notifyItemChanged(selectedPosition); // Highlight new selection

            onTimeSelectedListener.onTimeSelected(timeItem); // Notify the listener of the new selection
        });
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button dayButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dayButton = itemView.findViewById(R.id.time);
        }
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(TimeItem selectedTime); // Notify only the selected time
    }
}
