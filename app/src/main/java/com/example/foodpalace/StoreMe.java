package com.example.foodpalace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodpalace.Adapters.StoreAdapter;
import com.example.foodpalace.Common.Common;
import com.example.foodpalace.Models.StoreModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreMe extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth auth;
    SwipeRefreshLayout refreshLayout;
    Button pay;

    public static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_me);
        getSupportActionBar().setTitle("Store");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerstoreview);
        char end;
        end= Common.getMarketname().charAt(Common.getMarketname().length()-1);
        String foodname="food "+end;

       // Toast.makeText(StoreMe.this, foodname, Toast.LENGTH_SHORT).show();

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference(foodname);

        ArrayList<StoreModel> list=new ArrayList<>();
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(StoreMe.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);

        // Read from the database

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        StoreModel value=new StoreModel();
                        value=postSnapshot.getValue(StoreModel.class);
                        list.add(value);
                    }
                }
                else
                {
                    Toast.makeText(StoreMe.this, "Data not Received!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(StoreMe.this, "On Cancelled", Toast.LENGTH_SHORT).show();

                Log.w("TAG", "Failed to read value.", error.toException());
            }

        });

        refreshLayout=findViewById(R.id.refreshstoreMe);
        refreshLayout.setOnRefreshListener(() -> {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            StoreModel value = new StoreModel();
                            value = postSnapshot.getValue(StoreModel.class);
                            list.add(value);
                        }
                    } else {
                        Toast.makeText(StoreMe.this, "Data not Received!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(StoreMe.this, "On Cancelled", Toast.LENGTH_SHORT).show();

                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
            refreshLayout.setRefreshing(false);
        });

        ImageView marketimg=findViewById(R.id.ivMarketPic);
        Picasso.get().load(Common.getMarketimg()).into(marketimg);
        TextView marname=findViewById(R.id.ivMarketName);
        marname.setText(Common.getMarketname());

        StoreAdapter adapter = new StoreAdapter(list, StoreMe.this);
        recyclerView.setAdapter(adapter);


//        int n=Common.getCartval();
//        amount.setText("Rs. "+Common.getCartval());
//        if(Common.getCartval()!=n)
//        {
//            amount.setText("Rs. "+Common.getCartval());
//        }
        Button cart;
        cart=findViewById(R.id.tvCart);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(i==0)
                {
                    cart.setText("Rs. "+Common.getCartval());
                    i=1;
                }
                else
                {
                    cart.setText("Cart");
                    i=0;
                }
            }
        });


        pay=findViewById(R.id.tvPay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StoreMe.this, "To Payment", Toast.LENGTH_SHORT).show();
                Common.setPayamount(Common.getCartval()+"");
                Intent intent=new Intent(StoreMe.this,Payment.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu22,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        auth= FirebaseAuth.getInstance();
        switch(item.getItemId())
        {
            case R.id.myprofile:
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                if(auth.getCurrentUser()!=null)
                {
                    Intent intent;
                    intent=new Intent(StoreMe.this,my_Profile.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(StoreMe.this, "No Login info!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutme:
                auth.signOut();
                Intent intent;
                intent = new Intent(StoreMe.this, SignIN_Activity.class);
                startActivity(intent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(StoreMe.this, "Store!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(StoreMe.this, RecyclerView_main.class);
        startActivity(intent);
        return;
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}