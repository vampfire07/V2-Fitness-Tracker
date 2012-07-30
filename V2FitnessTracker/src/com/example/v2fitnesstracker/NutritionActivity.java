package com.example.v2fitnesstracker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NutritionActivity extends Activity implements V2Activity {
	
	private final String FIELD_NAME = "NAME";
	private final String FIELD_AMOUNT = "AMOUNT";
	private final String FIELD_CALORIES = "CALORIES";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    	Food newFood = new Food(User.foodId++, "", "", 0);
    	User.addFood(newFood);
    	updateView();
    }
    
    public void removeRow(View view) {
    	LinearLayout parent = (LinearLayout)view.getParent();
    	long id = -1;
    	for(int i = 0; i < parent.getChildCount(); i++) {
    		View child = parent.getChildAt(i);
    		if(child instanceof TextView) {
    			id = Long.parseLong(((TextView) child).getText().toString());
    			break;
    		}
    	}
    	User.removeFood(User.findFoodById(id));
    	parent.removeAllViews();
    	parent.setVisibility(View.GONE);
    }
    
    private void updateView() {
    	TableLayout foodLayout = (TableLayout)findViewById(R.id.nutrition_foodLayout);
    	foodLayout.removeAllViews();
    	for(Food f : User.getFoods()) {
    		foodLayout.addView(createFoodRow(f));
    	}
    }
    
    private TableRow createFoodRow(Food food) {
    	TableRow row = new TableRow(this);
    	row.setBackgroundColor(Color.WHITE);
    	
    	final EditText foodName = createFoodNameView(food);
    	final EditText foodAmount = createFoodAmountView(food);
    	final EditText foodCalories = createFoodCaloriesView(food);
    	setFieldListeners(foodName, foodAmount, foodCalories);
    	
    	View[] views = new View[] { createHiddenIdView(food), foodName, foodAmount,
    			foodCalories, createRemoveButtonView() };
    	addViewsToLayout(views, row);
    	return row;
    }
    
    private EditText createFoodNameView(Food food) {
    	EditText foodName = ActivityFactory.createEditText(this, 12, "Name");
		foodName.setSingleLine(true);
    	foodName.setText(food.getName());
		return foodName;
    }
    
    private EditText createFoodAmountView(Food food) {
    	EditText foodAmount = ActivityFactory.createEditText(this, 12, "Amount");
		foodAmount.setSingleLine(true);
    	foodAmount.setText(food.getAmount());
		return foodAmount;
    }
    
    private EditText createFoodCaloriesView(Food food) {
    	EditText foodCalories = ActivityFactory.createEditText(this, 12, "Calories");
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
    
    private void setFieldListeners(final EditText foodName, final EditText foodAmount, 
    		final EditText foodCalories) {
		setEditTextChangedListener(foodName, FIELD_NAME);
    	setEditTextChangedListener(foodAmount, FIELD_AMOUNT);
    	setEditTextChangedListener(foodCalories, FIELD_CALORIES);
	}
    
    private void setEditTextChangedListener(final EditText text, final String field) {
    	text.addTextChangedListener(new TextWatcher() {
    		public void afterTextChanged(Editable s) {
				try {
					if(field.equals(FIELD_NAME))
						User.findFoodById(findId(text)).setName(text.getText().toString());
					else if(field.equals(FIELD_AMOUNT))
						User.findFoodById(findId(text)).setAmount(text.getText().toString());
					else if(field.equals(FIELD_CALORIES))
						User.findFoodById(findId(text)).setCalories(Integer.parseInt(text.getText().toString()));
				}
				catch(NullPointerException e) {
				}
				catch(NumberFormatException e) {
				}
			}
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
    	});
    }
    
    private long findId(View view) {
		LinearLayout viewParent = (LinearLayout)view.getParent();
		View idView = viewParent.getChildAt(0);
		// The id field is a TextView located at index 0 of the layout.
		if(idView instanceof TextView) {
			return Long.parseLong(((TextView) idView).getText().toString());
		}
		return -1;
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
