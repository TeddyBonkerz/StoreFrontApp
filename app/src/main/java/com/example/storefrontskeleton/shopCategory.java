package com.example.storefrontskeleton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class shopCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_category);

        Spinner mySpinner = (Spinner) findViewById(R.id.categorySpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(shopCategory.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        ImageButton btn = (ImageButton) findViewById(R.id.forwardBtnToAdmin);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shopCategory.this, mailingAddress.class);
                startActivity(intent);
            }
        });
    }
}