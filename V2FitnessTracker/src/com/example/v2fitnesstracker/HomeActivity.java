package com.example.v2fitnesstracker;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class HomeActivity extends Activity implements V2Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeInformation();
        setNavigationButtons();
    }
    
    /*
     * Displays the username and password to the appropriate text fields.
     */
    public void initializeInformation() {
    	((EditText)findViewById(R.id.home_username)).setText(User.getUsername());
    	((EditText)findViewById(R.id.home_password)).setText(User.getPassword());
    	((EditText)findViewById(R.id.home_age)).setText(User.getAge() + "");
    	((EditText)findViewById(R.id.home_heightFeet)).setText(User.getHeight_feet() + "");
    	((EditText)findViewById(R.id.home_heightInches)).setText(User.getHeight_inches() + "");
    	((EditText)findViewById(R.id.home_weight)).setText(User.getWeight() + "");
    	((EditText)findViewById(R.id.home_goalWeight)).setText(User.getGoal_weight() + "");
    }
    
    private AlertDialog.Builder createInputDialog(String title, String message) {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle(title);
    	alert.setMessage(message);
    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	
    	return alert;
    }
    
    public void changeUsername(View view) {
    	AlertDialog.Builder alert = createInputDialog("Change Username", "Type in the new username: ");
    	final EditText input = createInputField();
    	alert.setView(input);
    	
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@SuppressWarnings("null")
			public void onClick(DialogInterface dialog, int which) {
				String newUsername = input.getText().toString();
				if(newUsername != null || !newUsername.isEmpty()) {
					if(newUsername.length() <= 15) {
						User.setUsername(newUsername);
						((EditText)findViewById(R.id.home_username)).setText(newUsername);
					}
					else showAlertMessage("Username must contain only up to 15 characters.", true);
				}
				else showAlertMessage("Username must not be empty.", true);
			}
		});
    	alert.show();
    }

	private EditText createInputField() {
		final EditText input = new EditText(this);
    	input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return input;
	}
    
    public void changePassword(View view) {
    	AlertDialog.Builder alert = createInputDialog("Change Password", "Type in the new password: ");
    	final EditText input = createInputField();
    	alert.setView(input);
    	
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@SuppressWarnings("null")
			public void onClick(DialogInterface dialog, int which) {
				String newPassword = input.getText().toString();
				if(newPassword != null || !newPassword.isEmpty()) {
					if(newPassword.length() <= 15) {
						User.setUsername(newPassword);
						((EditText)findViewById(R.id.home_password)).setText(newPassword);
					}
					else showAlertMessage("Password must contain only up to 15 characters.", true);
				}
				else showAlertMessage("Password must not be empty.", true);
			}
		});
    	alert.show();
    }
    
    // Updates the user information (age, weight, goal weight, height)
    public void update(View view) {
    	try {
	    	User.setAge(Integer.parseInt(((EditText)findViewById(R.id.home_age)).getText().toString()));
	    	User.setWeight(Integer.parseInt(((EditText)findViewById(R.id.home_weight)).getText().toString()));
	    	User.setGoal_weight(Integer.parseInt(((EditText)findViewById(R.id.home_goalWeight)).getText().toString()));
	    	User.setHeight_feet(Integer.parseInt(((EditText)findViewById(R.id.home_heightFeet)).getText().toString()));
	    	User.setHeight_inches(Integer.parseInt(((EditText)findViewById(R.id.home_heightInches)).getText().toString()));
	    	showAlertMessage("Information has been updated", true);
    	}
    	catch(NumberFormatException e) {
    		showAlertMessage("Make sure the information are filled in correctly.", true);
    	}
    }
    
    /*
     *  Calculates the user's BMI based on weight and height 
     *  then displays the result in a text field.
     */
    public void calculateBMI(View view) {
    	double index = calculateBMIIndex();
    	((EditText)findViewById(R.id.home_BMIindex)).setText(index + "");
    	((EditText)findViewById(R.id.home_BMIclassification)).setText(calculateBMIClassification(index));
    }
    
    // Calculates BMI Index and returns the result in one decimal place.
    public double calculateBMIIndex() {
    	double index = 0;
    	int heightInInches = (User.getHeight_feet() * 12) + User.getHeight_inches();
    	if(heightInInches != 0) {
    		double squared = Math.pow(heightInInches, 2);
    		double preResult = User.getWeight() / squared;
    		double BMI_formula = preResult * 703;
    		index = Double.parseDouble(new DecimalFormat("#0.0").format(BMI_formula));
    	}
    	return index;
    }
    
    /*
     * Categorizes BMI Classification based on the BMI Index 
     * and returns the result as a String.
     */
    public String calculateBMIClassification(double index) {
    	String classification = "";
    	if(index < 18.5) classification = "Underweight";
    	else if(index >= 18.5 && index < 25) classification = "Normal";
    	else if(index >= 25 && index < 30) classification = "Overweight";
    	else if(index >= 30) classification = "Obese";
    	return classification;
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
    
    // Shows an AlertDialog with the message passed in the parameter 
    public void showAlertMessage(String message, boolean cancelable) {
    	AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(cancelable);
		ad.setMessage(message);
		ad.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    public void setNavigationButtons() {
    	Button home = (Button)findViewById(R.id.navigation_home);
    	Button exercise = (Button)findViewById(R.id.navigation_exercise);
    	Button nutrition = (Button)findViewById(R.id.navigation_nutrition);
    	Button journal = (Button)findViewById(R.id.navigation_journal);
    	Button logout = (Button)findViewById(R.id.navigation_logout);
    	new NavigationFactory(this).setNavigationOnClick(home, exercise, nutrition, journal, logout);
    }
}
