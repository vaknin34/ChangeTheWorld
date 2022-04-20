package com.example.changetheworld;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.changetheworld.model.Order;
import com.example.changetheworld.model.Search;
import com.example.changetheworld.model.Transaction;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<Search> data;

    public AdapterSearch(Context context, List<Search> data) {
        this.layoutInflater =  LayoutInflater.from(context);
        this.data = data;
    }


    @NonNull
    @Override

    public AdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.search_item_recycler_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String businessName = data.get(i).getBusinessName();
        viewHolder.businessName.setText(businessName);

        String rate = data.get(i).getRate();
        viewHolder.rate.setText(rate);

        String distance = data.get(i).getDistance()+"K";
        viewHolder.distance.setText(distance);

        String openClose = data.get(i).getOpenClose();
        viewHolder.openClose.setText(openClose);

        String businessAddress = data.get(i).getBusiness_street()+" "+data.get(i).getBusiness_no()+","+data.get(i).getBusiness_city();
        viewHolder.businessAddress.setText(businessAddress);
    }

    @Override
    public int getItemCount() {
        Log.d("Yuval", "getItemCount: " + data.size());
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView businessName,rate,distance,openClose,businessAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessName=itemView.findViewById(R.id.businessNameRec);
            rate=itemView.findViewById(R.id.rateRec);
            distance=itemView.findViewById(R.id.distanceRec);
            openClose=itemView.findViewById(R.id.open_close);
            businessAddress=itemView.findViewById(R.id.AddressRec);
        }
    }
}
