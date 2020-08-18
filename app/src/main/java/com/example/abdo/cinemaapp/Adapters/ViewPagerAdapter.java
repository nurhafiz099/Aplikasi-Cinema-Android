package com.example.abdo.cinemaapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.abdo.cinemaapp.Fragments.AddFragment;
import com.example.abdo.cinemaapp.Fragments.HomeFragment;
import com.example.abdo.cinemaapp.Fragments.ProfileFragment;
import com.example.abdo.cinemaapp.Fragments.RecommendFragment;
import com.example.abdo.cinemaapp.Fragments.SearchFragment;
import com.example.abdo.cinemaapp.Fragments.SettingsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i==0)
            return new HomeFragment();
        else if(i==1)
            return new SearchFragment();
        else if (i==2)
            return new ProfileFragment();
        else if (i==3)
            return new RecommendFragment();
        else if (i==4)
            return new AddFragment();
        else if (i==5)
            return new SettingsFragment();
        else
            return null;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
