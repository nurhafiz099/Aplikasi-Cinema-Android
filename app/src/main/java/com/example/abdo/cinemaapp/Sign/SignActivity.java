package com.example.abdo.cinemaapp.Sign;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.abdo.cinemaapp.R;

public class SignActivity extends AppCompatActivity {
   Button SignIn,SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

          SignIn = findViewById(R.id.SignInBtn1);
          SignUp = findViewById(R.id.SignUpBtn1);
          SignIn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(SignActivity.this,SignInActivity.class));
              }
          });
          SignUp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(SignActivity.this,SignUpActivity.class));
              }
          });
    }
}
