package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    	
    	AlertDialog ad = new AlertDialog.Builder(this).create();
    	boolean usernameEmpty = false;
    	boolean passwordEmpty = false;
    	if(username == null || username.isEmpty()) {
    		ad.setCancelable(false);
    		ad.setMessage("Username cannot be empty.");
    		usernameEmpty = true;
    	}
    	if(password == null || password.isEmpty()) {
    		if(usernameEmpty) ad.setMessage("Username and password cannot be empty.");
    		else ad.setMessage("Password cannot be empty.");
    		passwordEmpty = true;
		}
    	
    	if(usernameEmpty || passwordEmpty) {
    		ad.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
				}
    		});
    		ad.show();
    		return;
    	}
    	
    	// Finishes this Activity and starts a new one
    	finish();
    	startActivity(loginIntent);
    }
    
    public void register(View view) {
    	Intent registerIntent = new Intent(this, RegistrationActivity.class);
    	finish();
    	startActivity(registerIntent);
    }
}