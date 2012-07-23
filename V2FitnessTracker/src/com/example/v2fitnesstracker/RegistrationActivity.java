package com.example.v2fitnesstracker;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends Activity {
	
	public final static String USERNAME_PACKAGE = "com.example.v2fitnesstracker.USERNAME";
	public final static String PASSWORD_PACKAGE = "com.example.v2fitnesstracker.PASSWORD";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_registration, menu);
        return true;
    }
    
    public void back(View view) {
    	// Get the Intent that started this Activity
    	Intent backToLogin = new Intent(this, LoginActivity.class);
    	
    	// Close this Activity and start the Activity defined by the Intent
    	finish();
    	startActivity(backToLogin);
    }
    
    public void register(View view) {
    	// An Intent to start an activity called HomeActivity
    	Intent loginIntent = new Intent(this, HomeActivity.class);
    	
    	if(isEmpty(R.id.user_username) || isEmpty(R.id.user_password)) {
    		showErrorMessage("Both username and password fields must not be empty.");
    		return;
    	}
    	
    	// Sets the User properties with the username, password and date registered provided
    	User.setUsername(getTextFromEditText(R.id.user_username));
    	User.setPassword(getTextFromEditText(R.id.user_password));
    	Date registered = new Date();
    	User.setRegistered(registered);
    	
    	// Adds the username, password and date registered to the Intent
    	addToIntent(loginIntent, R.id.user_username, USERNAME_PACKAGE);
    	addToIntent(loginIntent,R.id.user_password, PASSWORD_PACKAGE);
    	loginIntent.putExtra("date_registered", registered);
    	
    	// Finishes this Activity and starts a new one
    	finish();
    	startActivity(loginIntent);
    }
    
 // Shows an error with the message passed in the parameter 
    public void showErrorMessage(String message) {
    	AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(true);
		ad.setMessage(message);
		ad.show();
    }
    
    // Returns the String content of the EditText provided its id
    public String getTextFromEditText(int id) {
    	try {
    		EditText editText = (EditText) findViewById(id);
    		return editText.getText().toString();
    	}
    	catch(ClassCastException e) {
    		return "";
    	}
    }
    
    // Returns false if the content is empty or null
    public void addToIntent(Intent intent, int id, final String pack) {
    	EditText edittext = (EditText)findViewById(id);
    	String content = edittext.getText().toString();
    	intent.putExtra(pack, content);
    }
    
    // Returns true if the View with the id passed in's text is empty or null
    public boolean isEmpty(int id) {
    	EditText edittext = (EditText)findViewById(id);
    	String content = edittext.getText().toString();
    	if(content == null || content.isEmpty()) {
    		return true;
    	}
    	return false;
    }
    
}
