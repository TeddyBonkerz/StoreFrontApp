package com.example.storefrontapp.navdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.storefrontapp.InventoryModel;
import com.example.storefrontapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class add_product_fragment extends Fragment implements AdapterView.OnItemSelectedListener{

    String productName;
    String productType;
    String productPrice;
    String productQuantity;
    String productDescription;
    String uID;
    String header;

    String businessName;

    //member variables
    EditText mName;
    EditText mPrice;
    EditText mDescription;
    EditText mQuantity;
    Button addToInventory;

    InventoryModel inventory;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    private ImageView productImg;
    public Uri imageUri;
    public Spinner product_type_spinner;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uID = currentUser.getUid();

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();

        productImg = (ImageView) view.findViewById(R.id.productImg);
        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        product_type_spinner = view.findViewById(R.id.product_type_spinner);
        ArrayAdapter<CharSequence> adapter1 =  ArrayAdapter.createFromResource(getContext(), R.array.product_type_spinner, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_type_spinner.setAdapter(adapter1);
        product_type_spinner.setOnItemSelectedListener(this);

        inventory = new InventoryModel();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("inventory");

        mName = view.findViewById(R.id.productName);
        mPrice = view.findViewById(R.id.productPrice);
        mDescription = view.findViewById(R.id.productDescription);
        mQuantity = view.findViewById(R.id.productQuantity);
        addToInventory = view.findViewById(R.id.addToInventoryBtn);

        addToInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName = mName.getText().toString().trim();
                productDescription = mDescription.getText().toString().trim();
                productQuantity = mQuantity.getText().toString().trim();
                productPrice = mPrice.getText().toString().trim();

                inventory.setProductName(productName);
                inventory.setProductDescription(productDescription);
                inventory.setProductType(productType);
                inventory.setProductPrice(productPrice);
                inventory.setProductQuantity(productQuantity);

                reference.child(uID).setValue(inventory);


            }
        });

        // Reading Data From the Firebase Database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("busUsers");
        Query query = ref.orderByChild("uID").equalTo(uID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    businessName = snapshot.child("businessName").getValue(String.class);

//                    Log.i("DB", snapshot.getKey());
//                    Log.i("DB", snapshot.child("adminName").getValue(String.class));
//                    Log.i("DB", snapshot.child("businessName").getValue(String.class));
//                    Log.i("DB", snapshot.child("category").getValue(String.class));
//                    Log.i("DB", snapshot.child("mailingAddress").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        return view;
    }


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData() != null){
            imageUri = data.getData();
            productImg.setImageURI(imageUri);

            uploadImg();
        }
    }

    private void uploadImg() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randKey = UUID.randomUUID().toString();

        StorageReference riversRef = mStorageReference.child("productImages/" + businessName + "/" + randKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();

                        Toast.makeText(getActivity(), "Error! " + exception.getMessage(), Toast.LENGTH_LONG).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        productType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
