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

import java.util.concurrent.TimeUnit;

import java.util.List;


/**
 * Created by Ratan on 7/29/2015.
 */
public class DaysFragment extends android.support.v4.app.ListFragment {

    private static DaysFragment instance;
    private ArrayList<DaysObject> m_days = null;
    private DaysAdapter m_adapter;
    public String daysOfTravel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        //TODO: connect to the server using travels path
        //TODO: getThe response and show the travels on day dayOfTravels
        m_days = new ArrayList<>();
        if( SavedInformation.getInstance().startDate != null && SavedInformation.getInstance().finishDate != null) {
            m_days.clear();
            long dayDifference = SavedInformation.getDateDiff(SavedInformation.getInstance().startDate, SavedInformation.getInstance().finishDate, TimeUnit.DAYS);
            for (int i = 0; i < dayDifference + 1; i++) {
                Date day = SavedInformation.getInstance().startDate;
                Date dayAfter = new Date(day.getTime() + TimeUnit.DAYS.toMillis(i));
                m_days.add(new DaysObject( new SimpleDateFormat("dd.MM.yyyy").format(dayAfter)));
            }
        }
        else {
            m_days.add(new DaysObject("Hey! You need to Select Start and Finish Date"));
        }
        m_adapter = new DaysAdapter(getActivity(), R.layout.row_of_days, m_days);
        setListAdapter(m_adapter);
        return inflater.inflate(R.layout.days_layout, null);
    }

    public ArrayList<DaysObject> getDays() {
        return m_days;
    }

    public static DaysFragment getInstance() {
        return instance;
    }

}
