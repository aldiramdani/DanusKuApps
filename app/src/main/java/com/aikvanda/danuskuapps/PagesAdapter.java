package com.aikvanda.danuskuapps;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Satria on 4/9/2018.
 */

public class PagesAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagesAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                LaporanPemasukan tab1 = new LaporanPemasukan();
                return tab1;
            case 1:
                LaporanPengeluaran tab2 = new LaporanPengeluaran();
                return  tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}