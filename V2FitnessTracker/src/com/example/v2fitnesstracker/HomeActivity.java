package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class HomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Get the Intent that started this Activity
        Intent intent = getIntent();
        // Get the message from that Intent
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        
        // Create the TextView
        TextView userDisplay = new TextView(this);
        userDisplay.setTextSize(20);
        userDisplay.setText("Welcome, " + message + "!");
        
        setContentView(userDisplay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    
}
