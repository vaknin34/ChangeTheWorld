package com.example.changetheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changetheworld.model.FireStoreDB;
import com.example.changetheworld.model.Wallet;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class WalletActivity extends AppCompatActivity implements RecycleSubWalletClickInterface, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ArrayList<Wallet> items;
    String userName;
    String userType;
    TextView totalBalance;
    TextView symbol;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        userName = getIntent().getStringExtra(getString(R.string.userName));
        userType =  getIntent().getStringExtra("user_type");


        recyclerView= findViewById(R.id.recycleWallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalBalance = findViewById(R.id.balance);
        symbol = findViewById(R.id.symbol);


    }

    @Override
    protected void onResume() {
        super.onResume();
        items = new ArrayList<Wallet>();

        FireStoreDB.getInstance().LoadWallets(this, userName, userType, items, recyclerView, totalBalance, symbol);
    }


    @Override
    public void onItemClick(int position, String recycle_id) {
        Intent intent = new Intent(this,SubWallet.class);
        intent.putExtra("subWalletName",items.get(position).getCurrency());
        intent.putExtra(getString(R.string.userName), userName);
        intent.putExtra("userType", userType);
        intent.putExtra("localCurrencySymbol",symbol.getText().toString());
        intent.putExtra("subWalletBalance",items.get(position).getBalance());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void openWallet(){

        Intent intent = new Intent(this,WalletActivity.class);
        intent.putExtra(getString(R.string.userName), userName);
        intent.putExtra("user_type", "PrivateClient");
        startActivity(intent);
    }

    public void openProfile(){

        Intent intent = new Intent(this,ClientProfileActivity.class);
        intent.putExtra(getString(R.string.userName), userName);
        startActivity(intent);
    }

    public void openOrders(){

        Intent intent = new Intent(this,OrdersActivity.class);
        intent.putExtra(getString(R.string.userName), userName);
        intent.putExtra("user_type", "PrivateClient");
        startActivity(intent);
    }

    public void logOut(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_wallet:
                openWallet();
                break;
            case R.id.nav_profile:
                openProfile();
                break;
            case R.id.nav_orders:
                openOrders();
                break;
            case R.id.nav_logout:
                logOut();
                break;
        }
        return true;
    }
}