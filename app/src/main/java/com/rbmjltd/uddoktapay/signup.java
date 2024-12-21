package com.rbmjltd.uddoktapay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    private Button btn_signup;
    private EditText user, email, contact, pass, conpass;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        pass = findViewById(R.id.pass);
        conpass = findViewById(R.id.conpass);
        btn_signup = findViewById(R.id.btnsign);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) { // Check network connectivity
                    registerUser();
                } else {
                    showNetworkErrorDialog();
                }
            }
        });

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser() {
        String email1 = email.getText().toString();
        String password1 = pass.getText().toString();
        String username1 = user.getText().toString();
        String contact1 = contact.getText().toString();
        String conpass1 = conpass.getText().toString();

        // Input validation
        if (TextUtils.isEmpty(email1) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Enter a valid Email");
            return;
        }
        if (TextUtils.isEmpty(password1) || password1.length() < 6) {
            pass.setError("Enter Password (min 6 characters)");
            return;
        }
        if (TextUtils.isEmpty(username1)) {
            user.setError("Enter Username");
            return;
        }
        if (TextUtils.isEmpty(contact1)) {
            contact.setError("Enter Contact");
            return;
        }
        if (TextUtils.isEmpty(conpass1)) {
            conpass.setError("Enter Confirm Password");
            return;
        }

        if (!password1.equals(conpass1)) {
            conpass.setError("Passwords do not match!");
            return;
        }

        // Save user data to Realtime Database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        User helperClass = new User(username1, email1, contact1, password1);
        reference.child(username1).setValue(helperClass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(signup.this, "You have signup successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), camera.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(signup.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Check network connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Show network error dialog
    private void showNetworkErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout_dialog = LayoutInflater.from(this).inflate(R.layout.forcheckingnet, null);
        builder.setView(layout_dialog);

        AppCompatButton btnretry = layout_dialog.findViewById(R.id.try_again_button);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        dialog.getWindow().setGravity(Gravity.CENTER);

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Retry signup if network is available
                if (isNetworkAvailable()) {
                    registerUser();
                } else {
                    showNetworkErrorDialog(); // Show dialog again if still no network
                }
            }
        });
    }

    // ... (rest of the code)
}