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

                System.out.println(item.getProductName() + " 🔥🔥 " + item.getProductPrice());

                pNameTextView.setText("Product: " + item.getProductName());
                pDescriptionTextView.setText("Description: " + item.getProductDescription());
                pTypeTextView.setText("Type: " + item.getProductType());
                pPriceTextView.setText("Price: " + item.getProductPrice());
                pQuantityTextView.setText("Quantity left: " + item.getProductQuantity());

            }
        };

        lv.setAdapter(adapter);

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
