package com.example.abdo.cinemaapp;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.abdo.cinemaapp.Adapters.ViewPagerAdapter;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager1;
    TabLayout tab1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager1 = findViewById(R.id.ViewPager1);
        tab1 = findViewById(R.id.TabLayout1);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager1.setAdapter(adapter);
        tab1.setupWithViewPager(viewPager1);
        tab1.getTabAt(0).setIcon(R.drawable.fire);
        tab1.getTabAt(1).setIcon(R.drawable.search);
        tab1.getTabAt(2).setIcon(R.drawable.user);
        tab1.getTabAt(3).setIcon(R.drawable.recommended);
        tab1.getTabAt(4).setIcon(R.drawable.add);
        tab1.getTabAt(5).setIcon(R.drawable.settings);
          MyFirebaseInstanceIDService a = new MyFirebaseInstanceIDService();
          a.onTokenRefresh();
    }

}
