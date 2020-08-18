package com.example.abdo.cinemaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.abdo.cinemaapp.Sign.SignActivity;
import com.example.abdo.cinemaapp.Sign.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setContentView(R.layout.activity_start);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                mAuth = FirebaseAuth.getInstance();
                if (!pref.contains("email")) {
                    startActivity(new Intent(StartActivity.this, SignActivity.class));
                }
                else
                {
                    String tempEmail = pref.getString("email","");
                    String tempPassword = pref.getString("password","");
                    SignIn(tempEmail,tempPassword);
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }
                    finish();

            }
        }.start();

    }
    private void SignIn(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(StartActivity.this,MainActivity.class));
                            finishAffinity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StartActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
