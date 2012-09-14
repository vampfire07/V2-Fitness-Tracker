package com.example.v2fitnesstracker;

import java.util.HashSet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.databases.DatabaseHelper;
import com.example.entities.Food;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class NutritionActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {
	
	private User user;
	
	// Database Access Object (DAO)
	private RuntimeExceptionDao<Food, Integer> dao;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = HomeActivity.user;
        dao = getHelper().getRuntimeFoodDao();
        setContentView(R.layout.activity_nutrition);
        setNavigationButtons();
        updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nutrition, menu);
        return true;
    }
    
    public void addRow(View view) {
    	Food newFood = new Food("", "", 0);
    	newFood.setUser(user);
    	dao.create(newFood);
    	updateView();
    }
    
    public void removeRow(View view) {
    	LinearLayout parent = (LinearLayout)view.getParent();
    	int id = -1;
    	for(int i = 0; i < parent.getChildCount(); i++) {
    		View child = parent.getChildAt(i);
    		if(child instanceof TextView) {
    			id = Integer.parseInt(((TextView) child).getText().toString());
    			break;
    		}
    	}
    	dao.delete(findFoodById(id));
    	parent.removeAllViews();
    	parent.setVisibility(View.GONE);
    }
    
    private Food findFoodById(int id) {
    	for(Food f : dao.queryForAll()) {
    		if(f.getUser().getId() == user.getId()) {
    			if(f.getId() == id) return f;
    		}
    	}
    	return null;
    }
    
    private void updateView() {
    	TableLayout foodLayout = (TableLayout)findViewById(R.id.nutrition_foodLayout);
    	foodLayout.removeAllViews();
    	user.setFoodSet(new HashSet<Food>());
    	for(Food f : dao.queryForAll()) {
    		if(f.getUser().getId() == user.getId()) 
    			foodLayout.addView(createFoodRow(f));
    		user.getFoodSet().add(f);
    	}
    }
    
    private TableRow createFoodRow(Food food) {
    	TableRow row = new TableRow(this);
    	row.setBackgroundColor(Color.WHITE);
    	
    	TextView foodName = createFoodNameView(food);
    	TextView foodAmount = createFoodAmountView(food);
    	TextView foodCalories = createFoodCaloriesView(food);
    	
    	View[] views = new View[] { createHiddenIdView(food), foodName, foodAmount,
    			foodCalories, createEditButtonView(food), createRemoveButtonView() };
    	addViewsToLayout(views, row);
    	return row;
    }
    
    private TextView createFoodNameView(Food food) {
    	TextView foodName = ActivityFactory.createTextView(this, 14, "Unnamed");
    	foodName.setPadding(3, 0, 3, 0);
		if(food.getName().equals("")) foodName.setText("Unnamed");
		else foodName.setText(food.getName());
		foodName.setSingleLine(true);
		return foodName;
    }
    
    private TextView createFoodAmountView(Food food) {
    	TextView foodAmount = ActivityFactory.createTextView(this, 14, "Not specified");
    	foodAmount.setPadding(3, 0, 3, 0);
		foodAmount.setSingleLine(true);
    	foodAmount.setText(food.getAmount());
		return foodAmount;
    }
    
    private TextView createFoodCaloriesView(Food food) {
    	TextView foodCalories = ActivityFactory.createTextView(this, 14, "");
    	foodCalories.setPadding(3, 0, 3, 0);
		foodCalories.setSingleLine(true);
		foodCalories.setInputType(InputType.TYPE_CLASS_NUMBER);
    	foodCalories.setText(food.getCalories() + "");
		return foodCalories;
    }
    
    private TextView createHiddenIdView(Food food) {
    	TextView id = ActivityFactory.createTextView(this, 10, food.getId() + ""); 
    	id.setVisibility(View.GONE);
    	return id;
    }
    
    private Button createEditButtonView(final Food food) {
    	final Context context = this;
		Button editButton = new Button(this);
		editButton.setText("Edit");
    	editButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent navigateIntent = new Intent(context, ViewFoodActivity.class);
				navigateIntent.putExtra("food_extra", food);
				startActivity(navigateIntent);
				finish();
			}
		});
    	return editButton;
	}
    
    private Button createRemoveButtonView() {
		Button removeButton = new Button(this);
		removeButton.setText("Remove");
    	removeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removeRow(v);
			}
		});
    	return removeButton;
	}
    
    public void setNavigationButtons() {
    	Button home = (Button)findViewById(R.id.navigation_home);
    	Button exercise = (Button)findViewById(R.id.navigation_exercise);
    	Button nutrition = (Button)findViewById(R.id.navigation_nutrition);
    	Button journal = (Button)findViewById(R.id.navigation_journal);
    	Button logout = (Button)findViewById(R.id.navigation_logout);
    	new NavigationFactory(this).setNavigationOnClick(home, exercise, nutrition, journal, logout);
    }
    
	// Shows an AlertDialog with the message passed in the parameter
	public void showAlertMessage(String message, boolean cancelable) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(cancelable);
		ad.setMessage(message);
		ad.show();
	}

	// Adds the View elements inside the array into the layout
	public void addViewsToLayout(View[] views, ViewGroup layout) {
		if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
	}

	// Returns a LinearLayout with the orientation passed in as parameter
	public LinearLayout createLinearLayout(int orientation) {
		LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
	}
}
