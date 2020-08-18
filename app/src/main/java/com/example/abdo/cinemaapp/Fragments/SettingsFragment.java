package com.example.abdo.cinemaapp.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.abdo.cinemaapp.MainActivity;
import com.example.abdo.cinemaapp.R;
import com.example.abdo.cinemaapp.Sign.SignActivity;
import com.example.abdo.cinemaapp.Sign.SignInActivity;
import com.example.abdo.cinemaapp.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    Button SignOut;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings, container, false);
        SignOut = v.findViewById(R.id.signOutButton);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                mAuth.signOut();
                startActivity(new Intent(getActivity(),SignActivity.class));
            }
        });
        return v;
    }

}
