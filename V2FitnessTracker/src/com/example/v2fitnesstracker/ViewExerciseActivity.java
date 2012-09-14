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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.DatabaseHelper;
import com.example.entities.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class ViewExerciseActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {

	// Database Access Object (DAO)
	private RuntimeExceptionDao<Exercise, Integer> dao;
	
	private final String FIELD_NAME = "NAME";
	private final String FIELD_SETS = "SETS";
	private final String FIELD_REPS = "REPS";
	
	private Exercise exercise;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = getHelper().getRuntimeExerciseDao();
        setContentView(R.layout.activity_view_exercise);
        exercise = (Exercise)getIntent().getSerializableExtra("exercise_extra");
        initializeContents();
    }
    
    private void initializeContents() {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.viewExercise_exerciseLayout);
    	
    	final EditText exerciseNameEdit = createExerciseNameEdit(exercise);
    	final Spinner exerciseTypeEdit = createExerciseTypeEdit(exercise);
    	final EditText exerciseSetsEdit = createExerciseSetsEdit(exercise);
    	final EditText exerciseRepsEdit = createExerciseRepsEdit(exercise);
    	setFieldListeners(exerciseNameEdit, exerciseTypeEdit, exerciseSetsEdit, exerciseRepsEdit);
    	
    	final LinearLayout exerciseName = createNameView(exerciseNameEdit);
    	final LinearLayout exerciseType = createTypeView(exerciseTypeEdit);
    	final LinearLayout exerciseSets = createSetsView(exerciseSetsEdit);
    	final LinearLayout exerciseReps = createRepsView(exerciseRepsEdit);
    	Button doneButton = createDoneButton();
    	Button backButton = createBackButton();
    	
    	View[] views = new View[] { exerciseName,
    								exerciseType,
    								exerciseSets,
    								exerciseReps,
    								doneButton,
    								backButton};
    	addViewsToLayout(views, layout);
    }
    
    private void backToExerciseActivity() {
    	final Context context = this;
    	startActivity(new Intent(context, ExerciseActivity.class));
    	finish();
    }

	private Button createDoneButton() {
		Button doneButton = new Button(this);
    	doneButton.setText("Done");
    	doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dao.update(exercise);
				backToExerciseActivity();
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
				backToExerciseActivity();
			}
		});
		return doneButton;
	}

	private LinearLayout createRepsView(EditText exerciseReps) {
		LinearLayout repsLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView typeText = ActivityFactory.createTextView(this, 20, "Reps: ");
    	typeText.setTextColor(Color.RED);
    	View[] views = new View[] { typeText, exerciseReps };
    	addViewsToLayout(views, repsLayout);
		return repsLayout;
	}
	
	private EditText createExerciseRepsEdit(Exercise exercise) {
		final EditText reps = ActivityFactory.createEditText(this, 18, "Reps");
    	reps.setInputType(InputType.TYPE_CLASS_NUMBER);
    	reps.setText(exercise.getReps() + "");
		return reps;
	}

	private LinearLayout createSetsView(EditText exerciseSets) {
		LinearLayout setsLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView typeText = ActivityFactory.createTextView(this, 20, "Sets: ");
    	typeText.setTextColor(Color.RED);
    	View[] views = new View[] { typeText, exerciseSets };
    	addViewsToLayout(views, setsLayout);
		return setsLayout;
	}
    
	private EditText createExerciseSetsEdit(Exercise exercise) {
		final EditText sets = ActivityFactory.createEditText(this, 18, "Sets");
    	sets.setInputType(InputType.TYPE_CLASS_NUMBER);
    	sets.setText(exercise.getSets() + "");
		return sets;
	}

	private LinearLayout createTypeView(Spinner exerciseType) {
		LinearLayout typeLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView typeText = ActivityFactory.createTextView(this, 20, "Type: ");
    	typeText.setTextColor(Color.RED);
    	View[] views = new View[] { typeText, exerciseType };
    	addViewsToLayout(views, typeLayout);
		return typeLayout;
	}
    
    private Spinner createExerciseTypeEdit(Exercise exercise) {
		final Spinner exerciseType = createSpinner(R.array.exercise_types);
    	exerciseType.setSelection(getExercisePosition(exercise, exerciseType));
		return exerciseType;
	}

	private LinearLayout createNameView(EditText exerciseName) {
		LinearLayout nameLayout = createLinearLayout(LinearLayout.HORIZONTAL);
    	TextView nameText = ActivityFactory.createTextView(this, 20, "Name: ");
    	nameText.setTextColor(Color.RED);
    	View[] views = new View[] { nameText, exerciseName };
    	addViewsToLayout(views, nameLayout);
		return nameLayout;
	}
    
    private EditText createExerciseNameEdit(Exercise exercise) {
    	EditText nameEdit = ActivityFactory.createEditText(this, 18, "Name");
    	nameEdit.setText(exercise.getName());
    	return nameEdit;
	}
    
    private void setSpinnerListener(final Spinner spinner) {
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			// Sets the listener that triggers when the selected item changes
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
		    	exercise.setType(spinner.getSelectedItem().toString());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				spinner.setSelection(0);
			}
		});
    }
    
    private void setFieldListeners(final EditText exerciseName, final Spinner exerciseType, 
			final EditText exerciseSets, final EditText exerciseReps) {
		setEditTextChangedListener(exerciseName, FIELD_NAME);
    	setSpinnerListener(exerciseType);
    	setEditTextChangedListener(exerciseSets, FIELD_SETS);
    	setEditTextChangedListener(exerciseReps, FIELD_REPS);
	}
    
    private void setEditTextChangedListener(final EditText text, final String field) {
    	final Context context = this;
    	text.addTextChangedListener(new TextWatcher() {
    		public void afterTextChanged(Editable s) {
				try {
					if(field.equals(FIELD_NAME)) {
						if(text.getText().toString().length() > 20) {
							Toast.makeText(context, "Name can only be up to 20 characters long.", Toast.LENGTH_LONG).show();
							text.setText(text.getText().toString().substring(0, 20));
							return;
						}
				    	else exercise.setName(text.getText().toString());
					}
					else if(field.equals(FIELD_SETS)) {
						int sets = Integer.parseInt(text.getText().toString());
						if(sets < 0 || sets > 999) {
							Toast.makeText(context, "You cannot store that number of sets. Please try again.", Toast.LENGTH_LONG).show();
							text.setText(text.getText().toString().substring(0, 3));
							return;
						}
						else exercise.setSets(sets);
					}
					else if(field.equals(FIELD_REPS)) {
						int reps = Integer.parseInt(text.getText().toString());
						if(reps < 0 || reps > 999) {
							Toast.makeText(context, "You cannot store that number of reps. Please try again.", Toast.LENGTH_LONG).show();
							text.setText(text.getText().toString().substring(0, 3));
							return;
						}
						else exercise.setReps(Integer.parseInt(text.getText().toString()));
					}
				}
				catch(NullPointerException e) {
					Log.w("ExerciseActivity", "NullPointerException caught.");
				}
				catch(NumberFormatException e) {
					Log.w("ExerciseActivity", "NumberFormatException caught.");
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
    
    private int getExercisePosition(Exercise exercise, Spinner exerciseType) {
    	for(int i = 0; i < exerciseType.getAdapter().getCount(); i++) {
    		if(exerciseType.getAdapter().getItem(i).toString().equalsIgnoreCase(exercise.getType())) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    public Spinner createSpinner(int arrayId) {
		Spinner spinner = new Spinner(this);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
		return spinner;
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
    	Button exercise = (Button)findViewById(R.id.navigation_exercise);
    	Button nutrition = (Button)findViewById(R.id.navigation_nutrition);
    	Button journal = (Button)findViewById(R.id.navigation_journal);
    	Button logout = (Button)findViewById(R.id.navigation_logout);
    	new NavigationFactory(this).setNavigationOnClick(home, exercise, nutrition, journal, logout);
 	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_exercise, menu);
        return true;
    }

}
