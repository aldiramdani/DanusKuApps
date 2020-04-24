package com.aikvanda.danuskuapps.Database;

/**
 * Created by Satria on 3/25/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aikvanda.danuskuapps.tabeldb;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static  final String DATABASE_BAME="Danusku.db";
    private static  final int DATABASE_VERSION=1;
    private static final String DROP_TABLE="DROP TABLE IF EXISTS" + tabeldb.listtabeldb.TABLE_DATA;


    public DatabaseHelper(Context context) {
        super(context,DATABASE_BAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String DATA_TABELNYA = "CREATE TABLE "+ tabeldb.listtabeldb.TABLE_DATA+" ("
                    + tabeldb.listtabeldb.ID_DATA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + tabeldb.listtabeldb.NAMA+" TEXT NOT NULL, "
                    + tabeldb.listtabeldb.KELAS+" TEXT NOT NULL, "
                    + tabeldb.listtabeldb.HARI+" TEXT NOT NULL, "
                    + tabeldb.listtabeldb.DIVISI+" TEXT NOT NULL);";
            db.execSQL(DATA_TABELNYA);
        }catch (Exception e){
            Log.i("error","database gagal");
        }
        try {
            String REGISTER_TABEL = "CREATE TABLE " + tabeldb.listtabeldb.TABLE_NAME + " ("
                    + tabeldb.listtabeldb.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + tabeldb.listtabeldb.COL_1 + " TEXT NOT NULL, "
                    + tabeldb.listtabeldb.COL_2 + " TEXT NOT NULL, "
                    + tabeldb.listtabeldb.COL_3 + " INTEGER);";
            db.execSQL(REGISTER_TABEL);
        }catch (Exception e){
                Log.i("error","database gagal");
            }

        try {
            String DATA_TABEL_PEMASUKAN = "CREATE TABLE " + tabeldb.listtabeldb.TABLE_NAME_Pemasukan + " ("
                    + tabeldb.listtabeldb.ID_PEMASUKAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + tabeldb.listtabeldb.COL_1_Pemasukan + " TEXT NOT NULL, "
                    + tabeldb.listtabeldb.COL_2_Pemasukan + " INTEHER, "
                    + tabeldb.listtabeldb.COL_3_Pemasukan + " TEXT);";
            db.execSQL(DATA_TABEL_PEMASUKAN);
        }catch (Exception e){
            Log.i("error","database gagal");
        }
        try {


            String DATA_TABEL_PENGELUARAN = "CREATE TABLE " + tabeldb.listtabeldb.TABLE_NAME_Pengeluaran + " ("
                    + tabeldb.listtabeldb.ID_PENGELUARAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + tabeldb.listtabeldb.COL_1_Pengeluaran + " TEXT NOT NULL, "
                    + tabeldb.listtabeldb.COL_2_Pengeluaran + " INTEGER, "
                    + tabeldb.listtabeldb.COL_3_Pengeluaran + " TEXT, "
                    + tabeldb.listtabeldb.COL_4_Pengeluaran + " BLOB);";
            db.execSQL(DATA_TABEL_PENGELUARAN);
        }catch (Exception e){
        Log.i("error","database gagal");
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       try{
           db.execSQL(DROP_TABLE);
           onCreate(db);
       }catch (Exception e){}
        Log.i("error bro","erorrr brroo");
    }




}
