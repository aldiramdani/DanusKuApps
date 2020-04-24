package com.aikvanda.danuskuapps;


import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aikvanda.danuskuapps.Adapter.InputDanaAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputDana extends Fragment implements Interactor{

    private Interactor mInteractor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_dana, container, false);

        PemasukanFragment pF = new PemasukanFragment();
        PengeluaranFragment pengF = new PengeluaranFragment();

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.InputDana);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.contentPager);
        InputDanaAdapter adapter = new InputDanaAdapter(getContext(), getChildFragmentManager());
        // Pakenya get Context soalnya dia ada di fragment, dan kebutuhannya bukan activity tapi context
        // kalo di activity kelebihannya semuanya pake this
        adapter.addFragment(pF);
        adapter.addFragment(pengF);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractor = (Interactor)context;
    }

    @Override
    public void update() {
        mInteractor.update();
    }
}
