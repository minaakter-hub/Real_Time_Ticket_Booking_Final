package com.rbmjltd.uddoktapay;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText email, password;
    TextView signup;
    Button btnlog, btnsign;
    networklisten net = new networklisten(); // Network listener instance

    @Override
    public void onStart() {
        super.onStart();
        // Register network receiver
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(net, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnlog = findViewById(R.id.button3);
        signup = findViewById(R.id.signup);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatepassword() | !validateUsername()) {
                    // Do nothing if validation fails
                } else {
                    checkuser();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), signup.class));
            }
        });
    }

    public Boolean validateUsername() {
        String val = email.getText().toString();
        if (val.isEmpty()) {
            email.setError("Email cannot be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public Boolean validatepassword() {
        String pass = password.getText().toString();
        if (pass.isEmpty()) {
            password.setError("password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public void checkuser() {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(emailInput);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDb = userSnapshot.child("password").getValue(String.class);

                        if (passwordFromDb != null && passwordFromDb.equals(passwordInput)) {
                            // Passwords match, proceed with login
                            String nameFromDb = userSnapshot.child("name").getValue(String.class);
                            String emailFromDb = userSnapshot.child("email").getValue(String.class);
                            String contactFromDb = userSnapshot.child("contact").getValue(String.class);

                            // Save user data to SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", nameFromDb);
                            editor.putString("email", emailFromDb);
                            editor.putString("contact", contactFromDb);
                            editor.apply();

                            // Navigate to dashboard activity
                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                            startActivity(intent);
                            finish(); // Close the login activity
                            return; // Exit the loop
                        } else {
                            // Passwords do not match, show error
                            password.setError("Invalid password");
                            password.requestFocus();
                            return; // Exit the loop
                        }
                    }
                } else {
                    // Handle case where user doesn't exist
                    email.setError("User doesn't exist");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(login.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        // Unregister network receiver
        unregisterReceiver(net);
        super.onStop();
    }
}