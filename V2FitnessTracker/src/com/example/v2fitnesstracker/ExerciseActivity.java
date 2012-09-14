package com.example.v2fitnesstracker;

import java.util.HashSet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.databases.DatabaseHelper;
import com.example.entities.Exercise;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class ExerciseActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {
	
	private User user;
	
	// Database Access Object (DAO)
	private RuntimeExceptionDao<Exercise, Integer> dao;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = HomeActivity.user;
        dao = getHelper().getRuntimeExerciseDao();
        setContentView(R.layout.activity_exercise);
        setNavigationButtons();
        updateView();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_exercise, menu);
        return true;
    }
    
    public void addRow(View view) {
    	Dialog d = createSpinnerDialog("Add an exercise", "What type is the exercise you wish to add?", R.array.exercise_types);
    	d.show();
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
    	dao.delete(findExerciseById(id));
    	parent.removeAllViews();
    	parent.setVisibility(View.GONE);
    }
    
    private Exercise findExerciseById(int id) {
    	for(Exercise e : dao.queryForAll()) {
    		if(e.getUser().getId() == user.getId())
    			if(e.getId() == id) return e;
    	}
    	return null;
    }
    
    public void updateView() {
    	TableLayout exerciseLayout = (TableLayout)findViewById(R.id.exercise_exerciseLayout);
    	exerciseLayout.removeAllViews();
    	user.setExerciseSet(new HashSet<Exercise>());
    	for(Exercise e : dao.queryForAll()) {
    		if(e.getUser().getId() == user.getId())
    			exerciseLayout.addView(createExerciseRow(e));
    		user.getExerciseSet().add(e);
    	}
    }
    
    private TableRow createExerciseRow(Exercise exercise) {
    	TableRow row = new TableRow(this);
    	row.setBackgroundColor(Color.WHITE);
    	
    	TextView exerciseName = createExerciseNameView(exercise);
    	TextView exerciseType = createExerciseTypeView(exercise);
    	TextView exerciseSets = createSetsView(exercise);
    	TextView exerciseReps = createRepsView(exercise);
    	
    	View[] views = new View[] { createHiddenIdView(exercise), exerciseName, exerciseType,
    			exerciseSets, exerciseReps, createEditButtonView(exercise), createRemoveButtonView() };
    	addViewsToLayout(views, row);
    	return row;
    }
    
    private TextView createHiddenIdView(Exercise exercise) {
    	TextView id = ActivityFactory.createTextView(this, 10, exercise.getId() + ""); 
    	id.setVisibility(View.GONE);
    	return id;
    }
    
    private Button createEditButtonView(final Exercise exercise) {
    	final Context context = this;
		Button editButton = new Button(this);
		editButton.setText("Edit");
    	editButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent navigateIntent = new Intent(context, ViewExerciseActivity.class);
				navigateIntent.putExtra("exercise_extra", exercise);
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
	
	private TextView createExerciseNameView(Exercise exercise) {
		TextView exerciseName = ActivityFactory.createTextView(this, 14, exercise.getName());
		exerciseName.setPadding(3, 0, 3, 0);
		if(exercise.getName().equals("")) exerciseName.setText("Unnamed");
		if(exercise.getName().length() > 10) {
    		exerciseName.setText(exercise.getName().substring(0, 5) + "...");
    	}
		exerciseName.setSingleLine(true);
		return exerciseName;
	}

	private TextView createExerciseTypeView(Exercise exercise) {
		TextView exerciseType = ActivityFactory.createTextView(this, 14, exercise.getType());
		exerciseType.setPadding(3, 0, 3, 0);
		exerciseType.setSingleLine(true);
		return exerciseType;
	}

	private TextView createRepsView(Exercise exercise) {
		TextView exerciseReps = ActivityFactory.createTextView(this, 14, exercise.getReps() + "");
		exerciseReps.setPadding(3, 0, 3, 0);
		exerciseReps.setSingleLine(true);
		return exerciseReps;
	}

	private TextView createSetsView(Exercise exercise) {
		TextView exerciseSets = ActivityFactory.createTextView(this, 14, exercise.getSets() + "");
		exerciseSets.setPadding(3, 0, 3, 0);
		exerciseSets.setSingleLine(true);
		return exerciseSets;
	}

    // Shows an AlertDialog with the message passed in the parameter
	public void showAlertMessage(String message, boolean cancelable) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(cancelable);
		ad.setMessage(message);
		ad.show();
	}

	// Returns a LinearLayout with the orientation passed in as parameter
	public LinearLayout createLinearLayout(int orientation) {
		LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
	}


	// Adds the View elements inside the array into the layout
    public void addViewsToLayout(View[] views, ViewGroup layout) {
    	if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
	}
    
    private Dialog createSpinnerDialog(String title, String message, int arrayId) {
    	final Dialog dialog = new Dialog(this);
    	dialog.setTitle(title);
    	
    	LinearLayout overallLayout = createLinearLayout(LinearLayout.VERTICAL);
    	
    	TextView textView = new TextView(this);
    	textView.setText(message);
    	
    	final Spinner spinner = createSpinner(arrayId);
    	
    	View[] views = new View[] { textView, spinner, createSpinnerButtons(dialog, spinner) };
    	addViewsToLayout(views, overallLayout);
    	dialog.setContentView(overallLayout);
    	return dialog;
    }
    
    private String getExerciseType(Spinner spinner) {
    	return spinner.getSelectedItem().toString();
    }
    
    private LinearLayout createSpinnerButtons(final Dialog dialog, final Spinner spinner) {
    	LinearLayout layout = createLinearLayout(LinearLayout.HORIZONTAL);
    	Button okButton = createOKButton(dialog, spinner);
    	Button cancelButton = createCancelButton(dialog);
    	View[] views = new View[] { okButton, cancelButton };
    	addViewsToLayout(views, layout);
    	return layout;
    }

	private Button createCancelButton(final Dialog dialog) {
		Button cancelButton = new Button(this);
    	cancelButton.setText("Cancel");
    	cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return cancelButton;
	}

	private Button createOKButton(final Dialog dialog, final Spinner spinner) {
		Button okButton = new Button(this);
    	okButton.setText("OK");
    	okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Exercise exercise = new Exercise("", getExerciseType(spinner), 0, 0);
				exercise.setUser(user);
				dao.create(exercise);
				
				updateView();
				dialog.dismiss();
			}
		});
		return okButton;
	}

	public Spinner createSpinner(int arrayId) {
		Spinner spinner = new Spinner(this);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
		return spinner;
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
