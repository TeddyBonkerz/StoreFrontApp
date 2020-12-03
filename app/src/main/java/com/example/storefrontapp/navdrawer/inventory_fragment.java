package com.example.storefrontapp.navdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.storefrontapp.InventoryModel;
import com.example.storefrontapp.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class inventory_fragment extends Fragment {

    String uID;
    String businessName;
    String adminName;
    String category;

    String productName, productDescription, productPrice, productQuantity, productType;

    //Firebase variables
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;


    ListView lv;
    FirebaseListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        // Getting the Info for the Logged In(authenticated) User
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uID = currentUser.getUid();

        lv = view.findViewById(R.id.listView);

//        final TextView pDescriptionTextView = view.findViewById(R.id.pDescriptionTextView);
//        final TextView pPriceTextView = view.findViewById(R.id.pPriceTextView);
//        final TextView pNameTextView = view.findViewById(R.id.pNameTextView);
//        final TextView pQuantityTextView = view.findViewById(R.id.pQuantityTextView);
//        final TextView pTypeTextView = view.findViewById(R.id.pTypeTextView);

        DatabaseReference refInventory = FirebaseDatabase.getInstance().getReference("inventory");
        Query queryInventory = refInventory.orderByChild("businessId").equalTo(uID);

        FirebaseListOptions<InventoryModel> items = new FirebaseListOptions.Builder<InventoryModel>()
                .setLayout(R.layout.items)
                .setLifecycleOwner(getActivity())
                .setQuery(queryInventory, InventoryModel.class)
                .build();

        adapter = new FirebaseListAdapter(items) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView pDescriptionTextView = v.findViewById(R.id.pDescriptionTextView);
                TextView pPriceTextView = v.findViewById(R.id.pPriceTextView);
                TextView pNameTextView = v.findViewById(R.id.pNameTextView);
                TextView pQuantityTextView = v.findViewById(R.id.pQuantityTextView);
                TextView pTypeTextView = v.findViewById(R.id.pTypeTextView);

                InventoryModel item = (InventoryModel) model;

                pNameTextView.setText("Product: " + item.getProductName());
                pDescriptionTextView.setText("Description: " + item.getProductDescription());
                pTypeTextView.setText("Type: " + item.getProductType());
                pPriceTextView.setText("Price: " + item.getProductPrice());
                pQuantityTextView.setText("Quantity left: " + item.getProductQuantity());

            }
        };

        lv.setAdapter(adapter);


//        queryInventory.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//
//                for(DataSnapshot snapshot : datasnapshot.getChildren()){
//                    productName = snapshot.child("productName").getValue(String.class);
//                    productDescription = snapshot.child("productDescription").getValue(String.class);
//                    productPrice = snapshot.child("productPrice").getValue(String.class);
//                    productQuantity = snapshot.child("productQuantity").getValue(String.class);
//                    productType = snapshot.child("productType").getValue(String.class);
//
//                    if(productName == null){
//                        pNameTextView.setText("Nothing has been added to the Inventory yet!");
//                    }
//
//                    if (productName != null){
//                        pNameTextView.setVisibility(View.VISIBLE);
//                        pPriceTextView.setVisibility(View.VISIBLE);
//                        pDescriptionTextView.setVisibility(View.VISIBLE);
//                        pQuantityTextView.setVisibility(View.VISIBLE);
//                        pTypeTextView.setVisibility(View.VISIBLE);
//                    }
//
//
//                    pNameTextView.setText("Product: " + productName);
//                    pPriceTextView.setText("Price: " + productPrice);
//                    pDescriptionTextView.setText("Description: " + productDescription);
//                    pQuantityTextView.setText("Quantity left: " + productQuantity);
//                    pTypeTextView.setText("Type: " + productType);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
