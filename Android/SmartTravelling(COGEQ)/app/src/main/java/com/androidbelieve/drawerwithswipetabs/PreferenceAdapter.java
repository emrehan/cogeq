package com.androidbelieve.drawerwithswipetabs;

/**
 * Created by cangiracoglu on 11/03/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PreferenceAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<PreferenceObject> listStorage;
    private Context context;

    public PreferenceAdapter(Context context, List<PreferenceObject> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.preference_item, parent, false);
            listViewHolder.screenShot = (ImageView)convertView.findViewById(R.id.screen_shot);
            listViewHolder.preferenceName = (TextView)convertView.findViewById(R.id.preferenceName);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.screenShot.setImageResource(listStorage.get(position).getScreenShot());
        listViewHolder.preferenceName.setText(listStorage.get(position).getPreferenceName());

        return convertView;
    }

    static class ViewHolder{
        ImageView screenShot;
        TextView preferenceName;
    }

}

