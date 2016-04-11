package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class MyMapFragment extends Fragment {

    private static View view;
    /**
     * Note that this may be null if the Google Play services APK is not
     * available.
     */

    private static GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        try{
            view = (RelativeLayout) inflater.inflate(R.layout.map_layout, container, false);
        }
        catch ( InflateException e){
            Log.e("MAP", "Inflate Exception on Map!!");
        }

        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

    /***** Sets up the map if it is possible to do so *****/
    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment mapFragment = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.location_map));
            if( mapFragment != null) {
                mMap = mapFragment.getMap();
                Log.d( "MAP", "Fragment is null 2");
            }
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);

        //Add activities' positions
        ArrayList<CogeqActivity> activities = PrimaryFragment.getInstance().getActivities();
        for( int i = 0; i < activities.size(); i++){
            mMap.addMarker( new MarkerOptions().position( activities.get(i).getPosition()).title(activities.get(i).getName()));
        }

        // For zooming automatically to the Dropped PIN Location
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
//                longitude), 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.location_map));
            if( mapFragment != null) {
                Log.d( "MAP", "Fragment is not null!!");
                mMap = mapFragment.getMap(); // getMap is deprecated
            }
            else{
                Log.d( "MAP","Fragment is still null.");
            }
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }
}
