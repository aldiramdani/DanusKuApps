package com.aikvanda.danuskuapps;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;
import android.widget.TextView;


import com.aikvanda.danuskuapps.Adapter.PagerAdapter;
import com.aikvanda.danuskuapps.Database.DatabaseHelper;
import com.aikvanda.danuskuapps.Jadwal.JadwalFragment;


public class DashboardMenu extends AppCompatActivity implements Interactor{
    TextView organisasi,acara;
    ImageView toProfile;
    private Menu fMenu = new Menu();
    private InputDana fDana = new InputDana();
    private JadwalFragment fjadwalFragment = new JadwalFragment();
    private ProfileAccount fprofileAccount = new ProfileAccount();
    View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        // Coba ini ngambil layout app_bar_main
        // Ketika Lyaout yang punya tools:context Auto bisa manggil element XMLnya

        insertregister();
        display();


        final ViewPager viewPager = (ViewPager) findViewById(R.id.content);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayout);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);
        pagerAdapter.AddFragment(fMenu);
        pagerAdapter.AddFragment(fDana);
        pagerAdapter.AddFragment(fjadwalFragment);
        pagerAdapter.AddFragment(fprofileAccount);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        rootView =
        toProfile = (ImageView) findViewById(R.id.edit);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        display();
    }

    private void insertregister() {


        DatabaseHelper mDbHelper=new DatabaseHelper(getApplicationContext());

        SQLiteDatabase db=mDbHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();


        contentValues.put(tabeldb.listtabeldb.COL_1,"");
        contentValues.put(tabeldb.listtabeldb.COL_2,"Upadate Profil mu ");
        contentValues.put(tabeldb.listtabeldb.COL_3,0);
        long newRow=db.insert(tabeldb.listtabeldb.TABLE_NAME,null,contentValues);
        if (newRow==-1){
            Toast.makeText(getApplicationContext(),"Username telah digunakan",Toast.LENGTH_LONG).show();
        }
    }

    private void display() {
        String query = "select * from " + tabeldb.listtabeldb.TABLE_NAME;
        Cursor c = null;

        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        c = db.rawQuery(query, null);
        organisasi = (TextView) findViewById(R.id.organisasi);
        acara = (TextView) findViewById(R.id.acara);

        if (c.moveToFirst()) {
            organisasi.setText(c.getString(1));
            acara.setText(c.getString(2));
        }
    }

    @Override
    public void update() {
        fMenu.displayall();
        display();
    }
}
