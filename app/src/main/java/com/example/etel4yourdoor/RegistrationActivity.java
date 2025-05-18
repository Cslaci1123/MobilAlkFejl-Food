package com.example.etel4yourdoor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {
    EditText ETEmail;
    EditText ETPassword;
    EditText ETPasswordConfirm;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ETEmail = findViewById(R.id.editTextEMail);
        ETPassword = findViewById(R.id.editTextPassword);
        ETPasswordConfirm = findViewById(R.id.editTextPasswordAgain);

        int secretKey = getIntent().getIntExtra("KEY",0);

        if (secretKey != 979){
            finish();
        }

        auth = FirebaseAuth.getInstance();
    }


    public void registerUser(View view) {

        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();
        String passwordConfirm = ETPasswordConfirm.getText().toString();

        if (email.isEmpty() || passwordConfirm.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Kérlek töltsd ki az összes mezőt", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length()<6){
            Toast.makeText(this, "A jelszó legyen legalább 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.equals(passwordConfirm)){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Sikertelen regisztráció " + task.getException().getMessage(),  Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(RegistrationActivity.this, "A jelszavak nem egyeznek meg",  Toast.LENGTH_LONG).show();
        }



    }

    public void returnPage(View view) {
        finish();
    }
}