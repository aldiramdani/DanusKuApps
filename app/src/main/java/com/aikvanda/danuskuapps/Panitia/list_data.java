package com.aikvanda.danuskuapps.Panitia;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;
import com.aikvanda.danuskuapps.R;
import com.aikvanda.danuskuapps.tabeldb;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class list_data extends AppCompatActivity {
    ArrayList<String> hari  = new ArrayList<String>() {{
        add("Hari");
        add("Minggu");
        add("Senin");
        add("Selasa");
        add("Rabu");
        add("Kamis");
        add("Jum'at");
        add("Sabtu");
    }};
    ArrayList<String> divisi  = new ArrayList<String>() {{
        add("Divisi");
        add("Inti");
        add("Acara");
        add("Logistik");
        add("Dana Usaha");
        add("Keamanan");
        add("PubDok");
        add("Humas");
        add("Dekorasi");
    }};
    String text,text1;
    DatabaseHelper mydb;
    Spinner hr;
    Spinner div;
    List<data> array;

    //Deklarasi
    Button save,reset;
    EditText namaEdit;
    EditText kelas;
    String stringNama,stringKelas,stringHari,stringDivisi;
    private FloatingActionButton fab;

    private static final String TAG = "list_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        FrameLayout container = (FrameLayout) findViewById(R.id.containerku);

        fab = (FloatingActionButton) findViewById(R.id.tambahData);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(list_data.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_input_data, null);
                namaEdit = (EditText)mView.findViewById(R.id.nama);
                kelas = (EditText)mView.findViewById(R.id.listKelas);
                ArrayAdapter<String> arrayHari=new ArrayAdapter<String>
                        (list_data.this, android.R.layout.simple_dropdown_item_1line,hari);
                hr = (Spinner) mView.findViewById(R.id.listHari);
                hr.setAdapter(arrayHari);
                ArrayAdapter<String> arrayDivisi=new ArrayAdapter<String>
                        (list_data.this, android.R.layout.simple_dropdown_item_1line,divisi);
                div = (Spinner)mView.findViewById(R.id.listDivisi);
                div.setAdapter(arrayDivisi);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                save = (Button) mView.findViewById(R.id.simpan);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stringNama= namaEdit.getText().toString();
                        stringKelas= kelas.getText().toString();
                        stringHari= hr.getSelectedItem().toString().trim();
                        stringDivisi= div.getSelectedItem().toString().trim();
                        if(TextUtils.isEmpty(stringNama)||TextUtils.isEmpty(stringNama)&& stringHari.equals("Hari")||stringDivisi.equals("Divisi")) {
                            Toast.makeText(getApplicationContext(),"Tidak boleh Dikosongkan",Toast.LENGTH_LONG).show();
                        }else
                        if(stringDivisi.equals("Divisi")) {
                            namaEdit.setError("Your message");
                            return;
                        }else{
                        insertData();
                        dialog.hide();
                        display();}
                    }
                });

                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    if(bundle.getString("some")!= null){
                        Toast.makeText(getApplicationContext(),
                                "data:"+bundle.getString("some"),
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            private void insertData() {
                String namaString=namaEdit.getText().toString().trim();
                String kelasString=kelas.getText().toString().trim();
                String hariString=hr.getSelectedItem().toString().trim();
                String divisiString=div.getSelectedItem().toString().trim();
                DatabaseHelper mDbHelper=new DatabaseHelper(getApplicationContext());

                SQLiteDatabase db=mDbHelper.getWritableDatabase();

                ContentValues contentValues=new ContentValues();
                contentValues.put(tabeldb.listtabeldb.NAMA,namaString);
                contentValues.put(tabeldb.listtabeldb.KELAS,kelasString);
                contentValues.put(tabeldb.listtabeldb.HARI,hariString);
                contentValues.put(tabeldb.listtabeldb.DIVISI,divisiString);
                long newRow=db.insert(tabeldb.listtabeldb.TABLE_DATA,null,contentValues);
                if (newRow==-1){
                    Toast.makeText(getApplicationContext(),"TIDAK TERINPUT",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"BERHASIL:"+newRow ,Toast.LENGTH_LONG).show();

                }


            }
        });

        View emptyView = findViewById(R.id.empty_view);



        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listviewData);
        listView.setEmptyView(emptyView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                editItem.setWidth(170);
                // set a icon
                editItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        Log.d(TAG, "onMenuItemClick: klik"+position);
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(list_data.this);
                        View mView = getLayoutInflater().inflate(R.layout.activity_input_data, null);
                        namaEdit = (EditText)mView.findViewById(R.id.nama);
                        kelas = (EditText)mView.findViewById(R.id.listKelas);
                        ArrayAdapter<String> arrayHari=new ArrayAdapter<String>
                                (list_data.this, android.R.layout.simple_dropdown_item_1line,hari);
                        hr = (Spinner) mView.findViewById(R.id.listHari);
                        hr.setAdapter(arrayHari);
                        ArrayAdapter<String> arrayDivisi=new ArrayAdapter<String>
                                (list_data.this, android.R.layout.simple_dropdown_item_1line,divisi);
                        div = (Spinner)mView.findViewById(R.id.listDivisi);
                        div.setAdapter(arrayDivisi);

                        namaEdit.setText(array.get(position).getNama());
                        kelas.setText(array.get(position).getKelas());
                        Log.i(TAG, "onMenuItemClick: "  + array.get(position).getDivisi());
                        hr.setSelection(hari.indexOf(array.get(position).getDivisi()));
                        div.setSelection(divisi.indexOf(array.get(position).getJadwal()));

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        save = (Button) mView.findViewById(R.id.simpan);
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                update();
                                stringNama= namaEdit.getText().toString().trim();
                                stringKelas= kelas.getText().toString().trim();
                                stringHari= hr.getSelectedItem().toString().trim();
                                stringDivisi= div.getSelectedItem().toString().trim();
                                if(TextUtils.isEmpty(stringNama)||TextUtils.isEmpty(stringNama)&& stringHari.equals("Hari")||stringDivisi.equals("Divisi")) {
                                    Toast.makeText(getApplicationContext(),"Tidak boleh Dikosongkan",Toast.LENGTH_LONG).show();
                                }else
                                if(stringDivisi.equals("Divisi")) {
                                    namaEdit.setError("Your message");
                                    return;
                                }else{
                                dialog.hide();
                                update(array.get(position).getId());
                                display();}
                            }
                        });
                        break;
                    case 1:
                        // delete
                        Log.d(TAG, "onMenuItemClick: klik"+position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(list_data.this);
                        builder.setMessage("Data Yang sudah dihapus tidak dapat dikembalikan lagi. Apakah Anda Yakin ?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked the "Delete" button, so delete the pet.
                                delete(array.get(position).getId());
                                display();
                            }
                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked the "Cancel" button, so dismiss the dialog
                                // and continue editing the pet.
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }


    private void delete( int id) {
        DatabaseHelper mDbHelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        String where = "_ID_DATA=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        ContentValues contentValues=new ContentValues();
        long newRow=db.delete(tabeldb.listtabeldb.TABLE_DATA,where,whereArgs);
        if (newRow==-1){
            Toast.makeText(getApplicationContext(),"Gagal Delete",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Berhasil Delete:"+newRow ,Toast.LENGTH_LONG).show();

        }
    }

    private void update(int student) {
        String namaString=namaEdit.getText().toString().trim();
        String kelasString=kelas.getText().toString().trim();
        String hariString=hr.getSelectedItem().toString().trim();
        String divisiString=div.getSelectedItem().toString().trim();
        DatabaseHelper mDbHelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        String where = "_ID_DATA=?";
        String[] whereArgs = new String[] {String.valueOf(student)};
        ContentValues contentValues=new ContentValues();
        contentValues.put(tabeldb.listtabeldb.NAMA,namaString);
        contentValues.put(tabeldb.listtabeldb.KELAS,kelasString);
        contentValues.put(tabeldb.listtabeldb.HARI,hariString);
        contentValues.put(tabeldb.listtabeldb.DIVISI,divisiString);
        long newRow=db.update(tabeldb.listtabeldb.TABLE_DATA,contentValues,where,whereArgs);
        if (newRow==-1){
            Toast.makeText(getApplicationContext(),"Gagal Update",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Berhasil Update:"+newRow ,Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onStart() {
        display();
        super.onStart();
    }
    private void display(){
        array = new ArrayList<>();
        array = getData();
        final SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listviewData);
        listView.setAdapter(new viewAdapter(getApplicationContext()));
    }

    public List<data> getData(){
        List<data> lis=new ArrayList<>();
        data child;
        Cursor c=null;
        DatabaseHelper mDbHelper=new DatabaseHelper(getApplicationContext());

        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        try{
            String query="select * from "+tabeldb.listtabeldb.TABLE_DATA;
            c=db.rawQuery(query,null);
            while (c.moveToNext()){

                child=new data(c.getString(c.getColumnIndex(tabeldb.listtabeldb.NAMA)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.KELAS)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.HARI)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.DIVISI)));
                child.setId(c.getInt(c.getColumnIndex(tabeldb.listtabeldb.ID_DATA)));
                lis.add(child);

            }
            db.close();
            return lis;
        }finally {
            if (c!=null){
                c.close();
            }if (db!=null){
                db.close();
            }
        }

    }

    public class viewAdapter extends BaseAdapter {
        LayoutInflater vi;

        public viewAdapter(Context context){
            vi=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return getData().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView==null){
                convertView=vi.inflate(R.layout.list_view_data,null);
            }
            final TextView Nama=(TextView)convertView.findViewById(R.id.nama);
            Nama.setText(array.get(position).getNama());
            final TextView divisi=(TextView)convertView.findViewById(R.id.divisi);
            divisi.setText(array.get(position).getJadwal());
            return convertView;
        }

    }





}
