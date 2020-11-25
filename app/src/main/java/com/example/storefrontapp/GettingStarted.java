package com.example.storefrontapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GettingStarted extends AppCompatActivity {

    EditText mFirstName, mLastName, mStreet, mCity, mState, mPostalCode, mCountry;
    Button mNextBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    busUser bUser;

    //private int bID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        // instantiate the member variables
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mStreet = findViewById(R.id.street);
        mCity = findViewById(R.id.city);
        mState = findViewById(R.id.state);
        mPostalCode = findViewById(R.id.postalCode);
        mCountry = findViewById(R.id.country);
        mNextBtn = findViewById(R.id.nextButton );

        bUser = new busUser();

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("busUsers");

                int zip = Integer.parseInt(mPostalCode.getText().toString().trim());

                //Setting variables in busUser class
//                String name = bUser.setfName(mFirstName.getText().toString().trim());
//                bUser.setlName(mLastName.getText().toString().trim());
//                bUser.setStreet(mStreet.getText().toString().trim());
//                bUser.setCity(mCity.getText().toString().trim());
//                bUser.setState(mState.getText().toString().trim());
//                bUser.setZipCode(zip);
//                bUser.setCountry(mCountry.getText().toString().trim());

                //populating busUsers node in Firebase with above variables organized (temporarily) by owner's first name.
//                reference.child(name).setValue(bUser);
            }
        });
    }

    //Logout user
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}