package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SearchCityFragment extends Fragment {

    private ListView searchList;
    private ArrayAdapter<String> adapter;
    private SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_layout,null);
        adapter = new ArrayAdapter<String>( getContext(), R.layout.adapter_resource );
        searchList = (ListView)view.findViewById( R.id.search_list);
        searchView = (SearchView) view.findViewById( R.id.searchView);
        searchList.setAdapter( adapter);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String cityName = (String) searchList.getItemAtPosition( position);
                searchView.setQuery( cityName, false);
                PrimaryFragment.getInstance().city = cityName;
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList.setVisibility(View.INVISIBLE);
                System.out.println( "Query:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    searchList.setVisibility(View.VISIBLE);
                } else {
                    searchList.setVisibility(View.INVISIBLE);
                }
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue( getActivity().getApplicationContext());
                String url = getString( R.string.backendServer ) + "/cities?query=" + newText;
                // Request a string response from the provided URL.
                System.out.println( "URL:" + url);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //TODO: parse json
                                try {
                                    //JSONObject jsonObject = new JSONObject(response);
                                    JSONObject jsonObject = response;
                                    JSONArray jsonArray = jsonObject.getJSONArray("cities");
                                    ArrayList<String> list = new ArrayList<String>();
                                    if (jsonArray != null) {
                                        int len = jsonArray.length();
                                        for (int i=0;i<len;i++){
                                            list.add(jsonArray.get(i).toString());
                                        }
                                    }
                                    adapter = new ArrayAdapter<String>( getContext(), R.layout.adapter_resource , list );
                                    searchList.setAdapter( adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println( "Connection Error!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
                //adapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }
}
