package com.example.mobile_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class List extends BaseAdapter{
    public String[] lahanparkir;
    public String[] status;

    public List(String[] lahanparkir, String[] status){
        this.lahanparkir = lahanparkir;
        this.status = status;
    }

    @Override
    public int getCount() {
        return lahanparkir.length;
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout, viewGroup, false);
        }
        String currentLahanParkir = lahanparkir[i];
        String currentStatus = status[i];

// Example: Set the text of TextViews with IDs "tvLahanParkir" and "tvStatus" in your list item layout
        TextView tvLahanParkir = view.findViewById(R.id.lahanparkir);
        TextView tvStatus = view.findViewById(R.id.status);
        tvLahanParkir.setText(currentLahanParkir);
        tvStatus.setText(currentStatus);

        return view;
    }
}
