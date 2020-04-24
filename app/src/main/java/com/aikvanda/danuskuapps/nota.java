package com.aikvanda.danuskuapps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;

import java.util.ArrayList;

public class nota extends AppCompatActivity {


    ArrayList<listnota> listnotaitem;
    notaadapter adapter = null;
    ListView listdata;
    ImageView showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        display();

    }

    @Override
    protected void onStart() {

        display();
        listdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(nota.this);
                View mView = getLayoutInflater().inflate(R.layout.showimage,
                        null);
                showImage = (ImageView)mView.findViewById(R.id.showImage);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                byte[] notaImage = listnotaitem.get(position).getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(notaImage, 0, notaImage.length);
                showImage.setImageBitmap(bitmap);
            }
        });
        super.onStart();
    }

    private void display() {
        listnotaitem= new ArrayList<>();
        listnotaitem = getData();
        listdata = (ListView) findViewById(R.id.carddView);
        listdata.setAdapter(new viewAdapterkeluar(getApplicationContext()));
        }
    public ArrayList<listnota> getData() {
        ArrayList<listnota> lis = new ArrayList<>();
        listnota child;
        Cursor c = null;
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            String query = "select * from " + tabeldb.listtabeldb.TABLE_NAME_Pengeluaran;
            c = db.rawQuery(query, null);
            while (c.moveToNext()) {
                child = new listnota(
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_1_Pengeluaran)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_2_Pengeluaran)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_3_Pengeluaran)),
                        c.getBlob(c.getColumnIndex(tabeldb.listtabeldb.COL_4_Pengeluaran)));
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

    public class viewAdapterkeluar extends BaseAdapter {
        LayoutInflater vi;

        public viewAdapterkeluar(Context context) {
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
                convertView = vi.inflate(R.layout.list_item_nota, null);
            }

            listnota nota = listnotaitem.get(position);
            final TextView tanggal = (TextView) convertView.findViewById(R.id.tanggalnota);
            tanggal.setText(listnotaitem.get(position).getTanggal());
            final TextView jumlah = (TextView) convertView.findViewById(R.id.jumlahNota);
            jumlah.setText(listnotaitem.get(position).getJumlahnota());
            final TextView keterangan = (TextView) convertView.findViewById(R.id.keterangan);
            keterangan.setText(listnotaitem.get(position).getKeterangannota());
            final ImageView imageview = (ImageView) convertView.findViewById(R.id.imgNota);
            byte[] notaImage = nota.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(notaImage, 0, notaImage.length);
            imageview.setImageBitmap(bitmap);
            return convertView;
        }


    }

}
