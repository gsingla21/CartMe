package com.example.foodpalace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodpalace.Models.Users;
import com.example.foodpalace.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIN_Activity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database;
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressDialog=new ProgressDialog(SignIN_Activity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging Account.");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            if(auth.getCurrentUser().isEmailVerified())
                            {
                                Intent intent=new Intent(SignIN_Activity.this,RecyclerView_main.class);

                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignIN_Activity.this, "Email not Verified!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(SignIN_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.etGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        binding.etBackSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(SignIN_Activity.this, "Sign Up!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignIN_Activity.this,Signup_Activity.class);
                startActivity(intent);
            }
        });
        binding.etForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignIN_Activity.this, "Forgot Password!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignIN_Activity.this,Forgot_Password.class);
                startActivity(intent);
            }
        });
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null)
        {
            if(user.isEmailVerified())
            {
                Intent intent=new Intent(SignIN_Activity.this,RecyclerView_main.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(SignIN_Activity.this, "Email not Verified!!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    int RC_SIGN_IN=65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user=auth.getCurrentUser();
                            Users users=new Users();
                            users.setUserid(user.getUid());
                            users.setUsername(user.getDisplayName());
                            users.setProfilepic(user.getPhotoUrl().toString());

                            if(user.isEmailVerified())
                            {
                                database.getReference().child("Users").child(user.getUid()).setValue(users);
                                Intent intent=new Intent(SignIN_Activity.this,RecyclerView_main.class);
                                startActivity(intent);
                                Toast.makeText(SignIN_Activity.this, "SignIn with Google!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignIN_Activity.this, "Verify your Email!", Toast.LENGTH_SHORT).show();
                            }

                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignIN_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(binding.getRoot(),"Authentication Failed!",Snackbar.LENGTH_SHORT);
                           // updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if(auth.getCurrentUser()!=null)
        {
            Toast.makeText(SignIN_Activity.this, "Exit!", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
        else
        {
            Intent intent=new Intent(SignIN_Activity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}