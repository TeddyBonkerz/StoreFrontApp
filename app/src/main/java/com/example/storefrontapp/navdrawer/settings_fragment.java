package com.example.storefrontapp.navdrawer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storefrontapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


public class settings_fragment extends Fragment {

    Button passResetBtn;

    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        System.out.println("SETTINGS PAGE OPENED!");

        mAuth = FirebaseAuth.getInstance();

        passResetBtn = view.findViewById(R.id.passResetBtn);

        passResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED!");
                final EditText resetMail = new EditText(getContext());
                AlertDialog.Builder passResetDialog = new AlertDialog.Builder(getContext());
                passResetDialog.setTitle("Reset Password");
                passResetDialog.setMessage("Enter Your Email To Receive The Reset Link");
                passResetDialog.setView(resetMail);

                passResetDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Reset Link Sent Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passResetDialog.show();
            }
        });


        return view;
    }


}