package com.example.foodpalace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class my_Profile extends AppCompatActivity {

    Button reset;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        CircleImageView myimage=findViewById(R.id.my_image);
        Picasso.get().load(user.getPhotoUrl()).into(myimage);

        TextView name=findViewById(R.id.my_name);
        name.setText(user.getDisplayName());
        TextView email=findViewById(R.id.my_email);
        email.setText(user.getEmail());
        reset=findViewById(R.id.resetPassword);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(my_Profile.this);
                progressDialog.setMessage("Sending Password Reset Email");
                progressDialog.show();

                auth.sendPasswordResetEmail(user.getEmail().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override

                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(my_Profile.this, "Check your Email to reset Password!", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(my_Profile.this,SignIN_Activity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(my_Profile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(my_Profile.this, "Store!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(my_Profile.this,StoreMe.class);
        startActivity(intent);
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}