package com.example.tests;

import java.util.HashSet;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entities.Food;
import com.example.entities.User;
import com.example.v2fitnesstracker.NutritionActivity;

public class NutritionActivityTester extends ActivityInstrumentationTestCase2<NutritionActivity> {
	
	NutritionActivity activity;
	
	public NutritionActivityTester(String name) {
		super(NutritionActivity.class);
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
	public void testAddFood() {
		final User user = new User();
//		user.setFoods(new HashSet<Food>());
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Food food = new Food();
				food.setId(1);
				food.setName("Egg");
//				user.addFood(food);
			}
		});
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the click triggered for a new Food to be created
		assertTrue(user.getFoods().size() == 1);
	}
	
	@SmallTest
	public void testRemoveFood() {
		final User user = new User();
//		user.setFoods(new HashSet<Food>());
		Food food = new Food();
		food.setId(1);
		food.setName("Egg");
//		user.addFood(food);
		
		// Asserts that the food was added
		assertTrue(user.getFoods().size() == 1);
		
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
		
		// Asserts that the food was removed
		assertTrue(user.getFoods().size() == 0);
	}
}