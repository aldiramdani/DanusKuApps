package com.aikvanda.danuskuapps;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aikvanda.danuskuapps.About.AboutUsActivity;
import com.aikvanda.danuskuapps.Database.DatabaseHelper;
import com.aikvanda.danuskuapps.Panitia.list_data;

import org.json.JSONObject;

import java.io.InputStream;
import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {

    DatabaseHelper mDbHelper;
    TextView currentdana, edittarget, progres, develope,toProfile;
    ProgressBar bar;
    double pemasukan, pengeluaran ,total, target;
    private static final String TAG = "Menu";

    View view;

    public Menu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        displayall();

        CardView cardView = (CardView) view.findViewById(R.id.nota);
        CardView cardView1 = (CardView) view.findViewById(R.id.laporan);
        CardView cardView2 = (CardView) view.findViewById(R.id.panitia);
        CardView cardView3 = (CardView) view.findViewById(R.id.aboutUs);


        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                Menu.this.startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), laporan.class);
                Menu.this.startActivity(intent);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), nota.class);
                Menu.this.startActivity(intent);

            }
        });


        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), list_data.class);
                intent.putExtra("same", "data");
                Menu.this.startActivity(intent);
            }
        });




        return view;
    }



    public void displayall() {
        getJson();
        display();
        display1();
        display3();
        total = pemasukan - pengeluaran;
        currentdana = (TextView) view.findViewById(R.id.currentdana);
        DecimalFormat decim = new DecimalFormat("#,###.##");
        currentdana.setText(decim.format(total));
        edittarget = (TextView) view.findViewById(R.id.target);
        edittarget.setText(decim.format(target));
        double persen = (total / target)*100;
        Log.i(TAG,""+persen);
        Log.i(TAG,""+total);
        Log.i(TAG,""+target);
        bar = (ProgressBar) view.findViewById(R.id.barprogres);
        bar.setProgress((int)persen);
        progres = (TextView)view.findViewById(R.id.progres);
        progres.setText((int)persen+"%");

    }




    private void display() {

        Cursor cursor;
        String query = "SELECT SUM( " + tabeldb.listtabeldb.COL_2_Pemasukan + ") FROM " + tabeldb.listtabeldb.TABLE_NAME_Pemasukan;
        mDbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        pemasukan = cursor.getDouble(0);
    }

    private void display1() {

        Cursor cursor;
        String query = "SELECT SUM( " + tabeldb.listtabeldb.COL_2_Pengeluaran + ") FROM " + tabeldb.listtabeldb.TABLE_NAME_Pengeluaran;
        mDbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        pengeluaran = cursor.getDouble(0);
    }

    private void display3() {
        Cursor cursor;
        String query = "select TotalPemasukan from " + tabeldb.listtabeldb.TABLE_NAME;
        mDbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        target = cursor.getInt(0);
    }

    public void getJson() {
        String json;
        try {
            InputStream is = getContext().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            String developed = jsonObject.getString("Developed");
            develope = (TextView) view.findViewById(R.id.develop);
            develope.setText(developed);
        } catch (Exception e) {

        }

    }
}


