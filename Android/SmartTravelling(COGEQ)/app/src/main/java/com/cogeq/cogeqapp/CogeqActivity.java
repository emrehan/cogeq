package com.cogeq.cogeqapp;

import android.content.ContentQueryMap;

import com.google.android.gms.maps.model.LatLng;


/**can can
 * saygin saygin
 * baska bir sey
 * Created by saygin on 3/12/2016.
 * grc grc
 */
public class CogeqActivity {
    private String name;
    private String explanation;
    private double durationInHours;
    private String imageUrl;
    private LatLng position;

    public CogeqActivity( String name, String exp){
        this.name = name;
        this.explanation = exp;
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
    //private Location location;
}
