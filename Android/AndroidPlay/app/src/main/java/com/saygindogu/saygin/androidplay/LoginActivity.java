package com.saygindogu.saygin.androidplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.foursquare.android.nativeoauth.FoursquareOAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "XWWK4XOYWX3J1XDRN43LL2V0EB41OFCDF3EBZ1CZIABKA1DL";
    private static final String CLIENT_SECRET = "MGDCFYKO2SZP0TNPWOI4KJ2P5GVHRTWUIAB4O0I25PBH2BAS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButtonsOnClick( View view){
        if( view.getId() == R.id.foursquareButton){
            System.out.println("Foursquare Button Pressed");
            FoursquareOAuth.getConnectIntent( this, CLIENT_ID);

        }
    }
}
