package com.example.abdo.cinemaapp.Sign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdo.cinemaapp.MainActivity;
import com.example.abdo.cinemaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
// ...

    EditText email,password ,username;
    Button SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email= findViewById(R.id.SignUpEmail);
        password = findViewById(R.id.SignUpPassword);
        username = findViewById(R.id.SignUpUsername);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SignUp = findViewById(R.id.SignUpBtn2);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp(email.getText().toString(),password.getText().toString());

            }
        });

    }
    private void SignUp(final String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                         String uid=   mAuth.getCurrentUser().getUid();
                            mDatabase.child("Users").child(uid).child("Username").setValue(username.getText().toString());
                            mDatabase.child("Users").child(uid).child("Email").setValue(email);
                            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this,task.getException().getMessage().toString() ,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
