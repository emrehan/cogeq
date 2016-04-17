package com.cogeq.cogeqapp;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class MyMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {


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

            //mapFragment.getMapAsync(this);

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //startActivity(gpsOptionsIntent);

        try {
            // Zooming camera to position of the user
            // Get location from GPS if it's available
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (android.location.LocationListener) this);
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, (android.location.LocationListener) this);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (myLocation == null)
                myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (myLocation == null)
                myLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            int MAX_TIME = 30000;
            int counter = 0;
            while (myLocation == null)
            {
                if(counter > MAX_TIME) {
                    break;
                }
                else {
                    myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (myLocation == null)
                        myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (myLocation == null)
                        myLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    Thread.sleep(1000);
                    counter += 1000;
                }
            }

            // Location wasn't found, check the next most accurate place for the current location
            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                // Finds a provider that matches the criteria
                String provider = lm.getBestProvider(criteria, true);
                // Use the provider to get the last known location
                myLocation = lm.getLastKnownLocation(provider);
            }

            LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

            // Add a marker in Sydney and move the camera
            LatLng markerPos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(markerPos)
                    .title("Turkish Kebab")
                    .snippet("Deneme"));

            // Move the camera and zoom to the location
            float zoomLevel = 14.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoomLevel));
        }
        catch (SecurityException se) {

        }
        catch (InterruptedException ie) {

        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }
}
