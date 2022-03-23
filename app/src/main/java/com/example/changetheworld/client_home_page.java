package com.example.changetheworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.changetheworld.model.CurrencyDataApi;
import com.example.changetheworld.model.FireStoreDB;
import com.example.changetheworld.model.currency;

import java.util.ArrayList;

public class client_home_page extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterCurrency adapterCurrency;
    ArrayList<currency> items = new ArrayList<>();
    TextView userName;
    ImageView profilPhoto;
    CurrencyDataApi api = new CurrencyDataApi();
    Button moveToWallet;
    String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_page);
        user_name = getIntent().getStringExtra("userName");
        userName = findViewById(R.id.username);
        String localCurrency = getIntent().getStringExtra("localCurrency");
        userName.setText(user_name);

        Thread t = new Thread(() -> {
            Float value_usd = api.GetCurrencyValue("USD",localCurrency);
            Float change_usd = api.GetCurrencyDailyChange("USD", localCurrency);
            Float value_euro = api.GetCurrencyValue("EUR",localCurrency);
            Float change_euro = api.GetCurrencyDailyChange("EUR", localCurrency);
            Float value_cny = api.GetCurrencyValue("CNY",localCurrency);
            Float change_cny = api.GetCurrencyDailyChange("CNY", localCurrency);
            Float value_ils = api.GetCurrencyValue("ILS",localCurrency);
            Float change_ils = api.GetCurrencyDailyChange("ILS", localCurrency);
            Float value_gbp = api.GetCurrencyValue("GBP",localCurrency);
            Float change_gbp = api.GetCurrencyDailyChange("GBP", localCurrency);

            if (!localCurrency.equals("USD"))
                items.add(new currency(R.drawable.usd,""+value_usd,""+change_usd));
            if (!localCurrency.equals("EUR"))
                items.add(new currency(R.drawable.eur,""+value_euro,""+change_euro));
            if (!localCurrency.equals("CNY"))
                items.add(new currency(R.drawable.cny,""+value_cny,""+change_cny));
            if (!localCurrency.equals("ILS"))
                items.add(new currency(R.drawable.ils,""+value_ils,""+change_ils));
            if (!localCurrency.equals("GBP"))
                items.add(new currency(R.drawable.gbp,""+value_gbp,""+change_gbp));
        });
        t.start();
        try {
            t.join();
//            String usd = items.get(0).getDailyChange();
//            String eur = items.get(1).getDailyChange();
//            if (usd.equals("0.0") || eur.equals("0.0")){
//                Toast.makeText(this,"Failed to connect currency api",Toast.LENGTH_LONG).show();
//            }

            recyclerView=findViewById(R.id.recycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapterCurrency = new AdapterCurrency(this,items);
            recyclerView.setAdapter(adapterCurrency);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        profilPhoto = findViewById(R.id.profilePhoto);
        FireStoreDB.getInstance().LoadProfilePhoto(profilPhoto, user_name);



        moveToWallet = (Button) findViewById(R.id.moveToWallet);
        moveToWallet.setOnClickListener(view ->  { openWallet(); });
    }

    public void openWallet(){

        Intent intent = new Intent(this,WalletActivity.class);
        intent.putExtra("userName", user_name);
        startActivity(intent);
    }
}

