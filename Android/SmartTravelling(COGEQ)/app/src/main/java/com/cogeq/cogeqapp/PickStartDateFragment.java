package com.cogeq.cogeqapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Can on 7/29/2015.
 */

public class PickStartDateFragment extends Fragment{

    private static final int YEAR_BEGIN = 1900;
    int travelDays;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.date_layout, null);
        DatePicker datePicker = (DatePicker) view.findViewById( R.id.datePicker);
        final EditText lengthOfTravel = (EditText) view.findViewById( R.id.lengthOfTravel);
        final TextView startDate = (TextView) view.findViewById( R.id.startDate);

        lengthOfTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthOfTravel.setText("");
            }

        });

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                CharSequence text = dayOfMonth + "/" + monthOfYear + "/" + year;
                Date date = new Date(year - YEAR_BEGIN, monthOfYear, dayOfMonth);
                PrimaryFragment.getInstance().startDate = date;
                startDate.setText(text);
<<<<<<< Updated upstream:Android/SmartTravelling(COGEQ)/app/src/main/java/com/cogeq/cogeqapp/PickStartDateFragment.java
=======
                EditText lengthOfTravel = (EditText) view.findViewById( R.id.lengthOfTravel);
                try {
                    travelDays = Integer.parseInt(lengthOfTravel.toString());
                }
                catch ( NumberFormatException nfe){
                    nfe.printStackTrace();
                }
                Date endDate = new Date( year - YEAR_BEGIN, monthOfYear,dayOfMonth + travelDays);
                String url = getString(R.string.backendServer) + "/";
>>>>>>> Stashed changes:Android/SmartTravelling(COGEQ)/app/src/main/java/com/cogeq/cogeqapp/PickDateFragment.java
            }
        });

        return view;

    }
}






