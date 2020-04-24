package com.aikvanda.danuskuapps.Jadwal;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aikvanda.danuskuapps.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalFragment extends Fragment {


    public JadwalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_jadwal, container, false);

        TabLayout tabLayout = (TabLayout)v.findViewById(R.id.InputJadwal);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.contentPager);
        JadwalAdapter adapter = new JadwalAdapter(getContext(), getChildFragmentManager());
        // Pakenya get Context soalnya dia ada di fragment, dan kebutuhannya bukan activity tapi context
        // kalo di activity kelebihannya semuanya pake this

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return v;

    }

}
