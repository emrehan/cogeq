package com.androidbelieve.drawerwithswipetabs;

/**
 * Created by cangiracoglu on 11/03/16.
 */

public class PreferenceObject {

    private int screenShot;
    private String preferenceName;
    private boolean isSelected;

    public PreferenceObject(int screenShot, String preferenceName) {
        this.screenShot = screenShot;
        this.preferenceName = preferenceName;
    }

    public int getScreenShot() {
        return screenShot;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
