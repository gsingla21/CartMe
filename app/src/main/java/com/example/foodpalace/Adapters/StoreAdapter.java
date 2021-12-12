package com.example.foodpalace.Adapters;

import static java.lang.Integer.parseInt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpalace.Common.Common;
import com.example.foodpalace.Models.StoreModel;
import com.example.foodpalace.R;
import com.example.foodpalace.StoreMe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.viewHolder>{

    ArrayList<StoreModel> list;
    StoreMe context;

    public StoreAdapter(ArrayList<StoreModel> list, StoreMe context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_store,parent,false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StoreModel model=list.get(position);
        Picasso.get().load(model.getFimage()).fit().into(holder.stimg);
        holder.ingredients.setText(model.getIngredients());
        holder.nametag.setText(model.getFname());
        holder.price.setText(model.getPrice());
        holder.plus.setOnClickListener(view -> {
            holder.qty.setText((parseInt(holder.qty.getText().toString()) + 1) + "");
            int p=parseInt(holder.price.getText().toString().substring(4));
            Common.setCartval(Common.getCartval()+p);
        });
        holder.minus.setOnClickListener(view -> {
            if(parseInt(holder.qty.getText().toString())>0)
            {
                holder.qty.setText(parseInt(holder.qty.getText().toString())-1+"");
                int p=parseInt(holder.price.getText().toString().substring(4));
                Common.setCartval(Common.getCartval()-p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView stimg;
        TextView price,nametag,ingredients;
        Button plus,minus;
        TextView qty;

        public viewHolder(View view) {
            super(view);
            plus=view.findViewById(R.id.tvplusone);
            minus=view.findViewById(R.id.tvminusone);
            qty=view.findViewById(R.id.etQty);

            stimg=view.findViewById(R.id.ivFoodpic);
            price=view.findViewById(R.id.etPricetag);
            nametag=view.findViewById(R.id.etFoodname);
            ingredients=view.findViewById(R.id.etIngredients);
        }
    }
}
