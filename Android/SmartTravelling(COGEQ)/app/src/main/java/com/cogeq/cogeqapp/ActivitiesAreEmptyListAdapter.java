package com.cogeq.cogeqapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saygin on 4/11/2016.
 */
public class ActivitiesAreEmptyListAdapter extends ArrayAdapter<String> {
    boolean startSelected, endSelected, citySelected;
    public ActivitiesAreEmptyListAdapter(Context context, int resource, boolean startDateSelected, boolean endDateSelected, boolean citySelected, ArrayList<String> list ) {
        super(context, resource, list);

        this.startSelected = startDateSelected;
        this.endSelected = endDateSelected;
        this.citySelected = citySelected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.empty_list_row, null);
        }
        TextView textView = (TextView) v.findViewById(R.id.empty_list_textView);
        switch (position){
            case 0:
                if( startSelected){
                    textView.setText( "Start Date is Selected.");
                }
                else{
                    textView.setText( "You should select a start date");
                }
                break;
            case 1:
                if( endSelected){
                    textView.setText( "End Date is Selected.");
                }
                else{
                    textView.setText( "You should select a end date");
                }
                break;
            case 2:
                if( citySelected){
                    textView.setText( "City is Selected.");
                }
                else{
                    textView.setText( "You should select a city");
                }
                break;
        }
        return v;
    }
}
