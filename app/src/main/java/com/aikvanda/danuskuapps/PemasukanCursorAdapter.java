/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aikvanda.danuskuapps;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * {@link PemasukanCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class PemasukanCursorAdapter extends CursorAdapter {


    public PemasukanCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.activity_list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView tanggal = (TextView) view.findViewById(R.id.tanggal);
        TextView Pemasukan = (TextView) view.findViewById(R.id.Dana);
        TextView keterangan = (TextView) view.findViewById(R.id.keterangan);
        // Read the pet attributes from the Cursor for the current pet
        String editTanggal = cursor.getString(cursor.getColumnIndex(tabeldb.listtabeldb.COL_1_Pemasukan));
        double editPemasukan = cursor.getInt(cursor.getColumnIndex(tabeldb.listtabeldb.COL_2_Pemasukan));
        String editKeterangan= cursor.getString(cursor.getColumnIndex(tabeldb.listtabeldb.COL_3_Pemasukan));

        // Update the TextViews with the attributes for the current pet
        DecimalFormat decim = new DecimalFormat("#,###.##");
        tanggal.setText(editTanggal);
        Pemasukan.setText(decim.format(editPemasukan));
        keterangan.setText(editKeterangan);
    }
}
