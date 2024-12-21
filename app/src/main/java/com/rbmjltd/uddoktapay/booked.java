package com.rbmjltd.uddoktapay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class booked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        // Retrieve booking details from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("BookingDetails", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String amount = sharedPreferences.getString("amount", "N/A");
        String invoiceId = sharedPreferences.getString("invoiceId", "N/A");
        String paymentMethod = sharedPreferences.getString("paymentMethod", "N/A");
        String senderNumber = sharedPreferences.getString("senderNumber", "N/A");
        String transactionId = sharedPreferences.getString("transactionId", "N/A");
        String date = sharedPreferences.getString("date", "N/A");
        String fee = sharedPreferences.getString("fee", "N/A");
        String chargeAmount = sharedPreferences.getString("chargeAmount", "N/A");
        String seat = sharedPreferences.getString("seat", "N/A"); // Retrieve seat
        String time = sharedPreferences.getString("time", "N/A"); // Retrieve time
        String date1 = sharedPreferences.getString("date1", "N/A"); // Retrieve date

        // Find UI elements
        TextView fullNameTextView = findViewById(R.id.fullNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView amountTextView = findViewById(R.id.amountTextView);
        TextView invoiceIdTextView = findViewById(R.id.invoiceIdTextView);
        TextView paymentMethodTextView = findViewById(R.id.paymentMethodTextView);
        TextView senderNumberTextView = findViewById(R.id.senderNumberTextView);
        TextView transactionIdTextView = findViewById(R.id.transactionIdTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView feeTextView = findViewById(R.id.feeTextView);
        TextView chargeAmountTextView = findViewById(R.id.chargeAmountTextView);
        TextView seatTextView = findViewById(R.id.seatTextView); // Find seat TextView
        TextView timeTextView = findViewById(R.id.timeTextView); // Find time TextView
        TextView date1TextView = findViewById(R.id.date1TextView); // Find date TextView

        // Set booking details to UI elements
        fullNameTextView.setText("Full Name: " + fullName);
        emailTextView.setText("Email: " + email);
        amountTextView.setText("Amount: " + amount);
        invoiceIdTextView.setText("Invoice ID: " + invoiceId);
        paymentMethodTextView.setText("Payment Method: " + paymentMethod);
        senderNumberTextView.setText("Sender Number: " + senderNumber);
        transactionIdTextView.setText("Transaction ID: " + transactionId);
        dateTextView.setText("Date: " + date);
        feeTextView.setText("Fee: " + fee);
        chargeAmountTextView.setText("Charged Amount: " + chargeAmount);
        seatTextView.setText("Seat: " + seat); // Set seat
        timeTextView.setText("Time: " + time); // Set time
        date1TextView.setText("Date: " + date1); // Set date
    }
}