package com.example.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

import com.example.v2fitnesstracker.*;

public class NavigationTester extends ActivityInstrumentationTestCase2<HomeActivity> {
	
	HomeActivity activity;
	Button home, exercise, nutrition, journal, logout;
	
	public NavigationTester(String name) {
		super(HomeActivity.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Find views
		activity = getActivity();
		home = (Button)activity.findViewById(R.id.navigation_home);
		exercise = (Button)activity.findViewById(R.id.navigation_exercise);
		nutrition = (Button)activity.findViewById(R.id.navigation_nutrition);
		journal = (Button)activity.findViewById(R.id.navigation_journal);
		logout = (Button)activity.findViewById(R.id.navigation_logout);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testNavigationSetListener() {
		try {
			// Sets the listeners for the buttons
			activity.setNavigationButtons();
			// Asserts that the listeners were called
			assertTrue(home.callOnClick());
			assertTrue(exercise.callOnClick());
			assertTrue(nutrition.callOnClick());
			assertTrue(journal.callOnClick());
			assertTrue(logout.callOnClick());
		}
		catch(Exception e) {
			fail();
		}
	}
}

