package com.cogeq.cogeqapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cangiracoglu on 07/04/16.
 */
public class DaysAdapter extends ArrayAdapter<DaysObject> {

        private ArrayList<DaysObject> items;

        public DaysAdapter (Context context, int textViewResourceId, ArrayList<DaysObject> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_of_days, null);
            }
            DaysObject daysObject = items.get(position);
            if (daysObject != null) {
                TextView tt = (TextView) v.findViewById(R.id.daysOfRows);
                if (tt != null) {
                    tt.setText(daysObject.getDaysName());
                }
                ImageView iv = (ImageView) v.findViewById(R.id.daysTick);
                if( iv != null){
                    if( daysObject.isSelected() ){
                        iv.setVisibility( View.VISIBLE);
                    }
                    else{
                        iv.setVisibility( View.INVISIBLE);
                    }
                }
            }
            return v;
        }
    }


