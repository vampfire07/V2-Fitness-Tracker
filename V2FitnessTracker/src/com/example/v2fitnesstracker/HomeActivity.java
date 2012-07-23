package com.example.v2fitnesstracker;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	
	private EditText age;
	private EditText height_feet;
	private EditText height_inches;
	private EditText weight;
	private EditText goal_weight;
	private EditText _BMIIndex;
	private EditText _BMIClassification;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setPageView();
        
    }
    
    // Updates the user information (age, weight, goal weight, height)
    private void update() {
    	try {
	    	User.setAge(Integer.parseInt(age.getText().toString()));
	    	User.setWeight(Integer.parseInt(weight.getText().toString()));
	    	User.setGoal_weight(Integer.parseInt(goal_weight.getText().toString()));
	    	User.setHeight_feet(Integer.parseInt(height_feet.getText().toString()));
	    	User.setHeight_inches(Integer.parseInt(height_inches.getText().toString()));
    	}
    	catch(NumberFormatException e) {
    		showErrorMessage("Make sure the information are filled in correctly.");
    	}
    }
    
    // Creates the overall layout of the page, adds all the View to it, and sets it as the page content
    private void setPageView() {
    	ScrollView overallLayout = new ScrollView(this);
    	LinearLayout linearOverallLayout = createLinearLayout(LinearLayout.VERTICAL);
    	View[] views = new View[] { new NavigationFactory(this).createNavigationButtons(), createContentLayout() };
    	addViewsToLayout(views, linearOverallLayout);
    	overallLayout.addView(linearOverallLayout);
        setContentView(overallLayout);
    }
    
    private LinearLayout createContentLayout() {
    	LinearLayout contentLayout = (LinearLayout) createLinearLayout(LinearLayout.VERTICAL);
    	View[] views = new View[] { createCredentialsView(), createUserInfoView(), createBMIView() };
    	addViewsToLayout(views, contentLayout);
    	return contentLayout;
    }
    
    private void calculateBMI() {
    	double index = calculateBMIIndex();
    	_BMIIndex.setText(index + "");
    	_BMIClassification.setText(calculateBMIClassification(index));
    }
    
    private double calculateBMIIndex() {
    	double index = 0;
    	if(_BMIIndex != null) {
    		if(User.getHeight_feet() != 0 && User.getHeight_inches() != 0 && User.getWeight() != 0) {
    			double BMI_formula = (User.getWeight() / Math.pow(((User.getHeight_feet() * 12) + User.getHeight_inches()), 2)) * 703;
    			index = Double.parseDouble(new DecimalFormat("#0.0").format(BMI_formula));
    		}
    	}
    	return index;
    }
    
    private String calculateBMIClassification(double index) {
    	String classification = "";
    	if(_BMIClassification != null) {
    		if(index < 18.5) classification = "Underweight";
    		else if(index >= 18.5 && index < 25) classification = "Normal";
    		else if(index >= 25 && index < 30) classification = "Overweight";
    		else if(index >= 30) classification = "Obese";
    	}
    	return classification;
    }
   
    private LinearLayout createBMIView() {
    	LinearLayout BMILayout = (LinearLayout) createLinearLayout(LinearLayout.VERTICAL);
    	TextView BMIDisplay = (TextView) createText(Views.TEXTVIEW, 20, "BMI", "");
    	Button calculateButton = new Button(this);
    	calculateButton.setText("Calculate");
    	calculateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				calculateBMI();
			}
    	});
    	View[] views = new View[] { BMIDisplay, createBMIIndexView(), createBMIClassificationView(), calculateButton };
    	addViewsToLayout(views, BMILayout);
    	return BMILayout;
    }
    
    private LinearLayout createBMIIndexView() {
    	TextView indexDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Index: ", "");
    	_BMIIndex = (EditText) createText(Views.EDITTEXT, 15, "", "");
    	_BMIIndex.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	// Makes the EditText non-editable.
    	_BMIIndex.setKeyListener(null);
    	View[] views = new View[] { indexDisplay, _BMIIndex };
    	LinearLayout BMIIndexLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	addViewsToLayout(views, BMIIndexLayout);
    	return BMIIndexLayout;
    }
    
    private LinearLayout createBMIClassificationView() {
    	TextView classificationDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Classification:", "");
    	_BMIClassification = (EditText) createText(Views.EDITTEXT, 15, "", "");
    	View[] views = new View[] { classificationDisplay, _BMIClassification };
    	LinearLayout BMIClassificationLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	addViewsToLayout(views, BMIClassificationLayout);
    	return BMIClassificationLayout;
    }
    
    private LinearLayout createUserInfoView() {
    	LinearLayout userInfoLayout = (LinearLayout) createLinearLayout(LinearLayout.VERTICAL);
    	Button updateButton = new Button(this);
    	updateButton.setText("Update");
    	updateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				update();
			}
    	});
    	View[] views = new View[] { createAgeView(), createHeightView(), createWeightView(), createGoalWeightView(), updateButton };
    	addViewsToLayout(views, userInfoLayout);
    	return userInfoLayout;
    }
    
    private LinearLayout createGoalWeightView() {
    	TextView goalWeightDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Goal Weight: ", "");
    	goal_weight = (EditText) createText(Views.EDITTEXT, 15, User.getGoal_weight() + "", "");
    	goal_weight.setInputType(InputType.TYPE_CLASS_NUMBER);
    	TextView lbsDisplay = (TextView) createText(Views.TEXTVIEW, 15, "lbs.", "");
    	View[] views = new View[] { goalWeightDisplay, goal_weight, lbsDisplay };
    	
    	LinearLayout goalWeightLayout = (LinearLayout) createLinearLayout(LinearLayout.HORIZONTAL);
    	addViewsToLayout(views, goalWeightLayout);
    	return goalWeightLayout;
    }
    
    private LinearLayout createWeightView() {
    	TextView weightDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Weight: ", "");
    	weight = (EditText) createText(Views.EDITTEXT, 15, User.getWeight() + "", "");
    	weight.setInputType(InputType.TYPE_CLASS_NUMBER);
    	TextView lbsDisplay = (TextView) createText(Views.TEXTVIEW, 15, "lbs.", "");
    	View[] views = new View[] { weightDisplay, weight, lbsDisplay };
    	
    	LinearLayout weightLayout = (LinearLayout) createLinearLayout(LinearLayout.HORIZONTAL);
    	addViewsToLayout(views, weightLayout);
    	return weightLayout;
    }
    
    private LinearLayout createHeightView() {
    	TextView heightDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Height: ", "");
    	height_feet = (EditText) createText(Views.EDITTEXT, 15, User.getHeight_feet() + "", "");
    	height_feet.setInputType(InputType.TYPE_CLASS_NUMBER);
    	TextView feetDisplay = (TextView) createText(Views.TEXTVIEW, 15, "ft.", "");
    	height_inches = (EditText) createText(Views.EDITTEXT, 15, User.getHeight_inches() + "", "");
    	height_inches.setInputType(InputType.TYPE_CLASS_NUMBER);
    	TextView inchesDisplay = (TextView) createText(Views.TEXTVIEW, 15, "in.", "");
    	View[] views = new View[] { heightDisplay, height_feet, feetDisplay, height_inches, inchesDisplay };
    	
    	LinearLayout heightLayout = (LinearLayout) createLinearLayout(LinearLayout.HORIZONTAL);
    	addViewsToLayout(views, heightLayout);
    	return heightLayout;
    }
    
    private LinearLayout createAgeView() {
    	TextView ageDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Age: ", "");
    	age = (EditText) createText(Views.EDITTEXT, 15, User.getAge() + "", "");
    	age.setInputType(InputType.TYPE_CLASS_NUMBER);
    	View[] views = new View[] { ageDisplay, age };
    	
    	LinearLayout ageLayout = (LinearLayout) createLinearLayout(LinearLayout.HORIZONTAL);
    	addViewsToLayout(views, ageLayout);
    	return ageLayout;
    }
    
    private LinearLayout createCredentialsView() {
    	ImageView logo = createImageView(R.drawable.logo, ScaleType.FIT_XY);
        TextView userDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Username: " + User.getUsername(), "");
        TextView passwordDisplay = (TextView) createText(Views.TEXTVIEW, 15, "Password: " + User.getPassword(), "");
        View[] views = new View[] { logo, userDisplay, passwordDisplay };
        
        // Create a horizontal LinearLayout and add the Views to it
        LinearLayout credentialsLayout = (LinearLayout) createLinearLayout(LinearLayout.VERTICAL);
        addViewsToLayout(views, credentialsLayout);
        return credentialsLayout;
    }
    
    // Adds the View elements in the array into the layout
    private void addViewsToLayout(View[] views, ViewGroup layout) {
    	if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
    }

    /*
     * Creates a View and returns that created View. 
     * If creating a TextView, pass in "" for hint.
     * If creating an EditText, pass in "" for text.
     * Returns null if no View was created.
     */
    private View createText(Views views, int textSize, String text, String hint) {
    	View view = null;
    	if(views == Views.TEXTVIEW) {
    		view = new TextView(this);
    		((TextView) view).setTextSize(textSize);
    		((TextView) view).setText(text);
    	}
    	else if(views == Views.EDITTEXT) {
    		view = new EditText(this);
    		((EditText) view).setTextSize(textSize);
    		((EditText) view).setHint(text);
    	}
    	return view;
    }
    
    // Creates a View and returns that created View. 
    private LinearLayout createLinearLayout(int orientation) {
    	LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
    }
    
    // Creates a View and returns that created View. 
    private ImageView createImageView(int imageResource, ImageView.ScaleType scaleType) {
    	ImageView view = new ImageView(this);
    	view.setImageResource(imageResource);
    	view.setScaleType(scaleType);
    	return view;
    }
    
    // Shows an error with the message passed in the parameter 
    public void showErrorMessage(String message) {
    	AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(true);
		ad.setMessage(message);
		ad.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

	public EditText getBMIIndex() {
		return _BMIIndex;
	}

	public void setBMIIndex(EditText bMIIndex) {
		_BMIIndex = bMIIndex;
	}

	public EditText getBMIClassification() {
		return _BMIClassification;
	}

	public void setBMIClassification(EditText bMIClassification) {
		_BMIClassification = bMIClassification;
	}
	
	public EditText getWeight() {
		return weight;
	}

	public void setWeight(EditText weight) {
		this.weight = weight;
	}

	
	public EditText getAge() {
		return age;
	}
	

	public void setAge(EditText age) {
		this.age = age;
	}

	public EditText getHeight_feet() {
		return height_feet;
	}

	public void setHeight_feet(EditText height_feet) {
		this.height_feet = height_feet;
	}

	public EditText getHeight_inches() {
		return height_inches;
	}

	public void setHeight_inches(EditText height_inches) {
		this.height_inches = height_inches;
	}

	public EditText getGoal_weight() {
		return goal_weight;
	}

	public void setGoal_weight(EditText goal_weight) {
		this.goal_weight = goal_weight;
	}
}
