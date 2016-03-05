package com.saygindogu.saygin.androidplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButtonsOnClick( View view){
        if( view.getId() == R.id.foursquareButton){
            System.out.println( "Foursquare Button Pressed");
        }
    }
}
