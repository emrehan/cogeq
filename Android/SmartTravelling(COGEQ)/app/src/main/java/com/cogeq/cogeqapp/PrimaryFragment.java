package com.cogeq.cogeqapp;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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

/**
 * Created by Ratan on 7/29/2015.
 */
//public class PrimaryFragment extends android.support.v4.app.ListFragment {
public class PrimaryFragment extends Fragment {
    private static PrimaryFragment instance;
    private ArrayList<CogeqActivity> m_activities = null;
    private CogeqActivityAdapter m_adapter;

    @Override
    public void onStart(){
        super.onStart();
        Log.d("PRIMARY_FRAGMENT", "OnStart is called.");
        getTravelsForTheFirstTime();
    }

    public void populateFragment(){
        SavedInformation.getInstance().cogeqActivities = new ArrayList<>();
        Log.d("INFO", "Previous Cogeq Activities are deleted.");
        JSONObject travelsObject = SavedInformation.getInstance().travelObject;
        if( travelsObject == null){
            Log.e( "POPULATE_FRAGMENT" , "Travel Object is null!!");
            return;
        }
        try {
            if( travelsObject.has( "Error") ){
                Log.e("JSON", "Error is returned.");
                SavedInformation.getInstance().fillActivitiesWithDebug();
            }
            else{
                SavedInformation.getInstance().travelId = travelsObject.getString("travel_id");
                JSONArray array = travelsObject.getJSONArray("activities");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject activity = array.getJSONObject(i);
                    CogeqActivity cActivity = new CogeqActivity();
                    cActivity.setActivityId(activity.getString("id"));
                    cActivity.setName(activity.getString("name"));
                    cActivity.setExplanation(activity.getString("description"));
                    cActivity.setImageUrl(activity.getString("picture_url"));
                    JSONObject place = activity.getJSONObject( "place");
                    cActivity.setPosition(new LatLng(place.getDouble("latitude"), place.getDouble("longitude")));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    cActivity.setStart(format.parse(activity.getString("from")));
                    cActivity.setEnd(format.parse(activity.getString("to")));
                    SavedInformation.getInstance().cogeqActivities.add(cActivity);
                }
            }
        } catch (JSONException e) {
            Log.e( "JSON", "Eror while parsing travelsObject!");
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        try {
//            SavedInformation.getInstance().fillActivitiesWithDebug();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        m_activities = SavedInformation.getInstance().getActivitiesForSelectedDay();
        Log.d( "ACTIVITIES", "Size of m_activities: " + m_activities.size());
        m_adapter = new CogeqActivityAdapter( getActivity(), R.layout.cogeq_activity_row, m_activities);
        DynamicListView lv = (DynamicListView)getView().findViewById(R.id.list);
        lv.setCheeseList(m_activities);
        lv.setAdapter(m_adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        setListAdapter(m_adapter);
        m_adapter.notifyDataSetChanged();
    }
    public void getTravelsForTheFirstTime(){
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
        if( !city.equals( "") && !startRfc3339.equals( "") && !finishRfc3339.equals( "")) {
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
                            SavedInformation.getInstance().travelObject = response;
                            populateFragment();
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
            DynamicListView lv = (DynamicListView)getView().findViewById(R.id.list);
            lv.setCheeseList(m_activities);
            lv.setAdapter(m_adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//            setListAdapter(m_adapter);
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
            DynamicListView lv = (DynamicListView)getView().findViewById(R.id.list);
            lv.setCheeseList(m_activities);
            lv.setAdapter(m_adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//            setListAdapter(adapter);
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


        View view = inflater.inflate(R.layout.primary_layout, container, false);
        DynamicListView lv = (DynamicListView)view.findViewById(R.id.list);
        lv.setCheeseList(m_activities);
        lv.setAdapter(m_adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        setListAdapter(m_adapter);
        return view;
    }
    public static PrimaryFragment getInstance(){
        return instance;
    }
    public ArrayList<CogeqActivity> getActivities(){
        return m_activities;
    }
}
