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

    //create member variables
    EditText mEmail, mPassword;
    Button mLoginBtn, mRegisterBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // instantiate the member variables
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password1);
        mLoginBtn = findViewById(R.id.loginButton);
        mRegisterBtn = findViewById(R.id.registerButton);

        //create a Firebase instance
        mAuth = FirebaseAuth.getInstance();

        // Login User
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get email, password from the input fields
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                // check if the email and password fields are not empty
                if (email.isEmpty()){
                    mEmail.setError("Enter a valid email address!");
                    return;
                }

                if (password.isEmpty()){
                    mPassword.setError("Password is required! ");
                    return;
                }

                // Send email and password to Firebase to authenticate and login the user
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // if successful, show a Toast and go to GettingStarted
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In  Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }

                        //if not, then show error
                        else{
                            Toast.makeText(Login.this, "Something is wrong! " + task.getException().getMessage()  , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        // take user to Register Activity
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }
}