package com.example.foodpalace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodpalace.Adapters.MarketAdapter;
import com.example.foodpalace.Models.MarketModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerView_main extends AppCompatActivity {

    RecyclerView recyclerView;
    //ActivityMainBinding binding;
    FirebaseAuth auth;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_recyclerview);
        getSupportActionBar().setTitle("Markets");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("menu");

        ArrayList<MarketModel> list = new ArrayList<>();
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(RecyclerView_main.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);

        refreshLayout=findViewById(R.id.refreshMe);
        refreshLayout.setOnRefreshListener(() -> {
            // Read from the database
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot != null) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            MarketModel value;
                            value=postSnapshot.getValue(MarketModel.class);
                            list.add(value);
                        }
                    }
                    else
                    {
                        Toast.makeText(RecyclerView_main.this, "Data not Received!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(RecyclerView_main.this, "On Cancelled", Toast.LENGTH_SHORT).show();
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
            refreshLayout.setRefreshing(false);
        });


        // Read from the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        MarketModel value;
                        value=postSnapshot.getValue(MarketModel.class);
                        list.add(value);
                    }
                }
                else
                {
                    Toast.makeText(RecyclerView_main.this, "Data not Received!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(RecyclerView_main.this, "On Cancelled", Toast.LENGTH_SHORT).show();

                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        MarketAdapter adapter = new MarketAdapter(list, RecyclerView_main.this);
        recyclerView.setAdapter(adapter);

//        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//        recyclerview.setLayoutManager(staggered);
//        GridLayoutManager grid=new GridLayoutManager(this,3);
//        recyclerview.setLayoutManager(grid);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu22,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        auth=FirebaseAuth.getInstance();
        switch(item.getItemId())
        {
            case R.id.myprofile:
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                if(auth.getCurrentUser()!=null)
                {
                    Intent intent;
                    intent=new Intent(RecyclerView_main.this,my_Profile.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RecyclerView_main.this, "No Login info!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutme:
                auth.signOut();
                Intent intent;
                intent = new Intent(RecyclerView_main.this, SignIN_Activity.class);
                startActivity(intent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(RecyclerView_main.this, "Exit!", Toast.LENGTH_SHORT).show();
        finishAffinity();
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}