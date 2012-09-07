package com.example.v2fitnesstracker;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.databases.DatabaseHelper;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class LoginActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	
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

    public void login(View view) throws IOException, ClassNotFoundException {
    	// An Intent to start an activity called HomeActivity
    	Intent loginIntent = new Intent(this, HomeActivity.class);
    	String username = ((EditText)findViewById(R.id.username_input)).getText().toString();
    	String password = ((EditText)findViewById(R.id.password_input)).getText().toString();
    	RuntimeExceptionDao<User, Integer> dao = getHelper().getRuntimeUserDao();
    	
    	// Checks for empty fields
    	if(username.length() == 0 || password.length() == 0) {
    		showErrorMessage("Both username and password fields must not be empty.");
    		return;
    	}
    	
    	User user = null;
    	
    	// Checks for an existing username
    	List<User> users = dao.queryForAll();
    	boolean exists = false;
    	boolean passwordMatches = false;
    	for(User u : users) {
    		if(u.getUsername().equalsIgnoreCase(username)) {
    			user = u;
    			exists = true;
    			if(u.getPassword().equals(password)) passwordMatches = true;
    			break;
    		}
    	}
    	// If username does not exist, do not log in
    	if(!exists) {
    		showErrorMessage("Username does not exist");
    		return;
    	}
    	else if(exists && !passwordMatches) {
    		showErrorMessage("Password does not match the username");
    		return;
    	}
    	
    	loginIntent.putExtra("user_extra", user);
    	
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
    	if(content == null || content.length() == 0) {
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