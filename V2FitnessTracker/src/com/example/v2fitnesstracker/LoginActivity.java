package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.v2fitnesstracker.MESSAGE";

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

    public void sendMessage(View view) {
    	// An Intent to start an activity called HomeActivity
    	Intent goToHome = new Intent(this, HomeActivity.class);
    	
    	// findViewById() to get the EditText element and add its message to the intent
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	goToHome.putExtra(EXTRA_MESSAGE, message);
    	startActivity(goToHome);
    }
}
