package com.example.storefrontapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText mFirstName, mLastName, mStreet, mCity, mState, mPostalCode, mCountry;
    Button mNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate the member variables
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mStreet = findViewById(R.id.street);
        mCity = findViewById(R.id.city);
        mState = findViewById(R.id.state);
        mPostalCode = findViewById(R.id.postalCode);
        mCountry = findViewById(R.id.country );
        mNextBtn = findViewById(R.id.nextButton );
    }

    //Logout user
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}