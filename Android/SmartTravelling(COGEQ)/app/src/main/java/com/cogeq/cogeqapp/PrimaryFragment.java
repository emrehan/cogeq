package com.cogeq.cogeqapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ratan on 7/29/2015.
 */
public class PrimaryFragment extends android.support.v4.app.ListFragment {

    private static PrimaryFragment instance;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<CogeqActivity> m_activities = null;
    private CogeqActivityAdapter m_adapter;
    public int dayOfTravels = 1;

    @Override
    public void onStart(){
        super.onStart();
        Log.d("PRIMARY_FRAGMENT", "OnStart is called.");
        populateFragment();
    }

    public void populateFragment(){
        String startRfc3339 = "";
        String finishRfc3339 = "";
        if( SavedInformation.getInstance().startDate != null)
        {
            startRfc3339 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format( SavedInformation.getInstance().startDate);
        }
        if(SavedInformation.getInstance().finishDate != null){
            finishRfc3339 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SavedInformation.getInstance().finishDate);
        }
        String city = SavedInformation.getInstance().city;
        if( city != "" && startRfc3339 != "" && finishRfc3339 != "") {
            String url = getString(R.string.backendServer) + "/travels";
            url += "?";
            try {
                url += "city=" + URLEncoder.encode( city, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url += "&from=" + startRfc3339;
            url += "&to=" + finishRfc3339;
            url += "&access_token=" + SavedInformation.getInstance().accessToken;
            Log.d(  "PRIMARY_FRAGMENT", "URL: " + url );
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "CONNECTION", response.toString());
                            SavedInformation.getInstance().cogeqActivities = new ArrayList<>();
                            Log.d("INFO", "Previous Cogeq Activities are deleted.");
                            try {
                                if( response.has( "Error") ){
                                    Log.e( "JSON", "Error is returned.");
                                }
                                else{
                                    JSONArray array = response.getJSONArray("activities");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject activity = array.getJSONObject(i);
                                        CogeqActivity cActivity = new CogeqActivity();
                                        cActivity.setName(activity.getString("name"));
                                        cActivity.setExplanation(activity.getString("description"));
                                        cActivity.setImageUrl(activity.getString("picture_url"));
                                        JSONObject place = activity.getJSONObject( "place");
                                        cActivity.setPosition( new LatLng( place.getDouble("latitude"), place.getDouble("longitude")));
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                        cActivity.setStart(format.parse(activity.getString("from")));
                                        cActivity.setEnd(format.parse(activity.getString("to")));
                                        SavedInformation.getInstance().cogeqActivities.add(cActivity);
                                    }
                                }
                            } catch (JSONException e) {
                                Log.e( "JSON", "Eror while parsing response!");
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            m_activities = SavedInformation.getInstance().getActivitiesForSelectedDay();
                            m_adapter = new CogeqActivityAdapter( getActivity(), R.layout.cogeq_activity_row, m_activities);
                            setListAdapter(m_adapter);

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e( "CONNECTION", "Connection error while getting Activities");
                    VolleyLog.d( "CONNECTION", "Error: " + error.getMessage());
                }
            });
            queue.add(jsonObjectRequest);

            m_activities = SavedInformation.getInstance().getActivitiesForSelectedDay();
            m_adapter = new CogeqActivityAdapter( getActivity(), R.layout.cogeq_activity_row, m_activities);
            setListAdapter(m_adapter);
        }
        else {
            boolean startSelected, endSelected, citySelected;
            startSelected = endSelected = citySelected = true;
            if (startRfc3339 == "") {
                startSelected = false;
                Log.d( "EMPTY", "Start is null");
            }
            if (finishRfc3339 == "") {
                endSelected = false;
                Log.d( "EMPTY", "End is null");
            }
            if (city == "") {
                citySelected = false;
                Log.d( "EMPTY", "City is null");
            }
            ArrayList<String> list = new ArrayList<>();
            list.add( "" + startSelected);
            list.add( "" + endSelected);
            list.add( "" + citySelected);
            ListAdapter adapter = new ActivitiesAreEmptyListAdapter(getActivity(), R.layout.empty_list_row, startSelected, endSelected, citySelected, list);
            setListAdapter(adapter);
            Log.d( "ADAPTER", "emptyList adapter is set.");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        String startRfc3339 = "";
        String finishRfc3339 = "";

        m_activities = new ArrayList<>();
        m_adapter = new CogeqActivityAdapter( getActivity(), R.layout.cogeq_activity_row, m_activities);
        setListAdapter(m_adapter);
        return inflater.inflate(R.layout.primary_layout,null);
    }
    public static PrimaryFragment getInstance(){
        return instance;
    }
    public ArrayList<CogeqActivity> getActivities(){
        return m_activities;
    }
}
