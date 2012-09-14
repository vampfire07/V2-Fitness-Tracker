package com.example.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.entities.User;

/**
 * @author Marlo Castro
 * This is a service for the Fitness Coach that sends advice
 * based on the current user information.
 */
public class FitnessService extends Service {
	
	private ResponseBuilder responseBuilder;
	
	public FitnessService(User user) {
		if(user != null) {
			responseBuilder = new ResponseBuilder(user);
		}
	}
	
	// Simply returns the String "FitnessService"
	private static final String TAG = FitnessService.class.getSimpleName();
	
	public boolean SERVICE_STATUS_ON = false;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		SERVICE_STATUS_ON = true;
		Log.d(TAG, "Service Created");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		SERVICE_STATUS_ON = false;
		Log.d(TAG, "Service Destroyed");
	}
	
	public String returnMessage() {
		if(responseBuilder != null) {
			return responseBuilder.generateResponse();
		}
		return "";
	}
}

