package com.saygindogu.saygin.androidplay;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<TravelPreference> m_preferences = null;
    private PreferenceAdapter m_adapter;
    private Runnable viewOrders;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_preferences = new ArrayList<>();
        m_preferences.add( new TravelPreference("beach", R.drawable.beach));
        m_preferences.add( new TravelPreference("backpack", R.drawable.backpack));
        m_preferences.add( new TravelPreference("museum", R.drawable.museum));
        m_preferences.add( new TravelPreference("economy", R.drawable.economy));
        m_preferences.add( new TravelPreference("nature", R.drawable.nature));
        m_preferences.add( new TravelPreference("shopping", R.drawable.shopping));

        m_adapter = new PreferenceAdapter(this, R.layout.row, m_preferences);
        setListAdapter(m_adapter);
        //Ben Can
    }

    public void preferenceOnClick(View view){
        view.findViewById(R.id.icon).setVisibility(View.VISIBLE);

    }
}