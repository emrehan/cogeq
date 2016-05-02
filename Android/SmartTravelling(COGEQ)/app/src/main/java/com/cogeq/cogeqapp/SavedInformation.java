package com.cogeq.cogeqapp;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by saygin on 4/7/2016.
 */
public class SavedInformation {
    private static SavedInformation instance;
    public Date startDate, finishDate;
    public Date currentDay;
    public String city;
    public List<CogeqActivity> cogeqActivities;
    public String accessToken;
    public String travelId;
    public JSONObject travelObject;
    public boolean error = false;


    private SavedInformation() {
        city = "";
        startDate = finishDate = null;
        currentDay = null;
        cogeqActivities = new ArrayList<>();
    }

    public void fillActivitiesWithDebug() throws ParseException {
        Log.d( "DEBUG", "Fill activities with debug");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SavedInformation.getInstance().cogeqActivities = new ArrayList<>();
        SavedInformation.getInstance().cogeqActivities.add( new CogeqActivity( "activity1", "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. 1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar."));
        SavedInformation.getInstance().cogeqActivities.add( new CogeqActivity( "activity2", "2Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
        SavedInformation.getInstance().cogeqActivities.add( new CogeqActivity( "activity3", "3Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
        SavedInformation.getInstance().cogeqActivities.add(new CogeqActivity("activity4", "4Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
        SavedInformation.getInstance().cogeqActivities.get(0).setImageUrl("https://irs0.4sqi.net/img/general/500x300/26739064_mUxQ4CGrobFqwpcAIoX6YoAdH0xCDT4YAxaU6y65PPI.jpg");
        SavedInformation.getInstance().cogeqActivities.get(1).setImageUrl("http://drugfree.scdn1.secure.raxcdn.com/wp-content/uploads/2010/09/alcohol11.jpg");
        SavedInformation.getInstance().cogeqActivities.get(2).setImageUrl("http://orig12.deviantart.net/e367/f/2010/327/4/c/sunny_beach_14475003_by_stockproject1-d33h5n6.jpg");
        SavedInformation.getInstance().cogeqActivities.get(3).setImageUrl("http://covermaker.net/thumbnail/10/1058.jpg");
        SavedInformation.getInstance().cogeqActivities.get(0).setPosition(new LatLng(39.9117, 32.8403));
        SavedInformation.getInstance().cogeqActivities.get(1).setPosition(new LatLng(39.8563, 32.8403));
        SavedInformation.getInstance().cogeqActivities.get(2).setPosition(new LatLng(39.564, 32.8403));
        SavedInformation.getInstance().cogeqActivities.get(3).setPosition(new LatLng(39.65465, 32.8403));
        SavedInformation.getInstance().cogeqActivities.get(0).setStart(format.parse("2016-05-02T12:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(1).setStart(format.parse("2016-05-02T13:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(2).setStart(format.parse("2016-05-03T13:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(3).setStart(format.parse("2016-05-03T14:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(0).setEnd(format.parse("2016-05-01T00:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(1).setEnd(format.parse("2016-05-01T00:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(2).setEnd(format.parse("2016-05-01T00:00:00"));
        SavedInformation.getInstance().cogeqActivities.get(3).setEnd( format.parse( "2016-05-01T00:00:00"));
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

    public ArrayList<CogeqActivity> getActivitiesForSelectedDay() {
        ArrayList<CogeqActivity> list = new ArrayList<>();
        if( currentDay != null){
            for( CogeqActivity a : cogeqActivities){
                Date activityDate;
                Calendar cal = Calendar.getInstance();
                cal.setTime( a.getStart());
                cal.set(Calendar.HOUR_OF_DAY,0);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND,0);
                cal.set(Calendar.MILLISECOND,0);

                activityDate = cal.getTime();

                long dayDifference = getDateDiff( currentDay, activityDate, TimeUnit.DAYS);
                Log.d( "DAY", "Day Difference:" + dayDifference);
                if( dayDifference == 0){
                    list.add( a);
                }
            }
        }
        else{
            Log.e( "GET_ACTIVITIES", "Current day is null.");
        }
        return list;
    }
}
