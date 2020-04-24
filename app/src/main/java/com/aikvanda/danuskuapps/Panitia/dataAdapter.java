package com.aikvanda.danuskuapps.Panitia;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aikvanda.danuskuapps.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aulia Ikvanda Yoren
 * on 13/03/2018.
 */

public class dataAdapter extends ArrayAdapter<data> {
    public dataAdapter(Activity context , ArrayList<data> word){
        super(context, 0, word);
    }

    public dataAdapter(
            @NonNull Context context, int resource,
            @NonNull List<data> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_data, parent, false);

        }
        data hwn = getItem(position);
        TextView textView = (TextView) listView.findViewById(R.id.nama);
        textView.setText(hwn.getNama());
        TextView textView2 = (TextView) listView.findViewById(R.id.divisi);
        textView2.setText(hwn.getDivisi());
        return listView;
    }
}
