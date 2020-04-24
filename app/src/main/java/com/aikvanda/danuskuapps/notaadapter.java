package com.aikvanda.danuskuapps;

/**
 * Created by Satria on 4/5/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class notaadapter extends BaseAdapter{
    private Context context;
    private  int layout;
    private ArrayList<listnota> notaList;

    public notaadapter(Context context, int layout, ArrayList<listnota> notaList) {
        this.context = context;
        this.layout = layout;
        this.notaList = notaList;
    }

    @Override
    public int getCount() {
        return notaList.size();
    }

    @Override
    public Object getItem(int position) {
        return notaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView tanggal;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);


            holder.tanggal= (TextView) row.findViewById(R.id.tanggalnota);
            holder.tanggal= (TextView) row.findViewById(R.id.jumlahNota);
            holder.tanggal= (TextView) row.findViewById(R.id.keterangan);
            holder.imageView = (ImageView) row.findViewById(R.id.imgNota);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        listnota nota = notaList.get(position);
        holder.tanggal.setText(nota.getTanggal());
        byte[] notaImage = nota.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(notaImage, 0, notaImage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;
    }
}
