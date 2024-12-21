package com.rbmjltd.uddoktapay;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private final List<DataItem> dates;
    private final OnDateSelectedListener onDateSelectedListener;
    private int selectedPosition = -1; // Track the selected date

    public DataAdapter(List<DataItem> dates, OnDateSelectedListener onDateSelectedListener) {
        this.dates = dates;
        this.onDateSelectedListener = onDateSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dateitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dateitem = dates.get(position);
        holder.dayButton.setText(dateitem.date);
        holder.dateButton.setText(dateitem.day);

        // Highlight selected item
        if (position == selectedPosition) {
            holder.dayButton.setBackgroundColor(Color.parseColor("#FFA500"));
            holder.dateButton.setBackgroundColor(Color.parseColor("#FFA500"));
        }

        holder.dayButton.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            if (previousPosition != -1) {
                notifyItemChanged(previousPosition); // Reset previous selection
            }
            notifyItemChanged(selectedPosition); // Highlight new selection

            onDateSelectedListener.onDateSelected(dateitem); // Notify the listener of the new selection
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button dayButton;
        final Button dateButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dayButton = itemView.findViewById(R.id.day);
            dateButton = itemView.findViewById(R.id.date);
        }
    }

    public interface OnDateSelectedListener {
        void onDateSelected(DataItem selectedDate);
    }
}
