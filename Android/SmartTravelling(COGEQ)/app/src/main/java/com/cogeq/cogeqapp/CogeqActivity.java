package com.cogeq.cogeqapp;

import android.content.ContentQueryMap;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


/**
 * Created by saygin on 3/12/2016.
 */
public class CogeqActivity {
    private String name;
    private String explanation;
    private String imageUrl;
    private LatLng position;
    private Date start, end;
    private String activityId;

    public CogeqActivity( String name, String exp){
        this.name = name;
        this.explanation = exp;
    }

    public CogeqActivity() {
        this.name = "";
        this.explanation = "";
        position = new LatLng( 40, 40);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setImageUrl( String url){
        imageUrl = url;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getActivityId() {
        return activityId;
    }
    //private Location location;
}
