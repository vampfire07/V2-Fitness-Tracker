package com.example.v2fitnesstracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class ExerciseActivity extends Activity implements V2Activity {

	private TableLayout exerciseLayout;
	private EditText exerciseName;
	private Spinner exerciseType;
	private EditText sets;
	private EditText reps;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageView();
    }
    
    public void setPageView() {
		ScrollView overallLayout = new ScrollView(this);
		LinearLayout linearOverallLayout = createLinearLayout(LinearLayout.VERTICAL);
		View[] views = new View[] { createLogo(), new NavigationFactory(this).createNavigationButtons(), createContentLayout() };
		addViewsToLayout(views, linearOverallLayout);
		overallLayout.addView(linearOverallLayout);
		setContentView(overallLayout);
	}
    
    private LinearLayout createContentLayout() {
    	LinearLayout contentLayout = createLinearLayout(LinearLayout.VERTICAL);
    	TableLayout exerciseLayout = createTableLayoutView();
    	View[] views = new View[] { exerciseLayout };
    	addViewsToLayout(views, contentLayout);
    	return contentLayout;
    }
    
    private TableLayout createTableLayoutView() {
    	exerciseLayout = new TableLayout(this);
    	View[] views = new View[] { createHeader(), createExerciseRowView() };
    	addViewsToLayout(views, exerciseLayout);
    	return exerciseLayout;
    }
    
    private TableRow createExerciseRowView() {
    	TableRow row = new TableRow(this);
    	exerciseName = ActivityFactory.createEditText(this, 12, "Name");
    	sets = ActivityFactory.createEditText(this, 12, " ");
    	sets.setInputType(InputType.TYPE_CLASS_NUMBER);
    	reps = ActivityFactory.createEditText(this, 12, " ");
    	reps.setInputType(InputType.TYPE_CLASS_NUMBER);
    	View[] views = new View[] { exerciseName, createExerciseDropDown(), sets, reps, createAddExerciseButtonView() };
    	addViewsToLayout(views, row);
    	return row;
    }
    
    private Button createAddExerciseButtonView() {
    	Button addExerciseButton = new Button(this);
    	addExerciseButton.setText("Add");
    	addExerciseButton.setTextSize(10);
    	addExerciseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					exerciseLayout.addView(createExerciseRowView());
//					addExerciseToUser(new Exercise(exerciseName.getText().toString(), exerciseType.getSelectedItem().toString(),
//							Integer.parseInt(sets.getText().toString()), Integer.parseInt(reps.getText().toString())));
				}
				catch(NumberFormatException e) {
					showErrorMessage("Please fill in the fields with the correct information.");
				}
			}
    		
    	});
    	return addExerciseButton;
    }
    
    private void addExerciseToUser(Exercise exercise) {
    	List<Exercise> exercises = User.getExercises();
    	exercises.add(exercise);
    	User.setExercises(exercises);
    }

	private Spinner createExerciseDropDown() {
		Spinner exerciseType = new Spinner(this);
    	ArrayList<String> exerciseTypes = new ArrayList<String>(Arrays.asList("Biceps", "Triceps", "Forearms", "Chest", 
    		"Abs", "Deltoids", "Traps", "Lats", "Glutes", "Legs", "Other"));
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, exerciseTypes);
    	exerciseType.setAdapter(adapter);
		return exerciseType;
	}
    
    private TableRow createHeader() {
    	TableRow header = new TableRow(this);
    	LinearLayout headerContainer = createLinearLayout(LinearLayout.HORIZONTAL);
    	String[] headerNames = new String[] { "Name", "Type", "Sets", "Reps" };
    	TextView[] views = new TextView[headerNames.length];
    	for(int i = 0; i < headerNames.length; i++) {
    		views[i] = ActivityFactory.createTextView(this, 15, headerNames[i]);
    		views[i].setTextColor(Color.RED);
    	}
    	addViewsToLayout(views, headerContainer);
    	header.addView(headerContainer);
    	return header;
    }
    
    public ImageView createLogo() {
    	return createImageView(R.drawable.logo, ScaleType.FIT_XY);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_exercise, menu);
        return true;
    }

	public void showErrorMessage(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(true);
		ad.setMessage(message);
		ad.show();
	}

	public LinearLayout createLinearLayout(int orientation) {
		LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
	}


	// Adds the View elements in the array into the layout
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

	public Spinner getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(Spinner exerciseType) {
		this.exerciseType = exerciseType;
	}

    
}
