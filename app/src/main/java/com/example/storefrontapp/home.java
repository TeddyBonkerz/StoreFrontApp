package com.example.storefrontapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.storefrontapp.navdrawer.account_fragment;
import com.example.storefrontapp.navdrawer.add_product_fragment;
import com.example.storefrontapp.navdrawer.home_fragment;
import com.example.storefrontapp.navdrawer.inventory_fragment;
import com.example.storefrontapp.navdrawer.payment_fragment;
import com.example.storefrontapp.navdrawer.settings_fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private boolean navBarRegistered = false;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    TextView userText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new home_fragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportActionBar().setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new home_fragment()).commit();
                break;
            case R.id.account:
                getSupportActionBar().setTitle("Account");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new account_fragment()).commit();
                break;
            case R.id.inventory:
                getSupportActionBar().setTitle("Inventory");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new inventory_fragment()).commit();
                break;
            case R.id.payment_details:
                getSupportActionBar().setTitle("Payment Methods");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new payment_fragment()).commit();
                break;
            case R.id.settings:
                getSupportActionBar().setTitle("Settings");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new settings_fragment()).commit();
                break;
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_product:
                displayHomeUpOrHamburger();
                getSupportActionBar().setTitle("Add Product");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new add_product_fragment()).commit();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayHomeUpOrHamburger() {
        boolean upButton = getSupportFragmentManager().getBackStackEntryCount() > 0;

        if (upButton) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            if (!navBarRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                });
                navBarRegistered = true;
            }
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(null);
            navBarRegistered = false;
        }
    }
}
