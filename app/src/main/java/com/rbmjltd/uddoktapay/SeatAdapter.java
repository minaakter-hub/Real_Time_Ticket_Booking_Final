package com.rbmjltd.uddoktapay;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private final List<SeatItem> seats;
    private final OnSeatSelectedListener onSeatSelectedListener;

    public SeatAdapter(List<SeatItem> seats, OnSeatSelectedListener onSeatSelectedListener) {
        this.seats = seats;
        this.onSeatSelectedListener = onSeatSelectedListener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seatitem, parent, false);
        return new SeatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        SeatItem seat = seats.get(position);
        holder.seatTextView.setText(seat.seatNumber);

        // Set background color based on seat state
        if (!seat.isAvailable) {
            holder.seatTextView.setBackgroundColor(Color.parseColor("#bebebe")); // Gray for unavailable
        } else if (seat.isSelected) {
            holder.seatTextView.setBackgroundColor(Color.parseColor("#FFA500")); // Orange for selected
        }
        // Handle click events for selecting/deselecting seats
        holder.itemView.setOnClickListener(v -> {
            if (seat.isAvailable) {
                seat.isSelected = !seat.isSelected; // Toggle selection state
                notifyItemChanged(position); // Refresh item appearance
                onSeatSelectedListener.onSeatSelected(seat, position); // Notify listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        final TextView seatTextView;

        public SeatViewHolder(View itemView) {
            super(itemView);
            seatTextView = itemView.findViewById(R.id.seatTextView);
        }
    }

    public interface OnSeatSelectedListener {
        void onSeatSelected(SeatItem seat, int position); // Notify when a seat is selected/deselected
    }
}
