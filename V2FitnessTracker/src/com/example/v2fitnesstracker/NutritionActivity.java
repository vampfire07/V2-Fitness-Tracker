package com.example.v2fitnesstracker;

import java.util.HashSet;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
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

import com.example.databases.DatabaseHelper;
import com.example.entities.Food;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class NutritionActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {
	
	private User user;
	
	// Database Access Object (DAO)
	private RuntimeExceptionDao<Food, Integer> dao;
	
	private final String FIELD_NAME = "NAME";
	private final String FIELD_AMOUNT = "AMOUNT";
	private final String FIELD_CALORIES = "CALORIES";
	
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
					Food food = findFoodById(findId(text));
					if(field.equals(FIELD_NAME))
						food.setName(text.getText().toString());
					else if(field.equals(FIELD_AMOUNT))
						food.setAmount(text.getText().toString());
					else if(field.equals(FIELD_CALORIES))
						food.setCalories(Integer.parseInt(text.getText().toString()));
					dao.update(food);
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
    
    // Returns the id of the Food contained in the View.
    private int findId(View view) {
		LinearLayout viewParent = (LinearLayout)view.getParent();
		View idView = viewParent.getChildAt(0);
		// The id field is a TextView located at index 0 of the layout.
		if(idView instanceof TextView) {
			return Integer.parseInt(((TextView) idView).getText().toString());
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
