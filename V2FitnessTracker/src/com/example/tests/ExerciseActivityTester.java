package com.example.tests;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.v2fitnesstracker.*;

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
		User.setExercises(new ArrayList<Exercise>());
		final Spinner spinner = activity.createSpinner(R.array.exercise_types);
		// 2nd selection is Biceps
		spinner.setSelection(1);
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				User.addExercise(new Exercise(1, "Bicep Curl", spinner.getSelectedItem().toString(), 3, 20));
			}
		});
		// Asserts that the user Exercises is empty
		assertTrue(User.getExercises().size() == 0);
		
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the click triggered for a new Exercise to be created
		assertTrue(User.getExercises().size() == 1);
		assertTrue(User.getExercises().get(0).getName().equals("Bicep Curl"));
		assertTrue(User.getExercises().get(0).getType().equals("Biceps"));
		assertTrue(User.getExercises().get(0).getSets() == 3);
		assertTrue(User.getExercises().get(0).getReps() == 20);
	}
	
	@SmallTest
	public void testRemoveExercise() {
		User.setExercises(new ArrayList<Exercise>());
		Exercise exercise = new Exercise(1, "Bicep Curl", "Biceps", 3, 20);
		User.addExercise(exercise);
		
		// Asserts that the exercise was added
		assertTrue(User.getExercises().size() == 1);
		
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
		assertTrue(User.getExercises().size() == 0);
	}
}

