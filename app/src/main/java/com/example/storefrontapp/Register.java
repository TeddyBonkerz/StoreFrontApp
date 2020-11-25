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

public class Register extends AppCompatActivity {

    //create member variables
    EditText mEmail, mPassword1, mPassword2;
    Button mRegistrationBtn, mLoginBtn;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // instantiate the member variables
        mEmail = findViewById(R.id.email);
        mPassword1 = findViewById(R.id.password1);
        mPassword2 = findViewById(R.id.password2);
        mRegistrationBtn = findViewById(R.id.signupButton);
        mLoginBtn = findViewById(R.id.loginButton );

        //create a Firebase instance
        mAuth = FirebaseAuth.getInstance();


        //if user is already logged it, redirect user to GettingStarted
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Get_Started.class));
            finish();
        }

        // User Registration
        mRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get email, passwords from the input fields
                final String email = mEmail.getText().toString().trim();
                final String password1 = mPassword1.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();


                // check whether email and passwords are are valid and properly filled
                if (email.isEmpty()){
                    mEmail.setError("Enter a valid email address!");
                    return;
                }

                if (password1.isEmpty() || password2.isEmpty()){
                    mPassword1.setError("Password is required! ");
                    mPassword2.setError("Password is required! ");
                    return;
                }

                if (password1.length() < 6){
                    mPassword1.setError("Password must be atleast 6 characters long!");
                    return;
                }

                if ( !(password1.equals(password2)) ){
                    mPassword2.setError("Passwords does not match!");
                    return;
                }

                // Pass the email and password to Firebase to register the user
                mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // if successful, show a Toast and go to GettingStarted
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created Successfully",
                                    Toast.LENGTH_SHORT).show();


                            mAuth.signInWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    // if successful, show a Toast and go to GettingStarted
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(), Get_Started.class));
                                    }

                                }
                            });



                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }

                        //if not, then show error
                        else{
                            Toast.makeText(Register.this, "Something is wrong! "
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        // take user to LoginActivity
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}