package com.example.v2fitnesstracker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

// TO-DO: Add Food to User
// TO-DO: Remove rows
public class NutritionActivity extends Activity implements V2Activity {

	private EditText foodName;
	private EditText amount;
	private EditText calories;
	private TableLayout foodLayout;
	private List<ViewGroup> rows;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rows = new ArrayList<ViewGroup>();
        setPageView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nutrition, menu);
        return true;
    }

	public void setPageView() {
		ScrollView overallLayout = new ScrollView(this);
		LinearLayout linearOverallLayout = createLinearLayout(LinearLayout.VERTICAL);
		View[] views = new View[] { createLogo(), new NavigationFactory(this).createNavigationButtons(), createContentLayout() };
		addViewsToLayout(views, linearOverallLayout);
		overallLayout.addView(linearOverallLayout);
		setContentView(overallLayout);
	}
	
	private TableLayout createContentLayout() {
		foodLayout = new TableLayout(this);
		View[] views = new View[] { createHeader(), createFoodRowView() };
		addViewsToLayout(views, foodLayout);
		return foodLayout;
	}
	
	private TableRow createHeader() {
		TableRow header = new TableRow(this);
    	LinearLayout headerContainer = createLinearLayout(LinearLayout.HORIZONTAL);
    	String[] headerNames = new String[] { "Name", "Amount", "Calories"};
    	TextView[] views = new TextView[headerNames.length];
    	for(int i = 0; i < headerNames.length; i++) {
    		views[i] = ActivityFactory.createTextView(this, 15, headerNames[i]);
    		views[i].setTextColor(Color.RED);
    	}
    	addViewsToLayout(views, headerContainer);
    	header.addView(headerContainer);
    	return header;
	}
	
	private TableRow createFoodRowView() {
    	TableRow row = new TableRow(this);
    	foodName = ActivityFactory.createEditText(this, 12, "Food");
    	amount = ActivityFactory.createEditText(this, 12, "Amount");
    	calories = ActivityFactory.createEditText(this, 12, "Calories");
    	calories.setInputType(InputType.TYPE_CLASS_NUMBER);
    	View[] views = new View[] { foodName, amount, calories, createAddFoodButtonView() };
    	addViewsToLayout(views, row);
    	return row;
    }
	
	private Button createAddFoodButtonView() {
		Button addFoodButton = new Button(this);
		addFoodButton.setText("Add");
    	addFoodButton.setTextSize(10);
    	addFoodButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					foodLayout.addView(createFoodRowView());
//					addFoodToUser(new Food(foodName.getText().toString(), amount.getText().toString(), 
//							Integer.parseInt(calories.getText().toString())));
				}
				catch(NumberFormatException e) {
					showErrorMessage("Please fill in the fields with the correct information.");
				}
			}
    		
    	});
		return addFoodButton;
	}
	
	private void addFoodToUser(Food food) {
    	List<Food> foods = User.getFoods();
    	foods.add(food);
    	User.setFoods(foods);
    }
	
	public void showErrorMessage(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(true);
		ad.setMessage(message);
		ad.show();
	}

	public void addViewsToLayout(View[] views, ViewGroup layout) {
		if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
	}

	public ImageView createImageView(int imageResource, ScaleType scaleType) {
		ImageView view = new ImageView(this);
    	view.setImageResource(imageResource);
    	view.setScaleType(scaleType);
    	return view;
	}

	public ImageView createLogo() {
		return createImageView(R.drawable.logo, ScaleType.FIT_XY);
	}

	public LinearLayout createLinearLayout(int orientation) {
		LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
	}

    
}
