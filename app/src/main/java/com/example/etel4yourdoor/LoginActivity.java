package com.example.etel4yourdoor;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;


    EditText ETUserName;
    EditText ETPassword;
    private static final int  KEY = 979;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETUserName = findViewById(R.id.editTextUserName);
        ETPassword = findViewById(R.id.editTextPassword);

        int secretKey = getIntent().getIntExtra("KEY",0);

        if (secretKey != 979){
            finish();
        }

        auth = FirebaseAuth.getInstance();


    }

    public LoginActivity() {
        super();
    }


    public void login(View view) {


        String userName = ETUserName.getText().toString();
        String password = ETPassword.getText().toString();

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Kérlek töltsd ki az összes mezőt", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userName,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    toFood();
                } else {
                    Toast.makeText(LoginActivity.this, "Hibás E-mail vagy jelszó", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    private void toFood(){
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("KEY", KEY);

        startActivity(intent);
    }

    public void registration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("KEY", KEY);

        startActivity(intent);
    }

    public void returnToMain(View view) {
        finish();
    }

}