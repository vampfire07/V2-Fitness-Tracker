package com.example.tests;

import java.util.HashSet;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entities.Exercise;
import com.example.entities.User;
import com.example.v2fitnesstracker.ExerciseActivity;

public class ExerciseActivityTester extends ActivityInstrumentationTestCase2<ExerciseActivity> {
	
	ExerciseActivity activity;
	
	public ExerciseActivityTester(String name) {
		super(ExerciseActivity.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Find views
		activity = getActivity();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testAddExercise() {
		final User user = new User();
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Exercise exercise = new Exercise();
				exercise.setId(1);
				exercise.setName("Bicep Curl");
				exercise.setType("Biceps");
			}
		});
		// Asserts that the user Exercises is empty
		assertTrue(user.getExercises().size() == 0);
		
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the click triggered for a new Exercise to be created
		assertTrue(user.getExercises().size() == 1);
	}
	
	@SmallTest
	public void testRemoveExercise() {
		final User user = new User();
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("Bicep Curl");
		exercise.setType("Biceps");
//		user.setExercises(new HashSet<Exercise>());
//		user.addExercise(exercise);
		
		// Asserts that the exercise was added
		assertTrue(user.getExercises().size() == 1);
		
		LinearLayout layout = new LinearLayout(activity);
		TextView id = new TextView(activity);
		id.setText("1");
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				activity.removeRow(v);
			}
		});
		layout.addView(id);
		layout.addView(button);
		
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the exercise was removed
		assertTrue(user.getExercises().size() == 0);
	}
}

