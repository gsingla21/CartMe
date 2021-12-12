package com.example.foodpalace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Forgot_Password extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button sendEmail;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setTitle("Forgot Password?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText  emailtext=findViewById(R.id.etForgotEmail);
        sendEmail=findViewById(R.id.btnSendEmail);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailtext.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(Forgot_Password.this, "Email Required!", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(Forgot_Password.this, "Enter a Valid Email ID", Toast.LENGTH_SHORT).show();
                }
                else if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    progressDialog=new ProgressDialog(Forgot_Password.this);
                    progressDialog.setMessage("Sending Password Reset Email");
                    progressDialog.show();
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Forgot_Password.this, "Check your Email to reset Password!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Forgot_Password.this,SignIN_Activity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Forgot_Password.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }
    @Override
    public void onBackPressed() {
            Intent intent=new Intent(Forgot_Password.this,SignIN_Activity.class);
            startActivity(intent);
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}