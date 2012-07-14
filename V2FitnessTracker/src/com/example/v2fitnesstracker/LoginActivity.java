package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity{
	
	public final static String USERNAME_PACKAGE = "com.example.v2fitnesstracker.USERNAME";
	public final static String PASSWORD_PACKAGE = "com.example.v2fitnesstracker.PASSWORD";
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    public void login(View view) {
    	// An Intent to start an activity called HomeActivity
    	Intent loginIntent = new Intent(this, HomeActivity.class);
    	
    	// findViewById() to get the EditText username element and add its content to the intent
    	EditText usernameText = (EditText) findViewById(R.id.username_input);
    	String username = usernameText.getText().toString();
    	loginIntent.putExtra(USERNAME_PACKAGE, username);
    	
    	// findViewById() to get the EditText password element and add its content to the intent
    	EditText passwordText = (EditText) findViewById(R.id.password_input);
    	String password = passwordText.getText().toString();
    	loginIntent.putExtra(PASSWORD_PACKAGE, password);
    	
    	// Finishes this Activity and starts a new one
    	finish();
    	startActivity(loginIntent);
    }
}