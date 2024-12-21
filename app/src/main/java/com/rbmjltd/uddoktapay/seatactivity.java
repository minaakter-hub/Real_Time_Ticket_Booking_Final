package com.rbmjltd.uddoktapay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class seatactivity extends AppCompatActivity {
private RecyclerView dateRecyclerView;
    private List<String> selectedSeatNumbers = new ArrayList<>();
    private List<SeatItem> seats; // Declare seats as a class member
    private TextView moneyTextView;
    private TextView selectedseatTextView;
    Button confrim1;
    private String selectedDate = ""; // To store selected date
    private String selectedTime = ""; // To store selected time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seatactivity);
        dateRecyclerView = findViewById(R.id.daterecylceview);
        RecyclerView timeRecyclerView = findViewById(R.id.timerecylceview);
        RecyclerView seatRecyclerView = findViewById(R.id.seatrecycleview);
        moneyTextView = findViewById(R.id.money);
      selectedseatTextView = findViewById(R.id.selectedseat);
      confrim1=findViewById(R.id.confrim);
      confrim1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(seatactivity.this,MainActivity.class);

              SharedPreferences sharedPreferences = getSharedPreferences("BookingData", MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.putString("date", selectedDate);
              editor.putString("time", selectedTime);
              editor.putString("amount", moneyTextView.getText().toString());
              editor.putString("seats", selectedseatTextView.getText().toString());
              editor.apply();
              startActivity(intent);

          }
      });
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Set horizontal layout
         timeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Set horizontal layout
// Create sample data
        List<DataItem> dates = new ArrayList<>();
        dates.add(new DataItem("Mon", "20 Dec"));
        dates.add(new DataItem("Tue", "21 Dec"));
        dates.add(new DataItem("Tue", "21 Dec"));dates.add(new DataItem("Tue", "21 Dec"));dates.add(new DataItem("Tue", "21 Dec"));
// ... add more dates

// Set adapter
        DataAdapter dateAdapter = new DataAdapter(dates, new DataAdapter.OnDateSelectedListener() {
            @Override
            public void onDateSelected(DataItem selectedDate1) {
                selectedDate = selectedDate1.day + ", " + selectedDate1.date;
            }
        });
        dateRecyclerView.setAdapter(dateAdapter);
        List<TimeItem> times = new ArrayList<>();
        times.add(new TimeItem("10:00 AM"));
        times.add(new TimeItem("12:30 PM"));
        times.add(new TimeItem("12:30 PM"));
        times.add(new TimeItem("12:30 PM"));
        times.add(new TimeItem("12:30 PM"));
// ... add more times

// Set adapter
        TimeAdapter timeAdapter = new TimeAdapter(times, new TimeAdapter.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(TimeItem selectedTime1) {
                selectedTime = selectedTime1.time;
            }
        });
        timeRecyclerView.setAdapter(timeAdapter);
        seats = new ArrayList<>(); // Initialize seats list
        seats.add(new SeatItem("A1", true));
        seats.add(new SeatItem("B1", true));
        seats.add(new SeatItem("C1", true));
        seats.add(new SeatItem("D1", false));
        seats.add(new SeatItem("E1", true));
        seats.add(new SeatItem("F1", false));
        seats.add(new SeatItem("G1", true));
        seats.add(new SeatItem("H1", true));
        seats.add(new SeatItem("I1", true));
        seats.add(new SeatItem("J1", true));
        seats.add(new SeatItem("K1", true));
        seats.add(new SeatItem("L1", true));  seats.add(new SeatItem("M1", true));
        seats.add(new SeatItem("N1", true));
        seats.add(new SeatItem("O1", true));
        seats.add(new SeatItem("P1", false));
        seats.add(new SeatItem("Q1", true));
        seats.add(new SeatItem("R1", false));
      // Initialize seats list
        seats.add(new SeatItem("S1", true));
        seats.add(new SeatItem("A2", true));
        seats.add(new SeatItem("D2", true));
        seats.add(new SeatItem("D3", false));
        seats.add(new SeatItem("F2", true));
        seats.add(new SeatItem("G2", true));
        seats.add(new SeatItem("X1", true));
        seats.add(new SeatItem("Q2", false));
        seats.add(new SeatItem("O7", true));
        seats.add(new SeatItem("H8", true));
        seats.add(new SeatItem("M0", true));
        seats.add(new SeatItem("J8", false));  seats.add(new SeatItem("U9", true));
        seats.add(new SeatItem("B6", true));
        seats.add(new SeatItem("H5", true));
        seats.add(new SeatItem("V6", true));
        seats.add(new SeatItem("T7", true));
        seats.add(new SeatItem("X2", true));
        int spanCount = 6; // Number of columns in the grid
        seatRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false));
// Set adapter
        SeatAdapter seatAdapter = new SeatAdapter(seats, new SeatAdapter.OnSeatSelectedListener() {
            @Override
            public void onSeatSelected(SeatItem seat, int position) {
                if (seat.isSelected) {
                    selectedSeatNumbers.add(seat.seatNumber);
                } else {
                    selectedSeatNumbers.remove(seat.seatNumber);
                }
                updateSelectedSeatsAndTotal();
            }
        });
        seatRecyclerView.setAdapter(seatAdapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void updateSelectedSeatsAndTotal() {
        int totalAmount = 0;
        StringBuilder selectedSeats = new StringBuilder();

        // Use an Iterator
        Iterator<SeatItem> iterator = seats.iterator();
        while (iterator.hasNext()) {
            SeatItem seat = iterator.next();
            if (selectedSeatNumbers.contains(seat.seatNumber)) {
                totalAmount += 10;
                selectedSeats.append(seat.seatNumber).append(", ");
            }
        }

        // Update UI elements
        moneyTextView.setText(String.valueOf(totalAmount));
        selectedseatTextView.setText("Seat Selected: " + selectedSeats.toString());

    }


}