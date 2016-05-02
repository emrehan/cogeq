package com.cogeq.cogeqapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //testy

    }
    public void launcherButtonsClick( View view){
        Intent intent = null;
        if( view.getId() == R.id.loginButton){
            intent = new Intent(this, LoginActivity.class);
        }
        if( view.getId() == R.id.mainActivityButton){
            intent = new Intent( this, MainActivity.class);
        }
        if( intent != null){
            startActivity(intent);
        }
    }

}
