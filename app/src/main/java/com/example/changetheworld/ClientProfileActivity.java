package com.example.changetheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.changetheworld.model.FireStoreDB;
import com.google.android.material.navigation.NavigationView;

public class ClientProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String userName;
    TextView full_name;
    TextView mail_address;
    TextView phone_number;
    TextView local_currency;
    ImageView profile;
    Button editButton;
    TextView passportValue;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        full_name = findViewById(R.id.fullname_value);
        mail_address = findViewById(R.id.maillAddress_value);
        phone_number = findViewById(R.id.phoneNumber_value);
        local_currency = findViewById(R.id.localCurrency_value);
        userName = getIntent().getStringExtra(getString(R.string.userName));
        ((TextView)findViewById(R.id.username_profile_name)).setText(userName);
        FireStoreDB.getInstance().loadClientData(userName, full_name, mail_address, phone_number , local_currency);
        profile = findViewById(R.id.profilePhoto);
        FireStoreDB.getInstance().LoadProfilePhoto(profile, userName);
        passportValue = findViewById(R.id.passport_value);
        FireStoreDB.getInstance().checkExistPhoto(passportValue, userName, "passport_photo");


        drawerLayout = findViewById(R.id.drawer_menu);
        navigationView = findViewById(R.id.nav_view);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FireStoreDB.getInstance().loadClientData(userName, full_name, mail_address, phone_number , local_currency);
        profile = findViewById(R.id.profilePhoto);
        FireStoreDB.getInstance().LoadProfilePhoto(profile, userName);
        FireStoreDB.getInstance().checkExistPhoto(passportValue, userName, "passport_photo");

        editButton = findViewById(R.id.editBtn);
        editButton.setOnClickListener(view -> openEdit());
    }

    private void openEdit() {
        Intent intent = new Intent(this,EditClientProfileActivity.class);
        intent.putExtra("user_name", userName);
        startActivity(intent);
    }

    public void openOrders(){

        Intent intent = new Intent(this,OrdersActivity.class);
        intent.putExtra(getString(R.string.userName), userName);
        intent.putExtra("user_type", "PrivateClient");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void logOut(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void openWallet(){

        Intent intent = new Intent(this,WalletActivity.class);
        intent.putExtra(getString(R.string.userName), userName);
        intent.putExtra("user_type", "PrivateClient");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void openAbout(){
        Intent intent = new Intent(this,AboutPage.class);
        intent.putExtra(getString(R.string.userName), userName);
        intent.putExtra("user_type", "PrivateClient");
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(this,client_home_page.class);
                intent.putExtra(getString(R.string.userName), userName);
                intent.putExtra(getString(R.string.localCurrency), local_currency.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_orders:
                openOrders();
                break;
            case R.id.nav_logout:
                logOut();
                break;
            case R.id.nav_wallet:
                openWallet();
                break;
            case R.id.nav_about:
                openAbout();
                break;
        }
        return true;
    }
}