package com.cogeq.cogeqapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ratan on 7/29/2015.
 */
public class PrimaryFragment extends android.support.v4.app.ListFragment {

    public static final String TAG = "Connection";
    private static PrimaryFragment instance;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<CogeqActivity> m_activities = null;
    private CogeqActivityAdapter m_adapter;
    public int dayOfTravels = 1;

    @Override
    public void onStart(){
        super.onStart();
        Log.e( "HEY", "OnStart is called.");
        String startRfc3339 = "";
        String finishRfc3339 = "";
        if( SavedInformation.getInstance().startDate != null && SavedInformation.getInstance().finishDate != null) {
            startRfc3339 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format( SavedInformation.getInstance().startDate);
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
            System.out.println( "URL: " + url );
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            SavedInformation.getInstance().cogeqActivities = new ArrayList<>();
                            Log.d( "INFO", "Previous Cogeq Activities are deleted.")
                            try {
                                JSONArray array = response.getJSONArray("activities");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject activity = array.getJSONObject(i);
                                    CogeqActivity cActivity = new CogeqActivity();
                                    cActivity.setName( activity.getString("name"));
                                    cActivity.setExplanation(activity.getString("description"));
                                    cActivity.setImageUrl(activity.getString("picture_url"));
                                    JSONObject place = activity.getJSONObject( "place");
                                    cActivity.setPosition( new LatLng( place.getDouble("latitude"), place.getDouble("longitude")));
                                    SavedInformation.getInstance().cogeqActivities.add( cActivity);
                                }
                                
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //pDialog.hide();
                }
            });
            queue.add(jsonObjectRequest);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        String startRfc3339 = "";
        String finishRfc3339 = "";
        System.out.println( "OnCreateView is called");

        //TODO: connect to the server using travels path
        //TODO: getThe response and show the travels on day dayOfTravels
        m_activities = new ArrayList<>();
//        m_activities.add( new CogeqActivity( "activity1", "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. 1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar."));
//        m_activities.add( new CogeqActivity( "activity2", "2Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
//        m_activities.add( new CogeqActivity( "activity3", "3Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
//        m_activities.add(new CogeqActivity("activity4", "4Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
//        m_activities.get(0).setImageUrl("http://www.asociatiaedelvais.ro/wp-content/uploads/2014/11/goodwp.com_163111.jpg");
//        m_activities.get(1).setImageUrl("http://drugfree.scdn1.secure.raxcdn.com/wp-content/uploads/2010/09/alcohol11.jpg");
//        m_activities.get(2).setImageUrl("http://orig12.deviantart.net/e367/f/2010/327/4/c/sunny_beach_14475003_by_stockproject1-d33h5n6.jpg");
//        m_activities.get(3).setImageUrl("http://covermaker.net/thumbnail/10/1058.jpg");
//        m_activities.get(0).setPosition( new LatLng(39.9117, 32.8403));
//        m_activities.get(1).setPosition( new LatLng(39.8563, 32.8403));
//        m_activities.get(2).setPosition( new LatLng(39.564, 32.8403));
//        m_activities.get(3).setPosition( new LatLng(39.65465, 32.8403));
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
