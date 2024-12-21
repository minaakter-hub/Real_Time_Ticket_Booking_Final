package com.rbmjltd.uddoktapay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;


import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class movieactivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    List<movieitem> items = new ArrayList<>();
    Myadapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        searchView=findViewById(R.id.searchview);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return false;
            }
        });
        recyclerView=findViewById(R.id.movierecylce);

        items.add(new movieitem("Patan", "The great movie", R.drawable.beautyndbeast));
        items.add(new movieitem("3 Idiots", "The greate movie", R.drawable.adam));
        items.add(new movieitem("Shershaah", "Pvc Awarded", R.drawable.adventure));
        items.add(new movieitem("Singham Again", "Action,Drama", R.drawable.morbius));
        items.add(new movieitem("Dhadak 2", "Romance", R.drawable.oblivion));

        items.add(new movieitem("Jigra", "Action", R.drawable.pemandi));
        items.add(new movieitem("Megan Thee Stallion", "Documnetray", R.drawable.puspha));
        items.add(new movieitem("Martha", "Documentray", R.drawable.marvels));
//        items.add(new movieitem("Time Cut", "Horror,Thriller", R.drawable.timecut));
//        items.add(new movieitem("Dont Move", "Horror,Thriller", R.drawable.timecut1));
//        items.add(new movieitem("Hijack", "Drama,Thriller", R.drawable.hijack));

        myAdapter = new Myadapter(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    private void filterlist(String Text) {
        List<movieitem>filteredlist=new ArrayList<>();
        for(movieitem item:items){
            if(item.getTitle().toLowerCase().contains(Text.toLowerCase())){
                filteredlist.add(item);
            }
        }
        if(filteredlist.isEmpty()){
            Toast.makeText(this,"No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            myAdapter.setFilteredList(filteredlist);
        }

    }
}