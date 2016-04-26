package com.cogeq.cogeqapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity{
    public static FragmentManager fragmentManager;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        /**
         *Setup the DrawerLayout and NavigationView
         */

         mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
         mNavigationView = (NavigationView) findViewById(R.id.navigationView) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

         mFragmentManager = getSupportFragmentManager();
         mFragmentTransaction = mFragmentManager.beginTransaction();
         mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

         mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                 mDrawerLayout.closeDrawers();

                 if (menuItem.getItemId() == R.id.start_date) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new PickStartDateFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.finish_date) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new PickFinishDateFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_city) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new SearchCityFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_love) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new PreferencesFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_settings) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new SettingsFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_main) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                 }
                 else if( menuItem.getItemId() == R.id.nav_about){
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new AboutUsFragment()).commit();
                 }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    public void preferenceOnClick(View view){
        GridView gv = (GridView)findViewById(R.id.gridview);
        int pos = gv.getPositionForView(view);
        if( PreferencesFragment.allPreferences == null){
            //Precaution of nullptrException
            //Somethings are terribly wrong.
            Log.e("PREFERENCE", "Preferences are null" );
            return;
        }
        if( PreferencesFragment.allPreferences.get(pos).isSelected()){
            view.findViewById(R.id.preferenceTick).setVisibility(View.INVISIBLE);
            PreferencesFragment.allPreferences.get(pos).setIsSelected(false);
        }
        else{
            view.findViewById(R.id.preferenceTick).setVisibility(View.VISIBLE);
            PreferencesFragment.allPreferences.get(pos).setIsSelected(true);
        }

    }

    public void daysOnClick( View view){
        int pos = DaysFragment.getInstance().getListView().getPositionForView(view);

        ListView list = (ListView)view.getParent();
        for (int i = 0; i < DaysFragment.getInstance().getDays().size(); i++) {

            DaysFragment.getInstance().getDays().get(i).setIsSelected(false);
            if(list.getChildAt(i) != null) {
                ImageView imView = (ImageView) list.getChildAt(i).findViewById(R.id.daysTick);
                if (imView != null)
                    imView.setVisibility(View.INVISIBLE);
            }
        }
        view.findViewById(R.id.daysTick).setVisibility(View.VISIBLE);

        DaysFragment.getInstance().getDays().get(pos).setIsSelected(true);
        PrimaryFragment.getInstance().getTravelsForTheFirstTime();
        MyMapFragment.setUpMapIfNeeded();
    }

    public void activityOnClick( View view){
        int pos = ((ListView) PrimaryFragment.getInstance().getView().findViewById(R.id.list)).getPositionForView(view);
        //int pos = PrimaryFragment.getInstance().getListView().getPositionForView( view);
        if( view.getId() == R.id.activityRelativeLayout){
            Intent intent = new Intent(this, CogeqActivityViewActivity.class);
            intent.putExtra( "cogeqActivity", pos);
            startActivity(intent);
        }
        else if( view.getId() == R.id.crossImageView){
            CogeqActivity activity = PrimaryFragment.getInstance().getActivities().get(pos);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = getString(R.string.backendServer) + "/travels/"+ SavedInformation.getInstance().travelId;
            url += "/" + activity.getActivityId();
            Log.d( "CONNECTION", "URL:" + url );

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("CONNECTION", response.toString());
                            SavedInformation.getInstance().travelObject = response;
                            PrimaryFragment.getInstance().populateFragment();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("CONNECTION", "Connection error deleting Activity");
                    VolleyLog.d("CONNECTION", "Error: " + error.getMessage());

                }
            });
            queue.add(jsonObjectRequest);
        }
    }
}