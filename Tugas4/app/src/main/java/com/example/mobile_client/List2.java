package com.example.mobile_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class List2 extends BaseAdapter{
    public String[] kend;
    public String[] plat;

    public List2(String[] kendaraam, String[] platnomor){
        this.kend = kendaraam;
        this.plat = platnomor;
    }

    @Override
    public int getCount() {
        return kend.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout2, viewGroup, false);
        }
        String currentKendaraan = kend[i];
        String currentPlatNomor = plat[i];

// Example: Set the text of TextViews with IDs "tvLahanParkir" and "tvStatus" in your list item layout
        TextView tvLahanParkir = view.findViewById(R.id.kendaraan);
        TextView tvStatus = view.findViewById(R.id.platnom);
        tvLahanParkir.setText(currentKendaraan);
        tvStatus.setText(currentPlatNomor);

        return view;
    }
}
