package com.example.etel4yourdoor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private static final int  KEY = 979;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    public MainActivity() {
        super();
    }


    public void toLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("KEY", KEY);

        startActivity(intent);
    }

    public void registration(View view) {
    }
}
