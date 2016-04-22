package com.cogeq.cogeqapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by saygin on 3/12/2016.
 */
public class CogeqActivityAdapter extends ArrayAdapter<CogeqActivity> {
    private ArrayList<CogeqActivity> items;
    final int INVALID_ID = -1;

    HashMap<CogeqActivity, Integer> mIdMap = new HashMap<CogeqActivity, Integer>();

    public CogeqActivityAdapter(Context context, int textViewResourceId, ArrayList<CogeqActivity> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        for (int i = 0; i < items.size(); ++i) {
            mIdMap.put(items.get(i), i);
        }
    }
    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        CogeqActivity item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
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
            TextView dateText = (TextView) v.findViewById( R.id.dateText);
            if( dateText != null){
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date from, to;
                from = cogeqActivity.getStart();
                to = cogeqActivity.getEnd();
                String dateTextStr = format.format(from) + "-" + format.format(to);
                dateText.setText( dateTextStr );
            }
            ImageView iv = (ImageView) v.findViewById(R.id.image);
            Picasso.with(getContext()).load(cogeqActivity.getImageUrl()).into(iv);
        }
        return v;
    }
}
