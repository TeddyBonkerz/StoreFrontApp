package com.example.storefrontapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Get_Started extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String header;
    String category;
    String businessName;
    String mailingAddress;
    String adminName;

    EditText mBusinessName;
    EditText mMailingAddress;
    EditText mAdminName;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    busUser bUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started1);

    }

    public void switchLayout(View view) {
        bUser = new busUser();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("busUsers");

        switch (view.getId()) {
            case R.id.getStartedBtn1:
                setContentView(R.layout.get_started2);
                break;

            case R.id.getStartedBtn2:
                mBusinessName = findViewById(R.id.businessName);
                businessName = mBusinessName.getText().toString().trim();
                header = businessName;

//                System.out.println("🔥"+businessName);

                setContentView(R.layout.get_started3);

                Spinner category_spinner = findViewById(R.id.spinner_category);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_category, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category_spinner.setAdapter(adapter1);
                category_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

                break;

            case R.id.getStartedBtn3:
                setContentView(R.layout.get_started4);
                break;

            case R.id.getStartedBtn4:
                mMailingAddress = findViewById(R.id.mailingAddressField);
                mailingAddress = mMailingAddress.getText().toString().trim();

//                System.out.println("🔥"+mailingAddress);

                setContentView(R.layout.get_started5);
                break;

            case R.id.getStartedBtn5:
                mAdminName = findViewById(R.id.adminNameField);
                adminName = mAdminName.getText().toString().trim();

//                System.out.println("🔥"+adminName);

                bUser.setBusinessName(businessName);
                bUser.setCategory(category);
                bUser.setMailingAddress(mailingAddress);
                bUser.setAdminName(adminName);

                reference.child(header).setValue(bUser);

                Toast.makeText(this, "Business Setup is Successful",
                        Toast.LENGTH_SHORT).show();

                setContentView(R.layout.activity_home);

                break;

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getItemAtPosition(i).toString();
        System.out.println("🔥"+ category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}