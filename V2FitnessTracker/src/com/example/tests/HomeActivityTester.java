package com.example.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;

import com.example.v2fitnesstracker.*;

public class HomeActivityTester extends ActivityInstrumentationTestCase2<HomeActivity> {
	
	HomeActivity activity;
	EditText username, password, age, heightFeet, heightInches, weight, goalWeight;
	EditText BMI_index, BMI_classification;
	
	public HomeActivityTester(String name) {
		super(HomeActivity.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Find views
		activity = getActivity();
		username = (EditText)activity.findViewById(R.id.home_username);
		password = (EditText)activity.findViewById(R.id.home_password);
		age = (EditText)activity.findViewById(R.id.home_age);
		heightFeet = (EditText)activity.findViewById(R.id.home_heightFeet);
		heightInches = (EditText)activity.findViewById(R.id.home_heightInches);
		weight = (EditText)activity.findViewById(R.id.home_weight);
		goalWeight = (EditText)activity.findViewById(R.id.home_goalWeight);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testUpdate() {
		User.setUsername("Bob");
		User.setPassword("password123");
		User.setAge(22);
		User.setHeight_feet(6);
		User.setHeight_inches(4);
		User.setWeight(190);
		User.setGoal_weight(210);
		
		// Check to see if the setters work
		assertEquals(User.getUsername(), "Bob");
		assertEquals(User.getPassword(), "password123");
		assertEquals(User.getAge(), 22);
		assertEquals(User.getHeight_feet(), 6);
		assertEquals(User.getHeight_inches(), 4);
		assertEquals(User.getWeight(), 190);
		assertEquals(User.getGoal_weight(), 210);
	}
	
	@SmallTest
	public void testInitializeInformation() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				User.setUsername("Bob");
				User.setPassword("password123");
				User.setAge(22);
				User.setHeight_feet(6);
				User.setHeight_inches(4);
				User.setWeight(190);
				User.setGoal_weight(210);
				
				activity.initializeInformation();
				
				// Checks if the method initializeInformation was successfully executed
				assertEquals(username.getText().toString(), "Bob");
				assertEquals(password.getText().toString(), "password123");
				assertEquals(Integer.parseInt(age.getText().toString()), 22);
				assertEquals(Integer.parseInt(heightFeet.getText().toString()), 6);
				assertEquals(Integer.parseInt(heightInches.getText().toString()), 4);
				assertEquals(Integer.parseInt(weight.getText().toString()), 190);
				assertEquals(Integer.parseInt(goalWeight.getText().toString()), 210);
			}
		});
		getInstrumentation().waitForIdleSync();
	}
	
	@SmallTest
	public void testCalculateBMI() {
		User.setWeight(135);
		User.setHeight_feet(5);
		User.setHeight_inches(6);
		double index = activity.calculateBMIIndex();
		String classification = activity.calculateBMIClassification(index);
		// Index should be 21.8
		assertEquals(index, 21.8);
		// Classification should be Normal
		assertEquals(classification, "Normal");
		
		User.setHeight_feet(6);
		index = activity.calculateBMIIndex();
		classification = activity.calculateBMIClassification(index);
		// Index should be 15.6
		assertEquals(index, 15.6);
		// Classification should be Underweight
		assertEquals(classification, "Underweight");
		
		User.setHeight_feet(5);
		User.setHeight_inches(0);
		index = activity.calculateBMIIndex();
		classification = activity.calculateBMIClassification(index);
		// Index should be 26.4
		assertEquals(index, 26.4);
		// Classification should be Overweight
		assertEquals(classification, "Overweight");
		
		User.setHeight_feet(4);
		index = activity.calculateBMIIndex();
		classification = activity.calculateBMIClassification(index);
		// Index should be 41.2
		assertEquals(index, 41.2);
		// Classification should be Obese
		assertEquals(classification, "Obese");
	}
}

