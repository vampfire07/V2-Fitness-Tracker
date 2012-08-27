package com.example.v2fitnesstracker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebook.FacebookConnector;
import com.example.facebook.SessionEvents;

public class FacebookActivity extends Activity {

	private static final String FACEBOOK_APPID = "278245685621907";
	private static final String FACEBOOK_PERMISSION = "publish_stream";
	private static final String TAG = "V2FitnessTracker";
	private static final String MSG = "Message from V2FitnessTracker";

	private final Handler mFacebookHandler = new Handler();
	private TextView loginStatus;
	private FacebookConnector facebookConnector;

    final Runnable mUpdateFacebookNotification = new Runnable() {
        public void run() {
        	Toast.makeText(getBaseContext(), "Facebook updated!", Toast.LENGTH_LONG).show();
        }
    };

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        this.facebookConnector = new FacebookConnector(FACEBOOK_APPID, this, getApplicationContext(), new String[] {FACEBOOK_PERMISSION});
        

        loginStatus = (TextView)findViewById(R.id.facebook_login_status);
        Button postButton = (Button) findViewById(R.id.facebook_btn_post);
        Button clearCredentialsButton = (Button) findViewById(R.id.facebook_btn_clear_credentials);
        
        postButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		postMessage();
            }
        });

        clearCredentialsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Logs the User out of Facebook and updates the login status.
            	clearCredentials();
            	updateLoginStatus();
            }
        });
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.facebookConnector.getFacebook().authorizeCallback(requestCode, resultCode, data);
	}


	@Override
	protected void onResume() {
		super.onResume();
		updateLoginStatus();
	}

	public void updateLoginStatus() {
		loginStatus.setText("Logged into Facebook : " + facebookConnector.getFacebook().isSessionValid());
	}


	private String getFacebookMsg() {
		return MSG + " at " + new Date().toString();
	}	

	public void postMessage() {

		if (facebookConnector.getFacebook().isSessionValid()) {
			postMessageInThread();
		} else {
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {

				@Override
				public void onAuthSucceed() {
					postMessageInThread();
				}

				@Override
				public void onAuthFail(String error) {

				}
			};
			SessionEvents.addAuthListener(listener);
			facebookConnector.login();
		}
	}

	private void postMessageInThread() {
		Thread t = new Thread() {
			public void run() {

		    	try {
		    		facebookConnector.postMessageOnWall(getFacebookMsg());
					mFacebookHandler.post(mUpdateFacebookNotification);
				} catch (Exception ex) {
					Log.e(TAG, "Error sending message.",ex);
				}
		    }
		};
		t.start();
	}

	private void clearCredentials() {
		try {
			facebookConnector.getFacebook().logout(getApplicationContext());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
