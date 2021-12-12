package com.example.foodpalace.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpalace.BluetoothActivity;
import com.example.foodpalace.Common.Common;
import com.example.foodpalace.Models.MarketModel;
import com.example.foodpalace.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.viewHolder>{

    ArrayList<MarketModel> list;
    Context context;

    public MarketAdapter(ArrayList<MarketModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_recycler_view,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MarketModel model=list.get(position);

        Picasso.get().load(model.getImage()).placeholder(R.drawable.logo).fit().into(holder.img);

        holder.txt.setText(model.getName());
        holder.img.setOnClickListener(view -> {
            Toast.makeText(context, "Item is Clicked!", Toast.LENGTH_SHORT).show();
            Common.setMarketimg(model.getImage());
            Common.setMarketname(model.getName());
            Intent intent=new Intent(context, BluetoothActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;

        public viewHolder(@NonNull View itemView ) {
            super(itemView);
            img=itemView.findViewById(R.id.marketPic);
            txt=itemView.findViewById(R.id.marketText);
        }
    }
}
