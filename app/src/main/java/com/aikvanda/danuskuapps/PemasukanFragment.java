package com.aikvanda.danuskuapps;


import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.net.Uri;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class PemasukanFragment extends Fragment  implements
LoaderManager.LoaderCallbacks<Cursor>
{

    private static final int PET_LOADER = 0;

    /** Adapter for the ListView */
    PemasukanCursorAdapter mCursorAdapter;

    private FloatingActionButton fab;
    Button save;
    EditText jumlahMasuk, NoteMasuk;
    TextView tampilTgl;
    DatabaseHelper mydb;
    ImageButton setTgl;
    DatePickerDialog tgl;
    SimpleDateFormat simpleDateFormat;
    String tanggal,strjumlah,strketerangan;
    ListView listview ;
    View rootView;
    double number;
    private static final String TAG = "PemasukanFragment";
    private static final int MY_LOADER=0;
    ImageView hapus;

    Interactor mInteractor;

    public PemasukanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractor = (Interactor)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       rootView = inflater.inflate(R.layout.fragment_pemasukan, container, false);
        View emptyView = rootView.findViewById(R.id.empty_view);
        listview = (ListView)rootView.findViewById(R.id.listPemasukan);
        listview.setEmptyView(emptyView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, final long pos) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.edit_pemasukan,
                        null);

                hapus = (ImageView)mView.findViewById(R.id.hapuspemasukan1);

                jumlahMasuk = (EditText)mView.findViewById(R.id.pemasukan);
                jumlahMasuk.addTextChangedListener(new ThousandNumberEditText(jumlahMasuk));
                NoteMasuk = (EditText)mView.findViewById(R.id.editNoteMasuk);
                tampilTgl=(TextView)mView.findViewById(R.id.tampilTglMasuk);
                setTgl=(ImageButton)mView.findViewById(R.id.setTglMasuk);
                final Calendar c = Calendar.getInstance();

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                setTgl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog tgl = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                tampilTgl.setText(i + " - " + (i1 + 1) + " - " + i2);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                        tgl.show();
                    }
                });

                Uri currentUri = ContentUris.withAppendedId(tabeldb.listtabeldb.CONTENT_URI,pos );
                getUpdate(pos);

                // Set the URI on the data field of the intent
                ;

                hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Data Yang sudah dihapus tidak dapat dikembalikan lagi. Apakah Anda Yakin ?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked the "Delete" button, so delete the pet.
                                deletePet(pos);
                                mInteractor.update();
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
                        dialog.hide();
                    }
                });


                save = (Button) mView.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tanggal = tampilTgl.getText().toString().trim();
                        strjumlah = jumlahMasuk.getText().toString().trim();
                        strketerangan = NoteMasuk.getText().toString().trim();
                        if(TextUtils.isEmpty(tanggal)||TextUtils.isEmpty(strjumlah)||TextUtils.isEmpty(strketerangan)) {
                            Toast.makeText(getContext(),"Tidak boleh Dikosongkan",Toast.LENGTH_LONG).show();
                        }else {
                            updatePet(pos);
                            mInteractor.update();
                            dialog.hide();

                        }

                    }
                });
            }
        });
        mCursorAdapter = new PemasukanCursorAdapter(getContext(), null);
        listview.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(MY_LOADER, null,this);


        fab = (FloatingActionButton) rootView.findViewById(R.id.tambahPemasukan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.activity_pemasukan,
                        null);
                jumlahMasuk = (EditText)mView.findViewById(R.id.pemasukan);
                jumlahMasuk.addTextChangedListener(new ThousandNumberEditText(jumlahMasuk));
                NoteMasuk = (EditText)mView.findViewById(R.id.editNoteMasuk);

                //Calender Java Start
                tampilTgl=(TextView)mView.findViewById(R.id.tampilTglMasuk);
                setTgl=(ImageButton)mView.findViewById(R.id.setTglMasuk);
                final Calendar c = Calendar.getInstance();

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                setTgl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog tgl = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                tampilTgl.setText(i + " - " + (i1 + 1) + " - " + i2);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                        tgl.show();
                    }
                });
                save = (Button) mView.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            tanggal = tampilTgl.getText().toString().trim();
                            strjumlah = jumlahMasuk.getText().toString().trim();
                            strketerangan = NoteMasuk.getText().toString().trim();
                            if(TextUtils.isEmpty(tanggal)||TextUtils.isEmpty(strjumlah)||TextUtils.isEmpty(strketerangan)) {
                                Toast.makeText(getContext(),"Tidak boleh Dikosongkan",Toast.LENGTH_LONG).show();
                            }else {
                                insertPet();
                                mInteractor.update();
                                dialog.hide();
                        }

                    }
                });
            }
        });




        return rootView;
    }
    private void getUpdate(long id){
        String sql="Select * from "+tabeldb.listtabeldb.TABLE_NAME_Pemasukan+" where "+tabeldb.listtabeldb._ID+" ="+id;
        Cursor c = null;

        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        c = db.rawQuery(sql, null);


        if (c.moveToFirst()) {
            tampilTgl.setText(c.getString(1));
            number = c.getDouble(2);
            String str = NumberFormat.getNumberInstance(Locale.US).format(number);
            jumlahMasuk.setText(str);
            NoteMasuk.setText(c.getString(3));
        }

    }
    private void insertPet() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        String a = jumlahMasuk.getText().toString().replace(",","").replace(".","");
        int b ;
        b= Integer.parseInt(a);
        values.put(tabeldb.listtabeldb.COL_1_Pemasukan, tampilTgl.getText().toString().trim());
        values.put(tabeldb.listtabeldb.COL_2_Pemasukan, b  );
        values.put(tabeldb.listtabeldb.COL_3_Pemasukan, NoteMasuk.getText().toString().trim());
        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContext().getContentResolver().insert(tabeldb.listtabeldb.CONTENT_URI, values);
        if (newUri==null){
            Toast.makeText(getContext(),"erorr",Toast.LENGTH_LONG).show();
        }
    }

    private void deletePet(long id) {
        String where = "_id=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        int newUri = getContext().getContentResolver().delete(tabeldb.listtabeldb.CONTENT_URI, where,whereArgs);
        if (newUri==0){
            Toast.makeText(getContext(),"erorr",Toast.LENGTH_LONG).show();
        }
    }


    private void updatePet(long id) {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        int i= Integer.parseInt(jumlahMasuk.getText().toString().replace(",","").replace(".",""));
        values.put(tabeldb.listtabeldb.COL_1_Pemasukan, tampilTgl.getText().toString().trim());
        values.put(tabeldb.listtabeldb.COL_2_Pemasukan, i);
        values.put(tabeldb.listtabeldb.COL_3_Pemasukan, NoteMasuk.getText().toString().trim());

        String where = "_id=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        int newUri=getContext().getContentResolver().update(tabeldb.listtabeldb.CONTENT_URI,values,where,whereArgs);
        if (newUri == 0 ){
            Toast.makeText(getContext(),"erorr",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {
                tabeldb.listtabeldb.ID_PEMASUKAN,
                tabeldb.listtabeldb.COL_1_Pemasukan,
                tabeldb.listtabeldb.COL_2_Pemasukan,
                tabeldb.listtabeldb.COL_3_Pemasukan
        };
        return new CursorLoader(getContext(),   // Parent activity context
                tabeldb.listtabeldb.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }


}
