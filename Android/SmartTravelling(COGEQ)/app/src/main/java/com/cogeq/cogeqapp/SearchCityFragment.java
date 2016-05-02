package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SearchCityFragment extends Fragment {

    private ListView searchList;
    private ArrayAdapter<String> adapter;
    private SearchView searchView;
    private static final String[] CITIES = new String[] {
            "'s-Hertogenbosch",
            "Abu Dhabi",
            "Accra",
            "Ad Damman",
            "Adelaide",
            "Afyon",
            "Aguascalientes",
            "Aintab",
            "Ajman",
            "Al-Ahmadi",
            "Al-Muharraq",
            "Alajuela",
            "Almaty",
            "Alor Setar",
            "Amman",
            "Ammochostos",
            "Amsterdam",
            "Ankara",
            "Antalya",
            "Antioch",
            "Antofagasta",
            "Antwerpen",
            "Ar Rifa",
            "Aracaju",
            "Arnhem",
            "As-Salt",
            "Asuncion",
            "Athens",
            "Atlanta",
            "Auckland",
            "Austin",
            "Aydin",
            "B'abda",
            "Baku",
            "Balikesir",
            "Balikpapan",
            "Baltimore",
            "Banda Aceh",
            "Bandjermasin",
            "Bandung",
            "Bangalore",
            "Bangkok",
            "Barcelona",
            "Barranquilla",
            "Beijing",
            "Beirut",
            "Belem",
            "Belo Horizonte",
            "Berlin",
            "Birmingham",
            "Boa Vista",
            "Bogota",
            "Bologna",
            "Bolu",
            "Boston",
            "Brasilia",
            "Brisbane",
            "Brooklyn",
            "Bruges",
            "Brussels",
            "Bucharest",
            "Budapest",
            "Buenos Aires",
            "Buffalo",
            "Bursa",
            "Cairo",
            "Calgary",
            "Cali",
            "Callao",
            "Campeche",
            "Campo Grande",
            "Canakkale",
            "Cape Town",
            "Caracas",
            "Cartago",
            "Casablanca",
            "Ceboksary",
            "Chang Rai",
            "Charlotte",
            "Chelyabinsk",
            "Chennai",
            "Chester",
            "Chiang Mai",
            "Chicago",
            "Chios",
            "Chon Buri",
            "Cincinnati",
            "Cleveland",
            "Colima",
            "Cologne",
            "Colombo",
            "Columbia",
            "Columbus",
            "Concepcion",
            "Copenhagen",
            "Coquimbo",
            "Cordoba",
            "Cuernavaca",
            "Cuiaba",
            "Curitiba",
            "Dallas",
            "Davao",
            "Delhi",
            "Denizli",
            "Denpasar",
            "Denver",
            "Des Moines",
            "Detroit",
            "Dnipropetrovs'k",
            "Doha",
            "Donets'k",
            "Dubai",
            "Dublin",
            "Dusseldorf",
            "Edinburgh",
            "Edirne",
            "Edmonton",
            "El Paso",
            "Eskisehir",
            "Essen",
            "Europoort",
            "Fawley",
            "Florence",
            "Florianopolis",
            "Fort-De-France",
            "Fortaleza",
            "Frankfurt",
            "Fremantle",
            "Fukuoka",
            "Gent",
            "Gifu",
            "Giresun",
            "Giza",
            "Glasgow",
            "Goiania",
            "Gor'kiy",
            "Guadalajara",
            "Guayaquil",
            "Haarlem",
            "Halfway Tree",
            "Hamburg",
            "Harrisburg",
            "Hartford",
            "Hasselt",
            "Hawalli",
            "Helsinki",
            "Heredia",
            "Hermosillo",
            "Hiroshima",
            "Ho Chi Minh City",
            "Hong Kong",
            "Honolulu",
            "Houston",
            "Hyderabad",
            "Inch'on",
            "Indianapolis",
            "Ipoh",
            "Iquique",
            "Irkutsk",
            "Isparta",
            "Istanbul",
            "Ivanovo",
            "Izevsk",
            "Izmir",
            "Jacksonville",
            "Jakarta",
            "Jalapa",
            "Jeddah",
            "Jidd Hafs",
            "Joao Pessoa",
            "Johannesburg",
            "Johor Baharu",
            "Kahramanmaras",
            "Kaliningrad",
            "Kangar",
            "Kansas City",
            "Kawasaki",
            "Kayseri",
            "Kazan'",
            "Khabarovsk",
            "Kharkiv",
            "Khon Kaen",
            "Kingston",
            "Kobe",
            "Kocaeli",
            "Konya",
            "Kota Baharu",
            "Kota Kinabalu",
            "Kowloon",
            "Krasnodar",
            "Krasnoyarsk",
            "Kuala Lumpur",
            "Kuala Terengganu",
            "Kuantan New Port",
            "Kuching",
            "Kutahya",
            "Kuwait",
            "Kuybyskev",
            "Kyiv",
            "Kyoto",
            "Kyrenia",
            "L'Ariana",
            "La Paz",
            "La Serena",
            "Lansing",
            "Leeds",
            "Lima",
            "Lisbon",
            "Liverpool",
            "London",
            "Los Angeles",
            "Maastricht",
            "Macapa",
            "Maceio",
            "Madison",
            "Madrid",
            "Malatya",
            "Manado",
            "Manama",
            "Manaus",
            "Manchester",
            "Manila",
            "Manisa",
            "Mataram",
            "Medan",
            "Medellin",
            "Melaka",
            "Melbourne",
            "Memphis",
            "Merida",
            "Mersin",
            "Mexico City",
            "Miami",
            "Milan",
            "Milwaukee",
            "Minneapolis",
            "Minsk",
            "Monterrey",
            "Montevideo",
            "Montreal",
            "Morelia",
            "Moscow",
            "Mugla",
            "Mumbai",
            "Munich",
            "Muscat",
            "Nagoya",
            "Naha",
            "Nairobi",
            "Nakhon Pathom",
            "Nakhon Ratchasima",
            "Nashville",
            "Natal",
            "New Delhi",
            "New Orleans",
            "New York",
            "Newark",
            "Nicosia",
            "Niteroi",
            "Nonthaburi",
            "Norfolk",
            "Novosibirsk",
            "Nueva San Salvador",
            "Oakland",
            "Oaxaca",
            "Odesa",
            "Oklahoma City",
            "Omsk",
            "Ordu",
            "Osaka",
            "Ottawa",
            "Pachuca",
            "Padang",
            "Palembang",
            "Palmas",
            "Panama",
            "Paris",
            "Pathum Thani",
            "Pekanbaru",
            "Perm'",
            "Perth",
            "Philadelphia",
            "Phoenix",
            "Phra Nakhon Si Ayutthaya",
            "Phuket",
            "Pinang",
            "Piraeus",
            "Pittsburgh",
            "Pontianak",
            "Port of Spain",
            "Portland",
            "Porto",
            "Porto Alegre",
            "Porto Velho",
            "Prague",
            "Providence",
            "Puebla",
            "Puerto Montt",
            "Puerto Presidente Stroessner",
            "Pune",
            "Pusan",
            "Queretaro",
            "Quezon City",
            "Quito",
            "Raleigh",
            "Ramla",
            "Recife",
            "Richmond",
            "Riga",
            "Rio Branco",
            "Rio de Janeiro",
            "Riyadh",
            "Rize",
            "Rochester",
            "Rome",
            "Rostov-on-Don",
            "Rotterdam",
            "Sacramento",
            "Saint Paul",
            "Saint Petersburg",
            "Sakarya",
            "Salt Lake City",
            "Salvador",
            "Samarinda",
            "Samsun",
            "Samut Prakan",
            "Samut Sakhon",
            "San Antonio",
            "San Cristobal",
            "San Diego",
            "San Francisco",
            "San Jose",
            "San Jose",
            "San Juan",
            "San Luis Potosi",
            "San Salvador",
            "Sanhurfa",
            "Santarem",
            "Santiago",
            "Santo Domingo",
            "Santos",
            "Sao Luis",
            "Sao Paulo",
            "Sapporo",
            "Saratov",
            "Seattle",
            "Semarang",
            "Sendai",
            "Seoul",
            "Seremban",
            "Seville",
            "Seyhan",
            "Shah Alam",
            "Shanghai",
            "Sharjah",
            "Sheffield",
            "Shimonoseki",
            "Singapore",
            "Sitrah",
            "Smolensk",
            "Sofia",
            "Spanish Town",
            "St. Louis",
            "Stockholm",
            "Stuttgart",
            "Surabaja",
            "Sverdlovsk",
            "Sydney",
            "Taipei",
            "Talca",
            "Tallahassee",
            "Tallinn",
            "Tampa",
            "Tampico",
            "Tanjungkarang-Telukbetung",
            "Tekirdag",
            "Tel Aviv-Yafo",
            "Temuco",
            "Teresina",
            "The Hague",
            "Thessaloniki",
            "Tokyo",
            "Toluca",
            "Tomsk",
            "Toronto",
            "Trabzon",
            "Trenton",
            "Tunis",
            "Turin",
            "Tuxtla Gutierrez",
            "Tver",
            "Udon Thani",
            "Ufa",
            "Ul'yanovsk",
            "Utrecht",
            "Valencia",
            "Valparaiso",
            "Vancouver",
            "Venice",
            "Veracruz",
            "Vienna",
            "Villahermosa",
            "Vitoria",
            "Vladivostok",
            "Volgograd",
            "Voronezh",
            "Vyatka",
            "Warsaw",
            "Washington D.C.",
            "Yakutsk",
            "Yaroslavl",
            "Yogyakarta",
            "Yokohama",
            "Zonguldak",
            "Zurich"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_layout, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, CITIES);
        final AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.city_list);
        textView.setAdapter(adapter);

        /*adapter = new ArrayAdapter<String>( getContext(), R.layout.adapter_resource );
        searchList = (ListView)view.findViewById( R.id.search_list);
        searchView = (SearchView) view.findViewById( R.id.searchView);
        searchList.setAdapter( adapter);
        */
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String cityName = textView.getText().toString();
                //searchView.setQuery( cityName, false);
                SavedInformation.getInstance().city = cityName;
                System.out.println(cityName);
            }

        });

/*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList.setVisibility(View.INVISIBLE);
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
            try {
                newText = URLEncoder.encode(newText, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e( "ENCODE", "Error Occured while encoding.");
                e.printStackTrace();
            }
            String url = getString( R.string.backendServer ) + "/cities?query=" + newText;
            // Request a string response from the provided URL.
//                Log.d( "CONNECTION","URL:" + url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
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
                    Log.e("CONNECTION", "Connection Error!");
                    error.printStackTrace();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
            //adapter.getFilter().filter(newText);
            //return false;
            //}
        //});*/
        return view;
    }
}
