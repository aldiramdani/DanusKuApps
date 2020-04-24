package com.aikvanda.danuskuapps.Adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.aikvanda.danuskuapps.R;

import java.util.ArrayList;

/**
 * Created by Aulia Ikvanda Yoren
 * on 07/04/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    Context noOfTabs;

    ArrayList<Fragment> listOfFragment = new ArrayList<>();

    public PagerAdapter(FragmentManager fm , Context NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    public void AddFragment(Fragment f){
        listOfFragment.add(f);
    }

    @Override
    public Fragment getItem(int position) {
        return listOfFragment.get(position);
        /*
        if (position == 0) {
            return new Menu();
        } else if(position==1) {
            return new InputDana();
        }else if(position==2){
            return new JadwalFragment();
        }else{
            return new ProfileAccount();
        }
        */
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return noOfTabs.getString(R.string.menu);
        } else if (position == 1) {
            return noOfTabs.getString(R.string.inputDana);
        }else if (position == 2){
            return noOfTabs.getString(R.string.jadwal);
        } else if(position == 3 ){
            return noOfTabs.getString(R.string.profile);
        }
        else {return null;}
    }

        @Override
        public int getCount () {
            return listOfFragment.size();
        }
    }








