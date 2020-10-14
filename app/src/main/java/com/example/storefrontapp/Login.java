package com.example.storefrontapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn, mRegisterBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password1);
        mLoginBtn = findViewById(R.id.loginButton);
        mRegisterBtn = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (email.isEmpty()){
                    mEmail.setError("Enter a valid email address!");
                    return;
                }

                if (password.isEmpty()){
                    mPassword.setError("Password is required! ");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In  Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Something is wrong! " + task.getException().getMessage()  , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }
}