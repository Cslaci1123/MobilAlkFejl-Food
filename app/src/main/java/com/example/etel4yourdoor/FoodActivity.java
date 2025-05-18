package com.example.etel4yourdoor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private List<FoodItem> foods;

    private FirebaseUser user;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private FoodHelper adapter;
    private static final int  KEY = 979;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        int secretKey = getIntent().getIntExtra("KEY",0);

        if (secretKey != 979){
            finish();
        }

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();



        foods = Arrays.asList(
                new FoodItem("Pizza", 1500),
                new FoodItem("Hamburger", 1200),
                new FoodItem("Sült krumpli", 600)
        );

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodHelper(foods);
        recyclerView.setAdapter(adapter);

    }

    public void signOut(View view) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void toKosar(View view) {
        Intent intent = new Intent(this, KosarActivity.class);
        intent.putExtra("KEY", KEY);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View cartBadge = findViewById(R.id.cart_badge);
        List<FoodItem> cart = KosarHelper.getInstance().getCart();

        if (cart.isEmpty()) {
            cartBadge.setVisibility(View.GONE);
        } else {
            cartBadge.setVisibility(View.VISIBLE);
        }
    }

}