package com.rbmjltd.uddoktapay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class filmdetail extends AppCompatActivity {

    Button getreservation, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filmdetail);

        getreservation = findViewById(R.id.reservation_button);
        share = findViewById(R.id.share_button);

        getreservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(filmdetail.this, seatactivity.class));
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isms = new Intent(Intent.ACTION_SEND);
                isms.setType("text/plain");
                isms.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=Go8nTmfrQd8");
                startActivity(Intent.createChooser(isms, "Share via"));
            }
        });

        RecyclerView castingRecyclerView = findViewById(R.id.casting_recycler_view);
        castingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<CastingItem> castingItems = new ArrayList<>();
        castingItems.add(new CastingItem(R.drawable.actor1)); // Replace with your actor image resource IDs
        castingItems.add(new CastingItem(R.drawable.actor2));
        castingItems.add(new CastingItem(R.drawable.actor3));
        castingItems.add(new CastingItem(R.drawable.actor4));
        // ... add more casting items

        CastingAdapter castingAdapter = new CastingAdapter(castingItems);
        castingRecyclerView.setAdapter(castingAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}