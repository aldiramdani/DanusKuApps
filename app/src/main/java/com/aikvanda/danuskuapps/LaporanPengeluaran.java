package com.aikvanda.danuskuapps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LaporanPengeluaran extends Fragment {
    List<list> arraykita;
    TextView total;
    DatabaseHelper mDbHelper;

    View v;
    public LaporanPengeluaran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_laporan_pengeluaran, container, false);
        display();

        return v;
    }

    private void display() {
        arraykita = new ArrayList<>();
        arraykita = getData();
        final ListView listdata = (ListView) v.findViewById(R.id.listlaporanpengeluaran);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header, listdata, false);
        listdata.addHeaderView(headerView);
        ViewGroup footerView = (ViewGroup) getLayoutInflater().inflate(R.layout.footerpengeluaran, listdata, false);
        total = (TextView) footerView.findViewById(R.id.totalkeluar);
        Cursor cursor;
        String query = "SELECT SUM( " + tabeldb.listtabeldb.COL_2_Pengeluaran+ ") FROM " + tabeldb.listtabeldb.TABLE_NAME_Pengeluaran;
        mDbHelper=new DatabaseHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            DecimalFormat decim = new DecimalFormat("#,###.##");
            total.setText(decim.format(cursor.getDouble(0)));
        }
        listdata.addFooterView(footerView);
        listdata.setAdapter(new viewAdaptermasuk(getContext()));
    }

    public List<list> getData() {
        List<list> lis = new ArrayList<>();
        list child;
        Cursor c = null;
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            String query = "select * from " + tabeldb.listtabeldb.TABLE_NAME_Pengeluaran;
            c = db.rawQuery(query, null);
            while (c.moveToNext()) {
                child = new list(
                        c.getDouble(c.getColumnIndex(tabeldb.listtabeldb.COL_2_Pengeluaran)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_1_Pengeluaran)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_3_Pengeluaran)));
                lis.add(child);

            }
            db.close();
            return lis;
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }

    }

    public class viewAdaptermasuk extends BaseAdapter {
        LayoutInflater vi;

        public viewAdaptermasuk(Context context) {
            vi = LayoutInflater.from(context);
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

            if (convertView == null) {
                convertView = vi.inflate(R.layout.rowlayout, null);
            }

            DecimalFormat decim = new DecimalFormat("#,###.##");
            final TextView pemasukan = (TextView) convertView.findViewById(R.id.jumlah);
            pemasukan.setText(decim.format(arraykita.get(position).getDana()));
            final TextView tanggal = (TextView) convertView.findViewById(R.id.tanggallap);
            tanggal.setText(arraykita.get(position).getTanggal());
            final TextView keterangan = (TextView) convertView.findViewById(R.id.keteranganlap);
            keterangan.setText(arraykita.get(position).getKeterangan());
            return convertView;
        }


    }

}
