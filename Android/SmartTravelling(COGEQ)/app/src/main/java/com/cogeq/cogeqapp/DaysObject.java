package com.cogeq.cogeqapp;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cangiracoglu on 07/04/16.
 */
public class DaysObject {

    private String dayName;
    private boolean isSelected;

    public DaysObject(String dayName) {
        this.dayName = dayName;
        isSelected = false;
    }

    public String getDaysName() {
        return dayName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if( isSelected) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            try {
                SavedInformation.getInstance().currentDay = format.parse(dayName);
                Log.d("DAY_SELECT", "Current Day is Selected: " + dayName);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
