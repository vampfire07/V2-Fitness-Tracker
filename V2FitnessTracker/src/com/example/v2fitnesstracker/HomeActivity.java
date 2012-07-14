package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        String username = intent.getStringExtra(LoginActivity.USERNAME_PACKAGE);
        String password = intent.getStringExtra(LoginActivity.PASSWORD_PACKAGE);        
        
        TextView userDisplay = new TextView(this);
        userDisplay.setTextSize(20);
        userDisplay.setText("Welcome, " + username + "!");
        
        TextView passwordDisplay = new TextView(this);
        passwordDisplay.setText("Password: " + password);
        
        // Create a horizontal LinearLayout and add the Views to it
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(userDisplay);
        layout.addView(passwordDisplay);
        setContentView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    
}
