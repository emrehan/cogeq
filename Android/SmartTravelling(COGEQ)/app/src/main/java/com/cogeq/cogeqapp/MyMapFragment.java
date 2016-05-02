package com.cogeq.cogeqapp;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.content.Context;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.LocationListener;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ratan on 7/29/2015.
 */
public class MyMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {


    private static MyMapFragment instance = null;
    private LatLng myLatLng;
    private static final String serverKey = "AIzaSyC8vcQsQs-cJnQaanQriv51o6w2XikYHlk";
    private static View view;
    /**
     * Note that this may be null if the Google Play services APK is not
     * available.
     */

    private static GoogleMap mMap;
    private static LatLng position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
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
            SupportMapFragment mapFragment = null;
            if( instance != null) {
                mapFragment = ((SupportMapFragment) instance.getChildFragmentManager()
                        .findFragmentById(R.id.location_map));
            }
            else{
                mapFragment = ((SupportMapFragment) MainActivity.fragmentManager
                        .findFragmentById(R.id.location_map));
            }

            if( mapFragment == null){
                Log.e( "MAP", "Map fragment is null");
            }
            else {
                Log.d("MAP", "Map fragment is not null");
                mapFragment.getMapAsync(instance);
            }

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
        Log.d("MAP", "Setup map is called");
        mMap.clear();

        //Add activities' positions
        ArrayList<CogeqActivity> activities = PrimaryFragment.getInstance().getActivities();
        LatLng destination;
        LatLng source = null;
        for( int i = 0; i < activities.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(activities.get(i).getPosition()).title(activities.get(i).getName()));
            destination = activities.get(i).getPosition();
            if (source != null) {

                GoogleDirection.withServerKey(serverKey)
                        .from(source)
                        .to(destination)
                        .transitMode(TransportMode.DRIVING)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                if (direction.isOK()) {
                                    Route route = direction.getRouteList().get(0);
                                    Leg leg = route.getLegList().get(0);
                                    ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                    PolylineOptions polylineOptions = DirectionConverter.createPolyline(instance.getContext(), directionPositionList, 5, Color.BLUE);
                                    mMap.addPolyline(polylineOptions);
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {

                            }
                        });
            }
            source = destination;
        }
        mMap.setMyLocationEnabled(true);
        if( instance != null) {
            instance.onMapReady(mMap);
        }
        if( position != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12.0f));
            Log.d("MAP", "Zoom is zooming");
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
        Log.d( "MAP", "On map ready is called.");
        mMap = googleMap;

        try {
            // Get location from GPS if it's available
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
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

            myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            position = myLatLng;

        }
        catch (SecurityException se) {
            se.printStackTrace();
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
