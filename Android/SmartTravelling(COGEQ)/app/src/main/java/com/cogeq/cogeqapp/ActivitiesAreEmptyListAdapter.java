package com.cogeq.cogeqapp;

import android.content.Context;
import android.media.Image;
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
    boolean startSelected, endSelected, citySelected, error;
    ArrayList<String> list;
    public ActivitiesAreEmptyListAdapter(Context context, int resource, boolean startDateSelected, boolean endDateSelected, boolean citySelected, boolean error, ArrayList<String> list ) {
        super(context, resource, list);

        this.list = list;
        this.startSelected = startDateSelected;
        this.endSelected = endDateSelected;
        this.citySelected = citySelected;
        this.error = error;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.empty_list_row, null);
        }
        TextView textView = (TextView) v.findViewById(R.id.empty_list_textView);
        ImageView iv = (ImageView) v.findViewById( R.id.selectionTick);
        switch (position){
            case 0:
                if( error){
                    textView.setText( "An Error Occured:" + list.get(position) );
                    iv.setVisibility( View.INVISIBLE);
                }
                else if( startSelected){
                    textView.setText( "Start Date is Selected.");
                    iv.setVisibility( View.VISIBLE);
                }
                else{
                    textView.setText( "You should select a start date");
                }
                break;
            case 1:
                if( endSelected){
                    textView.setText( "End Date is Selected.");
                    iv.setVisibility(View.VISIBLE);
                }
                else{
                    textView.setText( "You should select a end date");
                }
                break;
            case 2:
                if( citySelected){
                    textView.setText( "City is Selected.");
                    iv.setVisibility(View.VISIBLE);
                }
                else{
                    textView.setText( "You should select a city");
                }
                break;
        }
        return v;
    }
}
