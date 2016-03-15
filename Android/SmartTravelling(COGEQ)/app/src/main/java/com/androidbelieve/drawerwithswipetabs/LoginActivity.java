package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

public class LoginActivity extends AppCompatActivity {

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
            System.out.println("Foursquare Button Pressed");
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
                System.out.println( "Code:" + codeResponse.getCode());
                break;
        }
    }

//    public void signUpButtonPressed( View view){
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//    }
}
