package com.saygindogu.saygin.androidplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mainButtonsOnClick(View view){
        Intent intent = null;
        System.out.println( "Button Pressed.");
        if( view.getId() == R.id.loginButton){
           intent = new Intent(this, LoginActivity.class);
        }
        else if( view.getId() == R.id.preferenceButton){
            intent = new Intent( this, PreferenceSelectActivity.class);
        }
        else if( view.getId() == R.id.settingsButton){
            intent = new Intent(this, SettingsActivity.class);
        }
        if( intent != null){
            startActivity(intent);
        }
    }

}
