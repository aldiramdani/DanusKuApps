package com.aikvanda.danuskuapps.Adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aikvanda.danuskuapps.R;

import java.util.ArrayList;

/**
 * Created by Aulia Ikvanda Yoren
 * on 08/04/2018.
 */

public class InputDanaAdapter extends FragmentPagerAdapter {
    Context Mcontext;
    public InputDanaAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.Mcontext= context;
    }

    ArrayList<Fragment> list = new ArrayList<>();

    @Override
    public Fragment getItem(int position) {
        // jangan pake IF,
        // soalnya kalo pake IF itu kalo else ga memnuhi dilewati semua
        return list.get(position);
        /*
        switch (position) {
            case 0:
                return new PemasukanFragment();
            case 1:
                return new PengeluaranFragment();
            default:
                return null;
        }*/
    }

    public void addFragment(Fragment f){
        list.add(f);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return Mcontext.getString(R.string.pemasukan);
            case 1:
                return Mcontext.getString(R.string.pengeluaran);
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return list.size();
    }

}
