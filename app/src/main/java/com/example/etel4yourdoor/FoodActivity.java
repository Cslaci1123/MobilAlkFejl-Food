package com.example.etel4yourdoor;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FoodActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;

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


    }

    public void signOut(View view) {
        auth.signOut();
        finish();
    }
}