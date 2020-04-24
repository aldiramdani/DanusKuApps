package com.aikvanda.danuskuapps.Jadwal;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;
import com.aikvanda.danuskuapps.Panitia.data;
import com.aikvanda.danuskuapps.R;
import com.aikvanda.danuskuapps.tabeldb;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class JumatFragment extends Fragment {

    List<data> array;
    View v ;

    public JumatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     v = inflater.inflate(R.layout.fragment_jumat, container, false);

     display();

     return v ;


    }
    private void display() {
        array = new ArrayList<>();
        array = getData();
        final ListView listdata = (ListView) v.findViewById(R.id.listdatajumat);
        listdata.setAdapter(new viewAdapter(getContext()));
    }

    public List<data> getData() {
        List<data> lis = new ArrayList<>();
        data child;
        Cursor c = null;
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            String query = "select * from "+ tabeldb.listtabeldb.TABLE_DATA+" where "+tabeldb.listtabeldb.HARI+" = 'Jumat'";
            c = db.rawQuery(query, null);
            while (c.moveToNext()) {
                child = new data(c.getString(c.getColumnIndex(tabeldb.listtabeldb.NAMA)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.KELAS)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.HARI)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.DIVISI)));
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

    public class viewAdapter extends BaseAdapter {
        LayoutInflater vi;

        public viewAdapter(Context context) {
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
                convertView = vi.inflate(R.layout.list_view_data, null);
            }
            final TextView Nama = (TextView) convertView.findViewById(R.id.nama);
            Nama.setText(array.get(position).getNama());
            final TextView divisi = (TextView) convertView.findViewById(R.id.divisi);
            divisi.setText(array.get(position).getJadwal());
            return convertView;
        }
    }

}
