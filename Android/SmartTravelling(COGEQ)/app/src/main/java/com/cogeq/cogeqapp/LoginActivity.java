package com.cogeq.cogeqapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "Connection";
    private static final String CLIENT_ID = "XWWK4XOYWX3J1XDRN43LL2V0EB41OFCDF3EBZ1CZIABKA1DL";
    private static final String CLIENT_SECRET = "MGDCFYKO2SZP0TNPWOI4KJ2P5GVHRTWUIAB4O0I25PBH2BAS";
    private static final int REQUEST_CODE_FSQ_CONNECT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButtonsOnClick( View view){
        if( view.getId() == R.id.foursquareButton){
            Log.d( "LOG_IN", "Foursquare Button Pressed");
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            Intent intent = FoursquareOAuth.getConnectIntent( this, CLIENT_ID);
            startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
                /* ... */
                codeResponse.getException();
                Log.d( "LOG_IN", "Code:" + codeResponse.getCode());
                String url = getString(R.string.backendServer) + "/login?code=";
                url += codeResponse.getCode();
                Log.d( "CONNECTION", "URL:" + url);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                try {
                                    SavedInformation.getInstance().accessToken = response.getString("access_token");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d( "SUCCESS" , "Access Token: " + SavedInformation.getInstance().accessToken);
                                Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e( "CONNECTION", "Error Occured in connection");
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });
                queue.add( jsonObjReq);
                break;
        }
    }

//    public void signUpButtonPressed( View view){
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//    }
}
