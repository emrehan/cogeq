package com.cogeq.cogeqapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PreferenceAdapter extends ArrayAdapter<TravelPreference> {

    private ArrayList<TravelPreference> items;

    public PreferenceAdapter(Context context, int textViewResourceId, ArrayList<TravelPreference> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }
        TravelPreference travelPreference = items.get(position);
        if (travelPreference != null) {
            TextView tt = (TextView) v.findViewById(R.id.toptext);
            if (tt != null) {
                tt.setText(travelPreference.getName());
            }
            ImageView iv = (ImageView) v.findViewById(R.id.icon);
            iv.setImageResource( travelPreference.getImageId());
        }
        return v;
    }
}