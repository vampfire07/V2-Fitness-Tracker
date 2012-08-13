package com.example.v2fitnesstracker;

import java.util.HashSet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
	
	private final String FIELD_NAME = "NAME";
	private final String FIELD_SETS = "SETS";
	private final String FIELD_REPS = "REPS";
	
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
    	
    	final EditText exerciseName = createExerciseNameView(exercise);
    	final Spinner exerciseType = createExerciseTypeView(exercise);
    	final EditText exerciseSets = createSetsView(exercise);
    	final EditText exerciseReps = createRepsView(exercise);
    	setFieldListeners(exerciseName, exerciseType, exerciseSets, exerciseReps);
    	
    	View[] views = new View[] { createHiddenIdView(exercise), exerciseName, exerciseType,
    			exerciseSets, exerciseReps, createRemoveButtonView() };
    	addViewsToLayout(views, row);
    	return row;
    }

	private void setFieldListeners(final EditText exerciseName, final Spinner exerciseType, 
			final EditText exerciseSets, final EditText exerciseReps) {
		setEditTextChangedListener(exerciseName, FIELD_NAME);
    	setSpinnerListener(exerciseType);
    	setEditTextChangedListener(exerciseSets, FIELD_SETS);
    	setEditTextChangedListener(exerciseReps, FIELD_REPS);
	}
    
    private void setSpinnerListener(final Spinner spinner) {
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			// Sets the listener that triggers when the selected item changes
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
		    	findExerciseById(findId(spinner)).setType(spinner.getSelectedItem().toString());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				spinner.setSelection(0);
			}
		});
    }
    
    private void setEditTextChangedListener(final EditText text, final String field) {
    	text.addTextChangedListener(new TextWatcher() {
    		public void afterTextChanged(Editable s) {
				try {
					Exercise exercise = findExerciseById(findId(text));
					if(field.equals(FIELD_NAME))
						exercise.setName(text.getText().toString());
					else if(field.equals(FIELD_SETS))
						exercise.setSets(Integer.parseInt(text.getText().toString()));
					else if(field.equals(FIELD_REPS))
						exercise.setReps(Integer.parseInt(text.getText().toString()));
					dao.update(exercise);
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
    
    private TextView createHiddenIdView(Exercise exercise) {
    	TextView id = ActivityFactory.createTextView(this, 10, exercise.getId() + ""); 
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
	
	private int findId(View view) {
		LinearLayout viewParent = (LinearLayout)view.getParent();
		View idView = viewParent.getChildAt(0);
		// The id field is a TextView located at index 0 of the layout.
		if(idView instanceof TextView) {
			return Integer.parseInt(((TextView) idView).getText().toString());
		}
		return -1;
	}

	private EditText createExerciseNameView(Exercise exercise) {
		EditText exerciseName = ActivityFactory.createEditText(this, 12, "Name");
		exerciseName.setSingleLine(true);
    	exerciseName.setText(exercise.getName());
		return exerciseName;
	}

	private Spinner createExerciseTypeView(Exercise exercise) {
		final Spinner exerciseType = createSpinner(R.array.exercise_types);
    	exerciseType.setSelection(getExercisePosition(exercise, exerciseType));
		return exerciseType;
	}

	private EditText createRepsView(Exercise exercise) {
		final EditText reps = ActivityFactory.createEditText(this, 12, "Reps");
    	reps.setInputType(InputType.TYPE_CLASS_NUMBER);
    	reps.setText(exercise.getReps() + "");
		return reps;
	}

	private EditText createSetsView(Exercise exercise) {
		final EditText sets = ActivityFactory.createEditText(this, 12, "Sets");
    	sets.setInputType(InputType.TYPE_CLASS_NUMBER);
    	sets.setText(exercise.getSets() + "");
		return sets;
	}
    
    private int getExercisePosition(Exercise exercise, Spinner exerciseType) {
    	for(int i = 0; i < exerciseType.getAdapter().getCount(); i++) {
    		if(exerciseType.getAdapter().getItem(i).toString().equalsIgnoreCase(exercise.getType())) {
    			return i;
    		}
    	}
    	return -1;
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
