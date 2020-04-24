package com.aikvanda.danuskuapps;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aikvanda.danuskuapps.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PengeluaranFragment extends Fragment {

    private FloatingActionButton fab;
    private TextView tampilTgl;
    private ImageButton setTgl;
    EditText jumlahKeluar, catatanKeluar;
    DatabaseHelper db;
    ImageView imageView;
    Button save;
    ImageButton opGallery, opCamera;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    List<dataPengeluaran> arraykeluar;
    private static final String TAG = "PengeluaranFragment";
    private View mView;
    private View mView2;
    private View v;
    ListView listView;
    ImageView hapus;
    Interactor mInteractor;
    String stringTanggal,stringJumlah,stringKeterangan;
    double number ;

    public PengeluaranFragment() {
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
         v =  inflater.inflate(R.layout.fragment_pengeluaran, container, false);

    // tampil
        display();


    // Edit dan Delete

        View emptyView = v.findViewById(R.id.empty_view);
        listView = (ListView)v.findViewById(R.id.listviewPengeluaran);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                mView = getLayoutInflater().inflate(R.layout.edit_item_pengeluaran, null);
                jumlahKeluar = mView.findViewById(R.id.jumlahKeluar);
                jumlahKeluar.addTextChangedListener(new ThousandNumberEditText(jumlahKeluar));
                catatanKeluar = mView.findViewById(R.id.catatanKeluar);
                //Calender Java Start
                tampilTgl = mView.findViewById(R.id.tampilTglKeluar);
                setTgl = mView.findViewById(R.id.setTglKeluar);
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
                //Open Gallery Java Intent Start
                imageView = mView.findViewById(R.id.tampilFotokita);
                opGallery = mView.findViewById(R.id.bukaGallery);

                opGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });
                //Open Gallery Java Intent End

                opCamera = mView.findViewById(R.id.bukaCamera);
                opCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
                });
                opCamera = mView.findViewById(R.id.bukaCamera);
                opCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
                });

                tampilTgl.setText(arraykeluar.get(position).getTanggalkeluar());
                number = arraykeluar.get(position).getDanakeluar();
                String str = NumberFormat.getNumberInstance(Locale.US).format(number);
                jumlahKeluar.setText(str );
                byte[] notaImage = arraykeluar.get(position).getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(notaImage, 0, notaImage.length);
                imageView.setImageBitmap(bitmap);
                Log.i(TAG, "onMenuItemClick: ");

                save = mView.findViewById(R.id.simpan);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stringTanggal= tampilTgl.getText().toString().trim();
                        stringJumlah = jumlahKeluar.getText().toString().trim();
                        stringKeterangan = catatanKeluar.getText().toString().trim();
                        if(TextUtils.isEmpty(stringTanggal)||TextUtils.isEmpty(stringJumlah)||TextUtils.isEmpty(stringKeterangan)) {
                            Toast.makeText(getContext(),"Tidak boleh Dikosongkan",Toast.LENGTH_LONG).show();
                        }else {
                            update(arraykeluar.get(position).getId());
                            mInteractor.update();
                            dialog.hide();
                            display();
                        }



                    }
                });
                hapus = (ImageView)mView.findViewById(R.id.hapus);
                hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Data Yang sudah dihapus tidak dapat dikembalikan lagi. Apakah Anda Yakin ?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked the "Delete" button, so delete the pet.
                                delete(arraykeluar.get(position).getId());
                                mInteractor.update();
                                dialog.dismiss();
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
                        dialog.hide();

                    }
                });

            }
        });

        // Modifikasi tombol Add
        fab = v.findViewById(R.id.tambahPengeluaran);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                mView = getLayoutInflater().inflate(R.layout.activity_pengeluaran, null);
                jumlahKeluar = mView.findViewById(R.id.jumlahKeluar);
                jumlahKeluar.addTextChangedListener(new ThousandNumberEditText(jumlahKeluar));
                catatanKeluar = mView.findViewById(R.id.catatanKeluar);
                //Calender Java Start
                tampilTgl = mView.findViewById(R.id.tampilTglKeluar);
                setTgl = mView.findViewById(R.id.setTglKeluar);
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
                //Open Gallery Java Intent Start
                imageView = mView.findViewById(R.id.tampilFotokita);
                opGallery = mView.findViewById(R.id.bukaGallery);

                opGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });
                //Open Gallery Java Intent End

                opCamera = mView.findViewById(R.id.bukaCamera);
                opCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
                });
                opCamera = mView.findViewById(R.id.bukaCamera);
                opCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
                });

                save = mView.findViewById(R.id.simpan);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stringTanggal= tampilTgl.getText().toString().trim();
                        stringJumlah = jumlahKeluar.getText().toString().trim();
                        stringKeterangan = catatanKeluar.getText().toString().trim();
                        if(TextUtils.isEmpty(stringTanggal)||TextUtils.isEmpty(stringJumlah)||TextUtils.isEmpty(stringKeterangan)) {
                            Toast.makeText(getContext(),"Tidak boleh Dikosongkan",Toast.LENGTH_LONG).show();
                        }else {
                            insertpengeluaran();
                            mInteractor.update();
                            dialog.hide();
                            display();
                        }

                    }
                });


            }
        });
        return v;
    }

    private void delete(int id) {
        DatabaseHelper mDbHelper=new DatabaseHelper(getContext());
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        String where = "ID_PENGELUARAN=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        ContentValues contentValues=new ContentValues();
        long newRow=db.delete(tabeldb.listtabeldb.TABLE_NAME_Pengeluaran,where,whereArgs);
        if (newRow==-1){
            Toast.makeText(getContext(),"Gagal Delete",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"Berhasil Delete:"+newRow ,Toast.LENGTH_LONG).show();

        }
    }


    private void update(int id) {
            String tanggalString = tampilTgl.getText().toString().trim();
            String pengeluaranString = jumlahKeluar.getText().toString().replace(",","").replace(".","");
            String catatanString = catatanKeluar.getText().toString().trim();
            byte[] Image = imageViewToByte(imageView);

            DatabaseHelper mDbHelper = new DatabaseHelper(getContext());

            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            int i ;
            i = Integer.parseInt(pengeluaranString);
            ContentValues contentValues = new ContentValues();
            String[] whereArgs = new String[] {String.valueOf(id)};
            String where = "ID_PENGELUARAN=?";

            contentValues.put(tabeldb.listtabeldb.COL_1_Pengeluaran, tanggalString);
            contentValues.put(tabeldb.listtabeldb.COL_2_Pengeluaran, i);
            contentValues.put(tabeldb.listtabeldb.COL_3_Pengeluaran, catatanString);
            contentValues.put(tabeldb.listtabeldb.COL_4_Pengeluaran, Image);

            long newRow = db.update(tabeldb.listtabeldb.TABLE_NAME_Pengeluaran,contentValues,where,whereArgs);
            if (newRow == -1) {
                Toast.makeText(getContext(), "TIDAK TERINPUT", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "BERHASIL:" + newRow, Toast.LENGTH_LONG).show();

            }
        }


    //write all method here
    private void insertpengeluaran() {
        String tanggalString = tampilTgl.getText().toString().trim();
        String pengeluaranString = jumlahKeluar.getText().toString().replace(",","").replace(".","");
        String catatanString = catatanKeluar.getText().toString().trim();
        byte[] Image = imageViewToByte(imageView);

        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        int b ;
        b= Integer.parseInt(pengeluaranString);
        contentValues.put(tabeldb.listtabeldb.COL_1_Pengeluaran, tanggalString);
        contentValues.put(tabeldb.listtabeldb.COL_2_Pengeluaran, b);
        contentValues.put(tabeldb.listtabeldb.COL_3_Pengeluaran, catatanString);
        contentValues.put(tabeldb.listtabeldb.COL_4_Pengeluaran, Image);

        long newRow = db.insert(tabeldb.listtabeldb.TABLE_NAME_Pengeluaran, null, contentValues);
        if (newRow == -1) {
            Toast.makeText(getContext(), "TIDAK TERINPUT", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "BERHASIL:" + newRow, Toast.LENGTH_LONG).show();

        }
    }


    private void display() {
        arraykeluar = new ArrayList<>();
        arraykeluar = getData();

        listView = (ListView) v.findViewById(R.id.listviewPengeluaran);
        listView .setAdapter(new viewAdapterkeluar(getContext()));
    }
    public List<dataPengeluaran> getData() {
        List<dataPengeluaran> lis = new ArrayList<>();
        dataPengeluaran child;
        Cursor c = null;
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            String query = "select * from " + tabeldb.listtabeldb.TABLE_NAME_Pengeluaran;
            c = db.rawQuery(query, null);
            while (c.moveToNext()) {
                child = new dataPengeluaran(
                        c.getInt(c.getColumnIndex(tabeldb.listtabeldb.COL_2_Pengeluaran)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_1_Pengeluaran)),
                        c.getString(c.getColumnIndex(tabeldb.listtabeldb.COL_3_Pengeluaran)),
                        c.getBlob(c.getColumnIndex(tabeldb.listtabeldb.COL_4_Pengeluaran)));
                child.setId(c.getInt(c.getColumnIndex(tabeldb.listtabeldb.ID_PENGELUARAN)));
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
                convertView = vi.inflate(R.layout.list_item_pengeluaran, null);
            }
            DecimalFormat decim = new DecimalFormat("#,###");
            final TextView pengeluaran = (TextView) convertView.findViewById(R.id.Danakeluar);
            pengeluaran.setText(decim.format(arraykeluar.get(position).getDanakeluar()));
            final TextView tanggal = (TextView) convertView.findViewById(R.id.tanggalKeluar);
            tanggal.setText(arraykeluar.get(position).getTanggalkeluar());
            final TextView keterangan = (TextView) convertView.findViewById(R.id.keteranganKeluar);
            keterangan.setText(arraykeluar.get(position).getKeterangankeluar());
            return convertView;
        }


    }

    private byte[] imageViewToByte(ImageView image) {
        image.buildDrawingCache();
        Bitmap bitmap = image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return  imageInByte;

    }






    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        } else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }

    }
}
