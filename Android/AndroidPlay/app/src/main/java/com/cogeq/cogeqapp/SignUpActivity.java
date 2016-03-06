package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.cogeq.cogeqapp.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void okButtonPressed( View view){
        EditText nameEditText = (EditText)findViewById(R.id.signUp_nameEditText);
        EditText surnameEditText = (EditText)findViewById(R.id.signUp_surnameEditText);
        EditText usernameEditText = (EditText)findViewById(R.id.signUp_usernameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.signUp_passwordEditText);
        /*TODO:
         Check Fields are filled
         Check username validity by connecting the server
         Hash the password
         Create user and send it to the server.
         */
        User user = new User(nameEditText.getText().toString(), surnameEditText.getText().toString(), usernameEditText.getText().toString(),passwordEditText.getText().toString());
    }

}
