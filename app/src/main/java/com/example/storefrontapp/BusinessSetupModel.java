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

public class BusinessSetupModel extends AppCompatActivity {

    //create member variables
    EditText mFirstName, mLastName, mStreet, mCity, mState, mPostalCode, mCountry;
    Button mNextBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_setup);

        // instantiate the member variables
        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mStreet = findViewById(R.id.street);
        mCity = findViewById(R.id.city);
        mState = findViewById(R.id.state);
        mPostalCode = findViewById(R.id.postalcode);
        mCountry = findViewById(R.id.country );
        mNextBtn = findViewById(R.id.nextButton );

    }
}