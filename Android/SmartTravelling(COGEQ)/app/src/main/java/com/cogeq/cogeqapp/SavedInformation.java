package com.cogeq.cogeqapp;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by saygin on 4/7/2016.
 */
public class SavedInformation {
    private static SavedInformation instance;
    public Date startDate, finishDate;
    public int dayDifference;
    public String city;


    private SavedInformation() {
        city = "";
        startDate = finishDate = null;
    }

    public static SavedInformation getInstance(){
        if( instance ==  null){
            instance = new SavedInformation();
        }
        return instance;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
