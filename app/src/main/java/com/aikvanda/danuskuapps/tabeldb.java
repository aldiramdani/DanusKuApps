package com.aikvanda.danuskuapps;

import android.provider.BaseColumns;
import android.net.Uri;
import android.content.ContentResolver;

/**
 * Created by Satria on 3/25/2018.
 */

public final class tabeldb {

    public static final String CONTENT_AUTHORITY = "com.aikvanda.danuskuapps";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_DANUS = "Danusku.db";



    private tabeldb(){

    }
    public static final class listtabeldb implements BaseColumns{

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DANUS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DANUS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DANUS;


        public static  final  String DATABASE_NAME = "Danusku.db";
        public static  final  String TABLE_NAME = "Register_table";
        public static  final  String COL_1 = "Organisasi";
        public static  final  String COL_2 = "Acara";
        public static  final  String COL_3 = "TotalPemasukan";


        public static  final  String TABLE_DATA = "DATA";
        public static  final  String ID_DATA = "_ID_DATA";
        public static  final  String NAMA = "Nama";
        public static  final  String KELAS = "Kelas";
        public static  final  String HARI = "Hari";
        public static  final  String DIVISI = "Divisi";
        public static  final  String ID = "ID";


        public static  final  String TABLE_NAME_Pemasukan = "Pemasukan";
        public static  final  String ID_PEMASUKAN = "_id";
        public static  final  String COL_1_Pemasukan = "Tanggal";
        public static  final  String COL_2_Pemasukan = "Total";
        public static  final  String COL_3_Pemasukan = "Catatan";

        public static  final  String TABLE_NAME_Pengeluaran = "Pengeluaran";
        public static  final  String ID_PENGELUARAN = "ID_PENGELUARAN";
        public static  final  String COL_1_Pengeluaran = "Tanggal_pengeluaran";
        public static  final  String COL_2_Pengeluaran = "Total_pengeluaran";
        public static  final  String COL_3_Pengeluaran = "Catatan_pengeluaran";
        public static  final  String COL_4_Pengeluaran = "Nota_pengeluaran";


    }
}
