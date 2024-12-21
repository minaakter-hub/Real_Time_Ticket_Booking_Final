package com.rbmjltd.uddoktapay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Constants for payment

    private static final String API_KEY = "982d381360a69d419689740d9f2e26ce36fb7a50";
    private static final String CHECKOUT_URL = "https://sandbox.uddoktapay.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://sandbox.uddoktapay.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://uddoktapay.com";
    private static final String CANCEL_URL = "https://uddoktapay.com";


    // Instance variables to store payment information
    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;

    TextView name,email,amount;
    Button pay;
    LinearLayout mainlay,weblay;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        amount = findViewById(R.id.amount);
        pay = findViewById(R.id.pay);
        mainlay = findViewById(R.id.mainlay);
        weblay = findViewById(R.id.weblay);
        createNotificationChannel();
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String FULL_NAME = sharedPreferences.getString("username", "N/A");
        String EMAIL = sharedPreferences.getString("email", "N/A");
        name.setText(FULL_NAME);
        email.setText(EMAIL);

        SharedPreferences sharedPreferences1 = getSharedPreferences("BookingData", MODE_PRIVATE);
        String enteredAmount = sharedPreferences1.getString("amount", "N/A");
        amount.setText(enteredAmount);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentDone();
            }
        });



    }//==========OnCreate Bundle End ================================================
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("booking_channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void paymentDone() {

        mainlay.setVisibility(View.GONE);
        weblay.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String FULL_NAME = sharedPreferences.getString("username", "N/A");
        String EMAIL = sharedPreferences.getString("email", "N/A");

        SharedPreferences sharedPreferences1 = getSharedPreferences("BookingData", MODE_PRIVATE);
        String enteredAmount = sharedPreferences1.getString("amount", "N/A");
        Log.d("taka",enteredAmount);
        // Set your metadata values in the map
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("CustomMetaData1", "Meta Value 1");
        metadataMap.put("CustomMetaData2", "Meta Value 2");
        metadataMap.put("CustomMetaData3", "Meta Value 3");

        UddoktaPay.PaymentCallback paymentCallback = new UddoktaPay.PaymentCallback() {
            @Override
            public void onPaymentStatus(String status, String fullName, String email, String amount, String invoiceId,
                                        String paymentMethod, String senderNumber, String transactionId,
                                        String date, Map<String, String> metadataValues, String fee,String chargeAmount) {
                // Callback method triggered when the payment status is received from the payment gateway.
                // It provides information about the payment transaction.
                storedFullName = fullName;
                storedEmail = email;
                storedAmount = amount;
                storedInvoiceId = invoiceId;
                storedPaymentMethod = paymentMethod;
                storedSenderNumber = senderNumber;
                storedTransactionId = transactionId;
                storedDate = date;
                storedFee = fee;
                storedChargedAmount = chargeAmount;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Clear previous metadata values to avoid duplication
                        storedMetaKey1 = null;
                        storedMetaValue1 = null;
                        storedMetaKey2 = null;
                        storedMetaValue2 = null;
                        storedMetaKey3 = null;
                        storedMetaValue3 = null;

                        // Iterate through the metadata map and store the key-value pairs
                        for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                            String metadataKey = entry.getKey();
                            String metadataValue = entry.getValue();

                            if ("CustomMetaData1".equals(metadataKey)) {
                                storedMetaKey1 = metadataKey;
                                storedMetaValue1 = metadataValue;
                            } else if ("CustomMetaData2".equals(metadataKey)) {
                                storedMetaKey2 = metadataKey;
                                storedMetaValue2 = metadataValue;
                            } else if ("CustomMetaData3".equals(metadataKey)) {
                                storedMetaKey3 = metadataKey;
                                storedMetaValue3 = metadataValue;
                            }
                        }

                        // Update UI based on payment status
                        if ("COMPLETED".equals(status)) {
                            SharedPreferences sharedPreferences = getSharedPreferences("BookingDetails", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("fullName", fullName);
                            editor.putString("email", email);
                            editor.putString("amount", amount);
                            editor.putString("invoiceId", invoiceId);
                            editor.putString("paymentMethod", paymentMethod);
                            editor.putString("senderNumber", senderNumber);
                            editor.putString("transactionId", transactionId);
                            editor.putString("date", date);
                            editor.putString("fee", fee);
                            editor.putString("chargeAmount", chargeAmount);
                            // ... add other booking details like seat, time, date, etc.
                            editor.apply();

                            Intent intent = new Intent(MainActivity.this, dashboard.class);
                            startActivity(intent);
                            finish();
                            sendBookingNotification(fullName, email, amount, invoiceId, paymentMethod, senderNumber, transactionId, date, metadataValues, fee, chargeAmount);
                        } else if ("PENDING".equals(status)) {
//                            mainlay.setVisibility(View.VISIBLE);
//                            weblay.setVisibility(View.GONE);
                        } else if ("ERROR".equals(status)) {
//                            mainlay.setVisibility(View.VISIBLE);
//                            weblay.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };

        UddoktaPay uddoktapay = new UddoktaPay(webView, paymentCallback);
        uddoktapay.loadPaymentForm(API_KEY, FULL_NAME, EMAIL, enteredAmount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);

    }
    private void sendBookingNotification(String fullName, String email, String amount, String invoiceId,
                                         String paymentMethod, String senderNumber, String transactionId,
                                         String date, Map<String, String> metadataValues, String fee, String chargeAmount) {
        // Retrieve booking details from SharedPreferences or instance variables
        SharedPreferences sharedPreferences1 = getSharedPreferences("BookingData", MODE_PRIVATE);
        String storedDate = sharedPreferences1.getString("date", "N/A");
        String storedTime = sharedPreferences1.getString("time", "N/A");
        String storedSeats = sharedPreferences1.getString("seats", "N/A");

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "booking_channel")
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Booking Confirmed")
                .setContentText("Name: " + fullName + "\nTime: " + storedTime + "\nDate: " + storedDate + "\nSeat: " + storedSeats + "\nAmount: " + amount)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }


}