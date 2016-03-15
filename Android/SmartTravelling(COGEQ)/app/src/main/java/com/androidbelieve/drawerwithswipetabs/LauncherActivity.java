package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

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
