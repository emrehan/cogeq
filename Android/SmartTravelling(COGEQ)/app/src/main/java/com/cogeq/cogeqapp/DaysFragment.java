package com.cogeq.cogeqapp;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Ratan on 7/29/2015.
 */
public class DaysFragment extends android.support.v4.app.ListFragment {

    private ArrayList<String> m_days = null;
    private DaysAdapter m_adapter;
    public String daysOfTravel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //TODO: connect to the server using travels path
        //TODO: getThe response and show the travels on day dayOfTravels
        m_days = new ArrayList<>();
        m_days.add( "26.04.2016");
        m_days.add( "27.04.2016");
        m_days.add( "28.04.2016");
        m_days.add( "29.04.2016");
        m_days.add( "30.04.2016");


        m_adapter = new DaysAdapter( getActivity(), R.layout.row_of_days, m_days);
        setListAdapter(m_adapter);
        return inflater.inflate(R.layout.days_layout,null);
    }

    public ArrayList<String> getDays(){
        return m_days;
    }

    }
