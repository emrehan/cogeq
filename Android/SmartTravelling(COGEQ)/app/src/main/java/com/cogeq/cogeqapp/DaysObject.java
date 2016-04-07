package com.cogeq.cogeqapp;

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
    }
}
