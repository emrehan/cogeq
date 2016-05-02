package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by cangiracoglu on 11/03/16.
 */
public class PreferencesFragment extends Fragment {
    public static List<PreferenceObject> allPreferences;
    public PreferencesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preference_grid_view, container, false);
        GridView gridview = (GridView)view.findViewById(R.id.gridview);

        allPreferences = getAllPreferences();
        PreferenceAdapter customAdapter = new PreferenceAdapter(getActivity(), allPreferences);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private List<PreferenceObject> getAllPreferences(){
        List<PreferenceObject> items = new ArrayList<>();
        items.add(new PreferenceObject(R.drawable.beach, "Beach"));
        items.add(new PreferenceObject(R.drawable.backpack,"Backpack"));
        items.add(new PreferenceObject(R.drawable.museum,"Museum"));
        items.add(new PreferenceObject(R.drawable.economy,"Economy"));
        items.add(new PreferenceObject(R.drawable.nature,"Nature"));
        items.add(new PreferenceObject(R.drawable.shopping,"Shopping"));
        items.add(new PreferenceObject(R.drawable.coffee,"Coffee"));
        items.add(new PreferenceObject(R.drawable.drinking,"Drinking"));
        items.add(new PreferenceObject(R.drawable.dessert,"Dessert"));
        return items;
    }

}
