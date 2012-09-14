package com.example.v2fitnesstracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.DatabaseHelper;
import com.example.entities.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class ViewFoodActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {

	// Database Access Object (DAO)
	private RuntimeExceptionDao<Food, Integer> dao;
	
	private final String FIELD_NAME = "NAME";
	private final String FIELD_AMOUNT = "AMOUNT";
	private final String FIELD_CALORIES = "CALORIES";
	
	private Food food;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = getHelper().getRuntimeFoodDao();
        setContentView(R.layout.activity_view_food);
        food = (Food)getIntent().getSerializableExtra("food_extra");
        initializeContents();
    }
    
    private void initializeContents() {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.viewFood_foodLayout);
    	
    	final EditText foodNameEdit = createFoodNameEdit(food);
    	final EditText foodAmountEdit = createFoodAmountEdit(food);
    	final EditText foodCaloriesEdit = createFoodCaloriesEdit(food);
    	setFieldListeners(foodNameEdit, foodAmountEdit, foodCaloriesEdit);
    	
    	final LinearLayout foodName = createNameView(foodNameEdit);
    	final LinearLayout foodSets = createAmountView(foodAmountEdit);
    	final LinearLayout foodReps = createCaloriesView(foodCaloriesEdit);
    	Button doneButton = createDoneButton();
    	Button backButton = createBackButton();
    	
    	View[] views = new View[] { foodName,
    								foodSets,
    								foodReps,
    								doneButton,
    								backButton};
    	addViewsToLayout(views, layout);
    }
    
    private void backToFoodActivity() {
    	final Context context = this;
    	startActivity(new Intent(context, NutritionActivity.class));
    	finish();
    }

	private Button createDoneButton() {
		Button doneButton = new Button(this);
    	doneButton.setText("Done");
    	doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dao.update(food);
				backToFoodActivity();
			}
		});
		return doneButton;
	}
	
	private Button createBackButton() {
		Button doneButton = new Button(this);
    	doneButton.setText("Back");
    	doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backToFoodActivity();
			}
		});
		return doneButton;
	}

	private LinearLayout createCaloriesView(EditText foodReps) {
		LinearLayout caloriesLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView caloriesText = ActivityFactory.createTextView(this, 20, "Calories: ");
    	caloriesText.setTextColor(Color.RED);
    	View[] views = new View[] { caloriesText, foodReps };
    	addViewsToLayout(views, caloriesLayout);
		return caloriesLayout;
	}
	
	private EditText createFoodCaloriesEdit(Food food) {
		final EditText calories = ActivityFactory.createEditText(this, 18, "Reps");
    	calories.setInputType(InputType.TYPE_CLASS_NUMBER);
    	calories.setText(food.getCalories() + "");
		return calories;
	}

	private LinearLayout createAmountView(EditText foodSets) {
		LinearLayout amountLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView amountText = ActivityFactory.createTextView(this, 20, "Amount: ");
    	amountText.setTextColor(Color.RED);
    	View[] views = new View[] { amountText, foodSets };
    	addViewsToLayout(views, amountLayout);
		return amountLayout;
	}
    
	private EditText createFoodAmountEdit(Food food) {
		final EditText amount = ActivityFactory.createEditText(this, 18, "Amount");
    	amount.setText(food.getAmount());
		return amount;
	}

	private LinearLayout createNameView(EditText foodName) {
		LinearLayout nameLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView nameText = ActivityFactory.createTextView(this, 20, "Name: ");
    	nameText.setTextColor(Color.RED);
    	View[] views = new View[] { nameText, foodName };
    	addViewsToLayout(views, nameLayout);
		return nameLayout;
	}
    
    private EditText createFoodNameEdit(Food food) {
    	EditText nameEdit = ActivityFactory.createEditText(this, 18, "Name");
    	nameEdit.setText(food.getName());
    	return nameEdit;
	}
    
    private void setFieldListeners(final EditText foodName, final EditText foodAmount, final EditText foodCalories) {
		setEditTextChangedListener(foodName, FIELD_NAME);
    	setEditTextChangedListener(foodAmount, FIELD_AMOUNT);
    	setEditTextChangedListener(foodCalories, FIELD_CALORIES);
	}
    
    private void setEditTextChangedListener(final EditText text, final String field) {
    	final Context context = this; 
    	text.addTextChangedListener(new TextWatcher() {
    		public void afterTextChanged(Editable s) {
				try {
					if(field.equals(FIELD_NAME)) {
						if(text.getText().toString().length() > 10) {
							Toast.makeText(context, "Name can only be up to 10 characters long.", Toast.LENGTH_LONG).show();
							text.setText(text.getText().toString().substring(0, 10));
							return;
						}
				    	else food.setName(text.getText().toString());
					}
					else if(field.equals(FIELD_AMOUNT)) {
						if(text.getText().toString().length() > 10) {
							Toast.makeText(context, "Amount can only be up to 10 characters long.", Toast.LENGTH_LONG).show();
							text.setText(text.getText().toString().substring(0, 10));
							return;
						}
						else food.setAmount(text.getText().toString());
					}
					else if(field.equals(FIELD_CALORIES)) {
						int calories = Integer.parseInt(text.getText().toString());
						if(calories < -9999 || calories > 9999) {
							Toast.makeText(context, "You cannot store that value for calories. Please try again.", Toast.LENGTH_LONG).show();
							text.setText(text.getText().toString().substring(0, 4));
							return;
						}
						else food.setCalories(calories);
					}
				}
				catch(NullPointerException e) {
					Log.w("FoodActivity", "NullPointerException caught.");
				}
				catch(NumberFormatException e) {
					Log.w("FoodActivity", "NumberFormatException caught.");
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
    
    // Returns a LinearLayout with the orientation passed in as parameter
 	public LinearLayout createLinearLayout(int orientation) {
 		LinearLayout view = new LinearLayout(this);
     	view.setOrientation(orientation);
     	return view;
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
     
    public void setNavigationButtons() {
    	Button home = (Button)findViewById(R.id.navigation_home);
    	Button food = (Button)findViewById(R.id.navigation_exercise);
    	Button nutrition = (Button)findViewById(R.id.navigation_nutrition);
    	Button journal = (Button)findViewById(R.id.navigation_journal);
    	Button logout = (Button)findViewById(R.id.navigation_logout);
    	new NavigationFactory(this).setNavigationOnClick(home, food, nutrition, journal, logout);
 	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_food, menu);
        return true;
    }

}
