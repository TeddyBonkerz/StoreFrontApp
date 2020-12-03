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

public class home_fragment extends Fragment {

    String uID;
    String businessName;
    String adminName;
    String category;

    TextView homeHeader1, homeHeader2, homeHeader3;

    FirebaseAuth mAuth;

    ListView lv1, lv2;
    FirebaseListAdapter adapter1, adapter2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uID = currentUser.getUid();

        homeHeader1 = view.findViewById(R.id.homeHeader1);
        homeHeader2 = view.findViewById(R.id.homeHeader2);
        homeHeader3 = view.findViewById(R.id.homeHeader3);


        lv1 = view.findViewById(R.id.aLV);
        lv2 = view.findViewById(R.id.tLV);

        DatabaseReference refInventory = FirebaseDatabase.getInstance().getReference("inventory");
        Query queryInventory = refInventory.orderByChild("businessId").equalTo(uID);

        FirebaseListOptions<InventoryModel> items = new FirebaseListOptions.Builder<InventoryModel>()
                .setLayout(R.layout.items)
                .setLifecycleOwner(getActivity())
                .setQuery(queryInventory, InventoryModel.class)
                .build();

        adapter1 = new FirebaseListAdapter(items) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView pNameTextView = v.findViewById(R.id.pNameTextView);
                TextView pQuantityTextView = v.findViewById(R.id.pQuantityTextView);

                InventoryModel item = (InventoryModel) model;

                System.out.println(item.getProductName() + " 🔥🔥 " + item.getProductPrice());

                pNameTextView.setText("Product: " + item.getProductName());
                pQuantityTextView.setText("Quantity left: " + item.getProductQuantity());

            }
        };

        adapter2 = new FirebaseListAdapter(items) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView pNameTextView = v.findViewById(R.id.pNameTextView);
                TextView pQuantityTextView = v.findViewById(R.id.pQuantityTextView);

                InventoryModel item = (InventoryModel) model;

                System.out.println(item.getProductName() + " 🔥🔥 " + item.getProductPrice());

                pNameTextView.setText("Product: " + item.getProductName());
                pQuantityTextView.setText("Quantity left: " + item.getProductQuantity());

            }
        };

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);


        // Read Data from Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("busUsers");
        Query query = ref.orderByChild("uID").equalTo(uID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    businessName = snapshot.child("businessName").getValue(String.class);
                    adminName = snapshot.child("adminName").getValue(String.class);
                    category = snapshot.child("category").getValue(String.class);


                    homeHeader1.setText(businessName);
                    homeHeader2.setText(adminName);
                    homeHeader3.setText(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
