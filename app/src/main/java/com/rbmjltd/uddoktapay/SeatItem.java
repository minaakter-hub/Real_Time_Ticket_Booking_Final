package com.rbmjltd.uddoktapay;

public class SeatItem {
    public String seatNumber;
    public boolean isAvailable;
    public boolean isSelected;

    public SeatItem(String seatNumber, boolean isAvailable) {
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
        this.isSelected = false; // Initially not selected
    }
}