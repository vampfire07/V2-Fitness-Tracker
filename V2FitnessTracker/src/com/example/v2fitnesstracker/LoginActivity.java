package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
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

    	if(isEmpty(R.id.username_input) || isEmpty(R.id.password_input)) {
    		showErrorMessage("Both username and password fields must not be empty.");
    		return;
    	}
    	
    	User.setUsername(((EditText)findViewById(R.id.username_input)).getText().toString());
    	User.setPassword(((EditText)findViewById(R.id.password_input)).getText().toString());
    	
    	// Adds the username and password elements' contents to the Intent
    	addToIntent(loginIntent, R.id.username_input, USERNAME_PACKAGE);
    	addToIntent(loginIntent,R.id.password_input, PASSWORD_PACKAGE);
    	
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
    
    // Creates an intent that starts the RegistrationActivity
    public void register(View view) {
    	Intent registerIntent = new Intent(this, RegistrationActivity.class);
    	finish();
    	startActivity(registerIntent);
    }
}