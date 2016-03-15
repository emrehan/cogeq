package com.cogeq.cogeqapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saygin on 3/12/2016.
 */
public class CogeqActivityAdapter extends ArrayAdapter<CogeqActivity> {
    private ArrayList<CogeqActivity> items;

    public CogeqActivityAdapter(Context context, int textViewResourceId, ArrayList<CogeqActivity> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cogeq_activity_row, null);
        }
        CogeqActivity cogeqActivity = items.get(position);
        if (cogeqActivity != null) {
            TextView tt = (TextView) v.findViewById(R.id.text);
            if (tt != null) {
                tt.setText(cogeqActivity.getName());
            }
           ImageView iv = (ImageView) v.findViewById(R.id.image);
           Picasso.with(getContext()).load(cogeqActivity.getImageUrl()).into(iv);
        }
        return v;
    }
}
