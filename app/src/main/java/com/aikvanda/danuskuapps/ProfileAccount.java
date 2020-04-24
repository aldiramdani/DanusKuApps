package com.aikvanda.danuskuapps;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileAccount extends Fragment {
    View v ;
    private static final String TAG ="profile" ;
    DatabaseHelper mDbHelper;
    Button update;
    EditText organisasi,acara,target;
    Interactor mInteractor;
    double number;
    public ProfileAccount() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractor = (Interactor)context;
    }

    @Override
    public void onStart() {
        super.onStart();
        display();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile_account, container, false);

        organisasi = (EditText) v.findViewById(R.id.editorganisasi);
        acara = (EditText) v.findViewById(R.id.editacara);
        target = (EditText)v.findViewById(R.id.editTargetDana);
        target.addTextChangedListener(new ThousandNumberEditText(target));

        update =(Button)v.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(1);
                mInteractor.update();
                display();

            }
        });

        return v;
    }


    private void display() {
        String query="select * from "+tabeldb.listtabeldb.TABLE_NAME;
        Cursor c=null;

        DatabaseHelper mDbHelper=new DatabaseHelper(getContext());
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        c = db.rawQuery(query, null);
        Log.i(TAG, "display: "+c);
        if (c.moveToFirst()) {
            organisasi.setText(c.getString(1));
            acara.setText(c.getString(2));
            number = c.getDouble(3);
            String str = NumberFormat.getNumberInstance(Locale.US).format(number);
            target.setText(str);
        }

    }

    private void update(int id) {
        String organString=organisasi.getText().toString().trim();
        String acaraString=acara.getText().toString().trim();



        DatabaseHelper mDbHelper=new DatabaseHelper(getContext());

        SQLiteDatabase db=mDbHelper.getWritableDatabase();

        String where = "ID=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        ContentValues contentValues=new ContentValues();

        double i= Double.parseDouble(target.getText().toString().replace(",","").replace(".",""));
        contentValues.put(tabeldb.listtabeldb.COL_1,organString);
        contentValues.put(tabeldb.listtabeldb.COL_2,acaraString);
        contentValues.put(tabeldb.listtabeldb.COL_3,i);
        long newRow=db.update(tabeldb.listtabeldb.TABLE_NAME,contentValues,where,whereArgs);
        if (newRow==-1){
            Toast.makeText(getContext(),"Gagal Update",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"Berhasil Update:"+newRow ,Toast.LENGTH_LONG).show();
        }
    }


}
