package com.example.foodpalace;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


    Thread thread=new Thread();
        thread = new Thread(){
          public void run(){
              try {
                  sleep(4000);
              }
              catch (Exception e){
                  e.printStackTrace();
              }
              finally {
                  if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                  {
                      Intent intent=new Intent(SplashActivity.this,RecyclerView_main.class);
                      startActivity(intent);
                  }
                  else
                  {
                      Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                      startActivity(intent);
                  }
              }
          }
        };
        thread.start();
    }
}