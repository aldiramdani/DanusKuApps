package com.aikvanda.danuskuapps.Jadwal;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aikvanda.danuskuapps.MingguFragment;
import com.aikvanda.danuskuapps.R;
import com.aikvanda.danuskuapps.SabtuFragment;
import com.aikvanda.danuskuapps.SeninFragment;

/**
 * Created by Aulia Ikvanda Yoren
 * on 15/04/2018.
 */

public class JadwalAdapter extends FragmentPagerAdapter {

    Context Mcontext;
    public JadwalAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.Mcontext= context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SeninFragment();
            case 1:
                return new SelasaFragment();
            case 2:
                return new RabuFragment();
            case 3:
                return new KamisFragment();
            case 4:
                return new JumatFragment();
            case 5:
                return new SabtuFragment();
            case 6:
                return new MingguFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return Mcontext.getString(R.string.senin);
            case 1:
                return Mcontext.getString(R.string.selasa);
            case 2:
                return Mcontext.getString(R.string.rabu);
            case 3:
                return Mcontext.getString(R.string.kamis);
            case 4:
                return Mcontext.getString(R.string.jumat);
            case 5:
                return Mcontext.getString(R.string.sabtu);
            case 6:
                return Mcontext.getString(R.string.minggu);
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return 7;
    }
}
