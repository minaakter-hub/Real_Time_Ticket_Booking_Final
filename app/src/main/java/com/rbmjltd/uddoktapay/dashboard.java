package com.rbmjltd.uddoktapay;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dashboard extends AppCompatActivity {
    private TextView name1,email1;
   private Integer imgbtn;
private ImageView imageView;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private LinearLayout dotsContainer;
    private int dotsCount;
    networklisten net=new networklisten();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
       name1=findViewById(R.id.name);
        email1=findViewById(R.id.email);
        viewPager2 = findViewById(R.id.viewpage2);
        imageView=findViewById(R.id.location);
        dotsContainer = findViewById(R.id.dots_container);
        // Add banner data
        List<MovieBanner> banners = new ArrayList<>();
        banners.add(new MovieBanner(R.drawable.ringand));
        banners.add(new MovieBanner(R.drawable.nine));
        banners.add(new MovieBanner(R.drawable.adventure));
        banners.add(new MovieBanner(R.drawable.lord));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this,mapactivity.class));
            }
        });


        SharedPreferences sharedPreferences2 = getSharedPreferences("ProfileData", MODE_PRIVATE);
        String profileImageUriString = sharedPreferences2.getString("profileImageUri", null);

        // Set image to ImageView
        if (profileImageUriString != null && !profileImageUriString.isEmpty()) {
            try {
                Uri profileImageUri = Uri.parse(profileImageUriString);
                ImageView profileImageView = findViewById(R.id.image);
                profileImageView.setImageURI(profileImageUri);
            } catch (Exception e) {
                // Handle exception, e.g., display default image or error message
                ImageView profileImageView = findViewById(R.id.image);
                profileImageView.setImageResource(R.drawable.profile); // Default image
                Log.d("Dashboard", "Error loading profile image: " + e.getMessage());
            }
        } else {
            ImageView profileImageView = findViewById(R.id.image);
            profileImageView.setImageResource(R.drawable.profile); // Default image
        }



        BannerAdapter adapter = new BannerAdapter(banners);
        viewPager2.setAdapter(adapter);
        dotsCount = adapter.getItemCount();
        createDots();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String name = sharedPreferences.getString("username", "N/A");
        String email = sharedPreferences.getString("email", "N/A");

        name1.setText("Hello "+name);
        email1.setText(email);
        // Enable auto-slide
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateDots(position); // Update dots on page change
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // Slide every 3 seconds
            }
        });
        RecyclerView movieRecyclerView = findViewById(R.id.movie_recycler_view);
        RecyclerView movieRecyclerView2 = findViewById(R.id.movie_recycler_view2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        movieRecyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        movieRecyclerView2.setLayoutManager(layoutManager2);

        List<Integer> movieList = Arrays.asList(R.drawable.pemandi, R.drawable.morbius, R.drawable.oblivion,R.drawable.puspha); // Replace with your movie image resources
        List<String> movieNames = Arrays.asList("Movie 1", "Movie 2", "Movie 3", "Movie 4");
        MovieAdapter movieAdapter = new MovieAdapter(movieList,movieNames);
        movieRecyclerView.setAdapter(movieAdapter);
        List<Integer> movieList1 = Arrays.asList(R.drawable.pemandi, R.drawable.morbius, R.drawable.oblivion,R.drawable.puspha); // Replace with your movie image resources
        List<String> movieNames1 = Arrays.asList("Movie 1", "Movie 2", "Movie 3", "Movie 4");
        upcomadapter movieAdapter1 = new upcomadapter(movieList1,movieNames1);
        movieRecyclerView2.setAdapter(movieAdapter1);
        // ... (In your dashboard activity)
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.page_2)
                {
                    startActivity(new Intent(dashboard.this,movieactivity.class));
                }
                else if(item.getItemId()==R.id.page_3)
                {
                    startActivity(new Intent(dashboard.this,booked.class));
                }


                return true;
            }
        });

// Set the item click listener for the adapter
        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                if (position == 0) { // Check if it's the first item (pemandi)
                    startActivity(new Intent(dashboard.this,filmdetail.class));
                }
            }
        });

    }
    @Override
    protected void onStart(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(net,filter);
        super.onStart();

    }
    @Override
    protected void onStop(){

        unregisterReceiver(net);
        super.onStop();

    }
    private void createDots() {
        ImageView[] dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot)); // Replace with your inactive dot drawable

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            dots[i].setLayoutParams(params);

            dotsContainer.addView(dots[i]);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot)); // Replace with your active dot drawable
    }
    private void updateDots(int currentPage) {
        for (int i = 0; i < dotsCount; i++) {
            ImageView dot = (ImageView) dotsContainer.getChildAt(i);
            if (i == currentPage) {
                dot.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            } else {
                dot.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            }
        }
    }



    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = (viewPager2.getCurrentItem() + 1) % 3;
            viewPager2.setCurrentItem(nextItem, true);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }
}
