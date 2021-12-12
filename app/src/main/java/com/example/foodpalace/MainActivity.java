package com.example.foodpalace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login,register;
    TextView skip;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        login=findViewById(R.id.btnLogin);
        login.setOnClickListener(view -> {
            if(auth.getCurrentUser()!=null)
                Toast.makeText(MainActivity.this, "Already Logged-in!", Toast.LENGTH_SHORT).show();
            else
            Toast.makeText(MainActivity.this, "Login!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,SignIN_Activity.class);
            startActivity(intent);
        });
        register=findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Register has been Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Signup_Activity.class);
                startActivity(intent);
            }
        });
        skip=findViewById(R.id.etSkip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Registration is Skipped!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,RecyclerView_main.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Exit!", Toast.LENGTH_SHORT).show();
        finishAffinity();
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}