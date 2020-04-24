package com.aikvanda.danuskuapps.Panitia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.aikvanda.danuskuapps.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class inputData extends AppCompatActivity {
    String [] hari  ={"Minggu","Senin","Selasa","Rabu","Kamis","Jum'at","Sabtu"};
    String [] divisi    = {"Inti","Acara","Logistik","Dana Usaha","Keamanan","PubDok","Humas","Dekorasi"};

    //Deklarasi
    Button save,reset;
    EditText namaEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        namaEdit = (EditText)findViewById(R.id.nama);
        //Java dari List Dropdown Start
        final ArrayAdapter<String> arrayHari=new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line,hari);
        MaterialBetterSpinner hr = (MaterialBetterSpinner)findViewById(R.id.listHari);
        hr.setAdapter(arrayHari);

        final ArrayAdapter<String> arrayDivisi=new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line,divisi);
        MaterialBetterSpinner div = (MaterialBetterSpinner)findViewById(R.id.listDivisi);
        div.setAdapter(arrayDivisi);

        //Java dari List Dropdown End

    }

}
